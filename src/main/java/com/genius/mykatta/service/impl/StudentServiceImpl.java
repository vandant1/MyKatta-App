package com.genius.mykatta.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import com.genius.mykatta.exception.DuplicateResourceException;
import com.genius.mykatta.exception.ResourceNotFoundException;
import com.genius.mykatta.model.Student;
import com.genius.mykatta.repository.StudentRepository;
import com.genius.mykatta.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Student registerStudent(Student student) {
        validateStudentDoesNotExist(student);
        student.setPasswordHash(passwordEncoder.encode(student.getPasswordHash()));
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentByPrn(String prnNumber) {
        return studentRepository.findByPrnNumber(prnNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with PRN: " + prnNumber));
    }

    @Override
    public void validateStudentDoesNotExist(Student student) {
        if (studentRepository.existsByPrnNumber(student.getPrnNumber())) {
            throw new DuplicateResourceException("PRN already registered");
        }
        if (studentRepository.existsByEmailIgnoreCase(student.getEmail())) {
            throw new DuplicateResourceException("Email already in use");
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public List<Student> getTopContributors(int limit) {
        return studentRepository.findTopContributors(PageRequest.of(0, limit)).getContent();
    }
}
