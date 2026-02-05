package com.cyber.portal.complaintAndFirManagement.repository;

import com.cyber.portal.complaintAndFirManagement.dto.ComplaintMonthlyCountDto;
import com.cyber.portal.complaintAndFirManagement.entity.Complaint;
import com.cyber.portal.sharedResources.enums.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByAcknowledgementNo(String acknowledgementNo);

    @Query("SELECT c FROM Complaint c WHERE c.citizen.id = :citizenId")
    List<Complaint> findByCitizenId(@Param("citizenId") Long citizenId);

    @org.springframework.data.jpa.repository.Query("SELECT c.category, COUNT(c) FROM Complaint c GROUP BY c.category")
    List<Object[]> countComplaintsByCategory();

    @Query("""
    SELECT c.category, COUNT(c)
    FROM Complaint c
    WHERE c.createdAt >= :fromDate
    GROUP BY c.category
    """)
    List<Object[]> countComplaintsByCategoryFromDate(
            @Param("fromDate") LocalDateTime fromDate
    );


    @org.springframework.data.jpa.repository.Query("SELECT c.status, COUNT(c) FROM Complaint c GROUP BY c.status")
    List<Object[]> countComplaintsByStatus();

    @org.springframework.data.jpa.repository.Query("SELECT c.state, COUNT(c) FROM Complaint c GROUP BY c.state")
    List<Object[]> countComplaintsByState();

    long countByCitizen_IdAndStatus(Long citizenId, IncidentStatus status);

    List<Complaint> findByCitizen_IdOrderByCreatedAtDesc(Long citizenId);

    long countByCitizenId(Long citizenId);

    long countByCitizenIdAndStatus(Long citizenId, IncidentStatus status);

    @Query(
            value = """
        SELECT
            :year AS year,
            m.month AS month,
            COALESCE(COUNT(c.id), 0) AS totalComplaints
        FROM generate_series(1, 12) AS m(month)
        LEFT JOIN complaints c
            ON EXTRACT(MONTH FROM c.created_at) = m.month
            AND EXTRACT(YEAR FROM c.created_at) = :year
        GROUP BY m.month
        ORDER BY m.month
    """,
            nativeQuery = true
    )
    List<Object[]> getComplaintCountMonthWise(@Param("year") int year);

    @Query("""
    SELECT AVG(TIMESTAMPDIFF(DAY, c.createdAt, c.resolvedAt))
    FROM Complaint c
    WHERE c.resolvedAt IS NOT NULL
""")
    Double findAverageProcessingTime();


}
