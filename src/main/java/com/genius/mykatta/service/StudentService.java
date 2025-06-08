package com.genius.mykatta.service;

import com.genius.mykatta.exception.DuplicateResourceException;
import com.genius.mykatta.exception.ResourceNotFoundException;
import com.genius.mykatta.model.Student;
import java.util.List;

public interface StudentService {
    Student registerStudent(Student student) throws DuplicateResourceException;
    Student getStudentByPrn(String prnNumber) throws ResourceNotFoundException;
    boolean existsByEmail(String email);
    List<Student> getTopContributors(int limit);
    void validateStudentDoesNotExist(Student student) throws DuplicateResourceException;
}
