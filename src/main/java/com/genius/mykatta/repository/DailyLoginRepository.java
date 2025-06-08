package com.genius.mykatta.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyLoginRepository extends JpaRepository<DailyLogin, Integer> {

    Optional<DailyLogin> findByStudentIdAndLoginDate(Integer studentId, LocalDate loginDate);

    long countByStudentId(Integer studentId);

    @Query("SELECT COUNT(DISTINCT dl.student.id) FROM DailyLogin dl " +
           "WHERE dl.loginDate BETWEEN :startDate AND :endDate")
    long countActiveUsersBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT dl.loginDate, COUNT(dl) FROM DailyLogin dl " +
           "WHERE dl.loginDate >= :sinceDate " +
           "GROUP BY dl.loginDate " +
           "ORDER BY dl.loginDate")
    List<Object[]> getDailyLoginStats(LocalDate sinceDate);
}