package com.genius.mykatta.repository;

import com.genius.mykatta.model.DailyLogin;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyLoginRepository extends JpaRepository<DailyLogin, Integer> {

    Optional<DailyLogin> findByStudentIdAndLoginDate(Integer studentId, LocalDate loginDate);

    long countByStudentId(Integer studentId);

    @Query("SELECT COUNT(DISTINCT dl.studentId) FROM DailyLogin dl " +
           "WHERE dl.loginDate BETWEEN :startDate AND :endDate")
    long countActiveUsersBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT dl.loginDate, COUNT(dl) FROM DailyLogin dl " +
           "WHERE dl.loginDate >= :sinceDate " +
           "GROUP BY dl.loginDate " +
           "ORDER BY dl.loginDate")
    List<Object[]> getDailyLoginStats(@Param("sinceDate") LocalDate sinceDate);
}
