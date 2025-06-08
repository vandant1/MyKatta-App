package com.genius.mykatta.controller;

import com.genius.mykatta.exception.*;
import com.genius.mykatta.model.Student;
import com.genius.mykatta.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student registerStudent(@Valid @RequestBody Student student) {
        return studentService.registerStudent(student);
    }

    @GetMapping("/{prnNumber}")
    public Student getStudent(@PathVariable String prnNumber) {
        return studentService.getStudentByPrn(prnNumber);
    }

    @GetMapping("/top-contributors")
    public List<Student> getTopContributors(
            @RequestParam(defaultValue = "10") int limit) {
        return (List<Student>) studentService.getTopContributors(limit);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
