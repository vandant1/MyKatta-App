package com.genius.mykatta.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AdminHistoryRepository extends JpaRepository<AdminHistory, Integer> {

    List<AdminHistory> findByStudentId(Integer studentId);

    Optional<AdminHistory> findByIsCurrentTrue();

    long countByIsCurrentTrue();

    @Query("SELECT ah FROM AdminHistory ah WHERE " +
           "ah.endDate IS NULL AND " +
           "ah.startDate <= :date AND " +
           "(ah.endDate IS NULL OR ah.endDate >= :date)")
    List<AdminHistory> findActiveAdminsOnDate(LocalDateTime date);

    @Transactional
    @Modifying
    @Query("UPDATE AdminHistory ah SET ah.isCurrent = false, ah.endDate = CURRENT_TIMESTAMP " +
           "WHERE ah.isCurrent = true")
    void demoteAllCurrentAdmins();
}
