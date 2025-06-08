package com.genius.mykatta.repository;

import com.genius.mykatta.model.AdminHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminHistoryRepository extends JpaRepository<AdminHistory, Integer> {

    List<AdminHistory> findByAdminId(Integer adminId);

    Optional<AdminHistory> findByIsCurrentTrue();

    long countByIsCurrentTrue();

    @Query("SELECT ah FROM AdminHistory ah WHERE " +
           "ah.endDate IS NULL AND " +
           "ah.startDate <= :date AND " +
           "(ah.endDate IS NULL OR ah.endDate >= :date)")
    List<AdminHistory> findActiveAdminsOnDate(@Param("date") LocalDateTime date);

    @Transactional
    @Modifying
    @Query("UPDATE AdminHistory ah SET ah.isCurrent = false, ah.endDate = CURRENT_TIMESTAMP " +
           "WHERE ah.isCurrent = true")
    void demoteAllCurrentAdmins();
}
