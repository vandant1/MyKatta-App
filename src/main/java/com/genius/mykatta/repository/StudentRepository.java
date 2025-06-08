package com.genius.mykatta.repository;

import com.genius.mykatta.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByPrnNumber(@NonNull String prnNumber);

    Optional<Student> findByEmailIgnoreCase(@NonNull String email);

    boolean existsByPrnNumber(@NonNull String prnNumber);

    boolean existsByEmailIgnoreCase(@NonNull String email);

    @Query("SELECT s FROM Student s WHERE s.isActive = true ORDER BY s.totalCoins DESC")
    Page<Student> findTopContributors(Pageable pageable);

    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.id = :studentId AND s.isActive = true")
    boolean isActiveStudent(Integer studentId);
}
