package org.example.lab7.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lab7.ApiResponse.ApiResponse;
import org.example.lab7.Model.Student;
import org.example.lab7.Service.ClassService;
import org.example.lab7.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final ClassService classService;




    @GetMapping("/get-students")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.status(200).body(studentService.getAllStudents());
    }

    @GetMapping("/get-student-by-id/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        return ResponseEntity.status(200).body(student);
    }

    @PostMapping("/add-student")
    public ResponseEntity<?> addStudent(@Valid @RequestBody Student student, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean added = studentService.addStudent(student);
        if (!added) {
            return ResponseEntity.status(400).body(new ApiResponse("Id should be unique"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Student added"));
    }

    @PutMapping("/updated-student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @Valid @RequestBody Student student, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean updated = studentService.updateStudent(id, student);
        if (!updated) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Student updated"));
    }

    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        boolean deleted = studentService.deleteStudent(id);
        if (!deleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Student deleted"));
    }

    @GetMapping("/filter-student-by-age/{age}")
    public ResponseEntity<?> getStudentsByMinAge(@PathVariable int age) {
        return ResponseEntity.status(200).body(studentService.getStudentsByMinAge(age));
    }

    /** Students who are not enrolled in any class (placement / registration queue). */
    @GetMapping("/not-enrolled-in-class")
    public ResponseEntity<?> getStudentsNotEnrolledInAnyClass() {
        return ResponseEntity.status(200).body(studentService.getStudentsNotEnrolledInAnyClass());
    }

    @GetMapping("/student-by-class/{classId}")
    public ResponseEntity<?> getStudentsByClass(@PathVariable String classId) {
        if (classService.getClassById(classId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }
        return ResponseEntity.status(200).body(studentService.getStudentsByClassId(classId));
    }

    @PutMapping("/add-student-to-class/{studentId}/{classId}")
    public ResponseEntity<?> addClassToStudent(@PathVariable String studentId, @PathVariable String classId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found" ));
        }
        if (classService.getClassById(classId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }
        if (!studentService.addClassToStudent(studentId, classId)) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Class added to student"));
    }

    @DeleteMapping("/remove-student-from-class/{studentId}/{classId}")
    public ResponseEntity<?> removeClassFromStudent(@PathVariable String studentId, @PathVariable String classId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found" ));
        }
        if (classService.getClassById(classId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }
        boolean removed = studentService.removeClassFromStudent(studentId, classId);
        if (!removed) {
            return ResponseEntity.status(400).body(new ApiResponse("Student is not in this class "));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Class removed from student"));
    }
}
