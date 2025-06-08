package com.genius.mykatta.repository;

import java.util.Optional;

import org.springframework.lang.NonNull;

import com.genius.mykatta.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByPrnNumber(@NonNull String prnNumber);

    Optional<Student> findByEmailIgnoreCase(@NonNull String email);

    boolean existsByPrnNumber(@NonNull String prnNumber);

    boolean existsByEmailIgnoreCase(@NonNull String email);

    @Query("SELECT s FROM Student s WHERE s.isActive = true ORDER BY s.totalCoins DESC")
    Page<Student> findTopContributors(Pageable pageable);

    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.id = :studentId AND s.isActive = true")
    boolean isActiveStudent(Integer studentId);

    Student save(Student student);

    Object findById(Integer studentId);
}
