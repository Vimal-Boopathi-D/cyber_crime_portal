package com.cyber.portal.volunteerManagement.service.impl;

import com.cyber.portal.sharedResources.enums.VolunteerStatus;
import com.cyber.portal.volunteerManagement.entity.Volunteer;
import com.cyber.portal.volunteerManagement.repository.VolunteerRepository;
import com.cyber.portal.volunteerManagement.service.VolunteerCsvService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerCsvServiceImpl implements VolunteerCsvService {


    private final VolunteerRepository volunteerRepository;

    public ByteArrayInputStream exportApprovedVolunteersExcel() throws IOException {
        List<Volunteer> list =
                volunteerRepository.findByStatus(VolunteerStatus.APPROVED);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Approved Volunteers");
        CellStyle headerStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);

        String[] headers = {
                "Volunteer ID","Name","Email","Mobile",
                "Gender","State","District","Type","Status"
        };

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;

        for (Volunteer v : list) {
            Row row = sheet.createRow(rowNum++);

            createCell(row, 0, v.getId(), centerStyle);
            createCell(row, 1, v.getVolunteerName(), centerStyle);
            createCell(row, 2, v.getEmail(), centerStyle);
            createCell(row, 3, v.getMobileNo(), centerStyle);
            createCell(row, 4, v.getGender(), centerStyle);
            createCell(row, 5, v.getState(), centerStyle);
            createCell(row, 6, v.getDistrict(), centerStyle);
            createCell(row, 7, v.getVolunteerType(), centerStyle);
            createCell(row, 8, v.getStatus(), centerStyle);
        }


        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void createCell(Row row, int col, Object value, CellStyle style) {
        Cell cell = row.createCell(col);
        String text = value == null ? "" : value.toString();
        cell.setCellValue(" " + text + " ");
        cell.setCellStyle(style);
    }
}
