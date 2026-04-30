package org.example.lab7.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lab7.ApiResponse.ApiResponse;
import org.example.lab7.Model.Subject;
import org.example.lab7.Service.SchoolConnectionService;
import org.example.lab7.Service.StudentService;
import org.example.lab7.Service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/subjects")

@RequiredArgsConstructor

public class SubjectController {
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final SchoolConnectionService schoolConnectionService;


    @GetMapping("/get-subjects")
    public ResponseEntity<?> getAllSubjects() {
        return ResponseEntity.status(200).body(subjectService.getAllSubjects());
    }

    @GetMapping("/get-subject-by-id/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable String id) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Subject not found"));
        }
        return ResponseEntity.status(200).body(subject);
    }

    @PostMapping("/add-subject")
    public ResponseEntity<?> addSubject(@Valid @RequestBody Subject subject, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean added = subjectService.addSubject(subject);
        if (!added) {
            return ResponseEntity.status(400).body(new ApiResponse("Id should be unique"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Subject added"));
    }

    @PutMapping("/update-subject/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable String id, @Valid @RequestBody Subject subject, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean updated = subjectService.updateSubject(id, subject);
        if (!updated) {
            return ResponseEntity.status(400).body(new ApiResponse("Subject not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Subject updated"));
    }

    @DeleteMapping("/delete-subject/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable String id) {
        boolean deleted = subjectService.deleteSubject(id);
        if (!deleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Subject not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Subject deleted"));
    }

    @GetMapping("/subject-by-grade/{grade}")
    public ResponseEntity<?> getSubjectsByGrade(@PathVariable String grade) {
        return ResponseEntity.status(200).body(subjectService.getSubjectsByGrade(grade));
    }

    @GetMapping("/filter-subject-by-hours/{hours}")
    public ResponseEntity<?> getSubjectsByHours(@PathVariable int hours) {
        return ResponseEntity.status(200).body(subjectService.getSubjectsByHours(hours));
    }

    @GetMapping("/get-student-subjects/{studentId}")
    public ResponseEntity<?> getSubjectsByStudent(@PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        return ResponseEntity.status(200).body(subjectService.getSubjectsByStudentId(studentId));
    }

    @PutMapping("/add-subject-to-student/{subjectId}/{studentId}")
    public ResponseEntity<?> assignSubjectToStudent(@PathVariable String subjectId, @PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        boolean assigned = schoolConnectionService.assignSubjectToStudent(subjectId, studentId);
        if (!assigned) {
            return ResponseEntity.status(400).body(new ApiResponse("Subject not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Subject assigned to student"));
    }
}
