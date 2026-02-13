package com.cyber.portal.complaintAndFirManagement.service.impl;

import com.cyber.portal.citizenManagement.entity.Citizen;
import com.cyber.portal.citizenManagement.entity.PoliceOfficer;
import com.cyber.portal.citizenManagement.repository.CitizenRepository;
import com.cyber.portal.citizenManagement.repository.PoliceOfficerRepository;
import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.complaintAndFirManagement.entity.FIR;
import com.cyber.portal.complaintAndFirManagement.repository.ComplaintRepository;
import com.cyber.portal.complaintAndFirManagement.repository.FIRRepository;
import com.cyber.portal.complaintAndFirManagement.service.DocumentService;
import com.cyber.portal.sharedResources.enums.State;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import com.cyber.portal.sharedResources.exception.PortalException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private static final Map<State, AtomicLong> STATE_SEQUENCE_MAP =
            new EnumMap<>(State.class);

    private final ComplaintRepository complaintRepository;
    private final FIRRepository firRepository;
    private final CitizenRepository citizenRepository;
    private final PoliceOfficerRepository policeOfficerRepository;

    private static final String FIR_UPLOAD_DIR = "fir/uploads/";

    @Override
    public byte[] generateComplaintReport(Long complaintId) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // ===== Title =====
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph(
                    "COMPLAINT SUBMISSION RECEIPT (CSRF)", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            // ===== Complaint Header =====
            PdfPTable header = new PdfPTable(2);
            header.setWidthPercentage(100);

            addRow(header, "Complaint ID", complaint.getId().toString());
            addRow(header, "Submitted On", complaint.getCreatedAt().toString());
            addRow(header, "Status", complaint.getStatus().name());

            document.add(header);
            document.add(new Paragraph("\n"));

            // ===== Complainant Details =====
            addSectionTitle(document, "Complainant Details");

            PdfPTable citizenTable = new PdfPTable(2);
            citizenTable.setWidthPercentage(100);

            addRow(citizenTable, "Name", complaint.getCitizen().getName());
            addRow(citizenTable, "Phone", complaint.getCitizen().getMobileNo());
            addRow(citizenTable, "Email", complaint.getCitizen().getEmail());
            addRow(citizenTable, "Address", complaint.getCitizen().getAddress());

            document.add(citizenTable);
            document.add(new Paragraph("\n"));

            // ===== Incident Summary =====
            addSectionTitle(document, "Incident Summary");

            PdfPTable incidentTable = new PdfPTable(2);
            incidentTable.setWidthPercentage(100);

            addRow(incidentTable, "Category", complaint.getCategory().toString());
            addRow(incidentTable, "Incident Date",
                    complaint.getIncidentDate().toString());
            addRow(incidentTable, "Location", complaint.getIncidentLocation());

            document.add(incidentTable);
            document.add(new Paragraph("\n"));

            addSectionTitle(document, "Complaint Description");

            Paragraph desc = new Paragraph(
                    complaint.getAdditionalInfo(),
                    new Font(Font.HELVETICA, 11)
            );
            desc.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(desc);

            document.add(new Paragraph("\n\n"));
            addSectionTitle(document, "Important Notice");

            Paragraph notice = new Paragraph(
                    "• This document is NOT an FIR.\n" +
                            "• Police verification is pending.\n" +
                            "• FIR will be generated after police approval.\n" +
                            "• Track your complaint using Complaint ID.",
                    new Font(Font.HELVETICA, 10)
            );
            document.add(notice);

            document.add(new Paragraph("\n\n"));

            // ===== Signatures =====
            PdfPTable signTable = new PdfPTable(2);
            signTable.setWidthPercentage(100);

            PdfPCell citizenSign = new PdfPCell(
                    new Phrase("Citizen Signature"));
            PdfPCell systemSign = new PdfPCell(
                    new Phrase("System Generated"));

            citizenSign.setFixedHeight(50);
            systemSign.setFixedHeight(50);

            signTable.addCell(citizenSign);
            signTable.addCell(systemSign);

            document.add(signTable);

            document.add(new Paragraph("\n"));

            // ===== Footer =====
            Paragraph footer = new Paragraph(
                    "Generated by Cyber Crime Reporting Portal",
                    new Font(Font.HELVETICA, 9, Font.ITALIC)
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSRF PDF", e);
        }
        return out.toByteArray();
    }

    @Override
    public byte[] getFIRCopy(Long firId) {
        FIR fir = firRepository.getFir(firId)
                .orElseThrow(() -> new RuntimeException("FIR not found"));

        Complaint complaint = fir.getComplaint();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("FIRST INFORMATION REPORT (FIR)", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);

            addRow(headerTable, "FIR Number", fir.getFirNo());
            addRow(headerTable, "Generated At", fir.getGeneratedAt().toString());
            addRow(headerTable, "Generated By", fir.getGeneratedBy().getName());
            addRow(headerTable, "Complaint ID", complaint.getId().toString());

            document.add(headerTable);
            document.add(new Paragraph("\n"));

            addSectionTitle(document, "Complainant Details");

            PdfPTable complainantTable = new PdfPTable(2);
            complainantTable.setWidthPercentage(100);

            addRow(complainantTable, "Name", complaint.getCitizen().getName());
            addRow(complainantTable, "Phone", complaint.getCitizen().getMobileNo());
            addRow(complainantTable, "Email", complaint.getCitizen().getEmail());
            addRow(complainantTable, "Address", complaint.getCitizen().getAddress());

            document.add(complainantTable);
            document.add(new Paragraph("\n"));

            addSectionTitle(document, "Incident Details");

            PdfPTable incidentTable = new PdfPTable(2);
            incidentTable.setWidthPercentage(100);

            addRow(incidentTable, "Category", complaint.getCategory().toString());
            addRow(incidentTable, "Incident Date", complaint.getIncidentDate().toString());
            addRow(incidentTable, "Location", complaint.getIncidentLocation());
            addRow(incidentTable, "Complaint Status", complaint.getStatus().name());

            document.add(incidentTable);
            document.add(new Paragraph("\n"));

            addSectionTitle(document, "Statement of the Complainant");

            Paragraph description = new Paragraph(
                    complaint.getAdditionalInfo(),
                    new Font(Font.HELVETICA, 11)
            );
            description.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(description);

            document.add(new Paragraph("\n\n"));

            PdfPTable signTable = new PdfPTable(2);
            signTable.setWidthPercentage(100);

            PdfPCell citizenSign = new PdfPCell(new Phrase("Complainant Signature"));
            PdfPCell policeSign = new PdfPCell(new Phrase(
                    "Police Officer Signature\n" + fir.getGeneratedBy()
            ));

            citizenSign.setFixedHeight(60);
            policeSign.setFixedHeight(60);

            signTable.addCell(citizenSign);
            signTable.addCell(policeSign);

            document.add(signTable);

            document.add(new Paragraph("\n"));

            Paragraph footer = new Paragraph(
                    "This FIR is generated electronically as per Section 154 CrPC.",
                    new Font(Font.HELVETICA, 9, Font.ITALIC)
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating FIR PDF", e);
        }

        return out.toByteArray();
    }

    private void addSectionTitle(Document document, String title) throws DocumentException {
        Font font = new Font(Font.HELVETICA, 14, Font.BOLD);
        Paragraph p = new Paragraph(title, font);
        p.setSpacingBefore(10);
        p.setSpacingAfter(5);
        document.add(p);
    }

    private void addRow(PdfPTable table, String key, String value) {
        PdfPCell keyCell = new PdfPCell(new Phrase(key, new Font(Font.HELVETICA, 11, Font.BOLD)));
        PdfPCell valueCell = new PdfPCell(new Phrase(
                value != null ? value : "-", new Font(Font.HELVETICA, 11)
        ));

        keyCell.setPadding(6);
        valueCell.setPadding(6);
        table.addCell(keyCell);
        table.addCell(valueCell);
    }

    static {
        for (State state : State.values()) {
            STATE_SEQUENCE_MAP.put(state, new AtomicLong(0));
        }
    }


    @Override
    @Transactional
    public void uploadFIR(Long complaintId, Long officerId, MultipartFile firDocument) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new PortalException("Complaint not found", HttpStatus.NOT_FOUND));

        PoliceOfficer policeOfficer = policeOfficerRepository.findById(officerId)
                .orElseThrow(() -> new PortalException("Officer not found", HttpStatus.NOT_FOUND));

        FIR fir = firRepository.findByComplaintId(complaintId)
                .orElseGet(() -> {
                    long sequence = STATE_SEQUENCE_MAP
                            .get(policeOfficer.getState())
                            .incrementAndGet();

                    int year = LocalDate.now().getYear();
                    String firNo = String.format(
                            "FIR/%s/%d/%06d",
                            policeOfficer.getState(),
                            year,
                            sequence
                    );

                    return FIR.builder()
                            .firNo(firNo)
                            .complaint(complaint)
                            .generatedBy(policeOfficer)
                            .build();
                });
        fir.setGeneratedBy(policeOfficer);

        if (firDocument != null && !firDocument.isEmpty()) {
            try {
                Path uploadDir = Paths.get(FIR_UPLOAD_DIR).toAbsolutePath().normalize();
                Files.createDirectories(uploadDir);

                String safeFirNo = fir.getFirNo().replace("/", "_");
                String filename = "FIR_" + complaintId + "_" + safeFirNo + ".pdf";

                Path path = uploadDir.resolve(filename);

                Files.copy(firDocument.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                fir.setFirPath(path.toString());

            } catch (Exception e) {
                throw new RuntimeException("Failed to upload FIR document", e);
            }
        }

        complaint.setStatus(IncidentStatus.FIR_GENERATED);

        complaintRepository.save(complaint);
        firRepository.save(fir);
    }


    @Override
    public byte[] generateCitizenComplaintExcel(Long citizenId) {
        List<Complaint> complaints;

        if (citizenId == null) {
            complaints = complaintRepository.findAll();
        }else {
            complaints =
                    complaintRepository.findByCitizenId(citizenId);
        }

        if (complaints.isEmpty()) {
            throw new RuntimeException("No complaints found for this citizen");
        }

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("My Complaints");
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);          // Works now
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            Row header = sheet.createRow(0);
            String[] columns = {
                    "Complaint No",
                    "Complaint Date",
                    "Category",
                    "Description",
                    "Status",
                    "Police Station",
                    "Fir Generated"
            };

            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy");

            int rowNum = 1;
            for (Complaint c : complaints) {
                Row row = sheet.createRow(rowNum++);
                String firGenerated =
                        c.getFir() != null ? "YES" : "NO";
                row.createCell(0).setCellValue(c.getId());
                row.createCell(1).setCellValue(c.getCreatedAt().format(formatter));
                row.createCell(2).setCellValue(c.getCategory().name());
                row.createCell(3).setCellValue(c.getAdditionalInfo());
                row.createCell(4).setCellValue(c.getStatus().name());
                row.createCell(5).setCellValue(c.getPoliceStation());
                row.createCell(6).setCellValue(firGenerated);
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating complaint Excel file", e);
        }
    }

}
