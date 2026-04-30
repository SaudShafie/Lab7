package org.example.lab7.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lab7.ApiResponse.ApiResponse;
import org.example.lab7.Model.Class;
import org.example.lab7.Service.ClassService;
import org.example.lab7.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/classes")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;
    private final StudentService studentService;


    @GetMapping("/get-all-classes")
    public ResponseEntity<?> getAllClasses() {
        return ResponseEntity.status(200).body(classService.getAllClasses());
    }



    @PostMapping("/add-class")
    public ResponseEntity<?> addClass(@Valid @RequestBody Class schoolClass, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean added = classService.addClass(schoolClass);
        if (!added) {
            return ResponseEntity.status(400).body(new ApiResponse("Id should be unique"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Class added"));
    }

    @PutMapping("/update-class/{id}")
    public ResponseEntity<?> updateClass(@PathVariable String id, @Valid @RequestBody Class schoolClass, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }

        boolean updated = classService.updateClass(id, schoolClass);
        if (!updated) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }


        return ResponseEntity.status(200).body(new ApiResponse("Class updated"));
    }

    @DeleteMapping("/delete-class/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable String id) {
        boolean deleted = classService.deleteClass(id);
        if (!deleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Class deleted"));
    }
    @GetMapping("/get-class-by-id/{id}")
    public ResponseEntity<?> getClassById(@PathVariable String id) {
        Class schoolClass = classService.getClassById(id);
        if (schoolClass == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }
        return ResponseEntity.status(200).body(schoolClass);
    }
    @GetMapping("/get-class-by-subject/{subjectName}")
    public ResponseEntity<?> getClassesBySubjectName(@PathVariable String subjectName) {
        ArrayList<Class> classes =classService.getClassesBySubjectName(subjectName);
        if (classes.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No classes for this subject found"));

        }

        return ResponseEntity.status(200).body(classes);
    }

    @GetMapping("/get-classes-student-have/{studentId}")
    public ResponseEntity<?> getClassesByStudent(@PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        ArrayList<Class> classes =classService.getClassesByStudentId(studentId);
        if (classes.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("No classes for this student found"));

        }
        return ResponseEntity.status(200).body(classes);
    }

    @PutMapping("/add-to-class/{classId}/{studentId}")
    public ResponseEntity<?> addStudentToClass(@PathVariable String classId, @PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        if (classService.getClassById(classId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }
        classService.addStudentToClass(classId, studentId);
        studentService.addClassToStudent(studentId, classId);
        return ResponseEntity.status(200).body(new ApiResponse("Student added to class"));
    }

    @DeleteMapping("/remove-from-class/{classId}/{studentId}")
    public ResponseEntity<?> removeStudentFromClass(@PathVariable String classId, @PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        if (classService.getClassById(classId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }
        boolean removed = classService.removeStudentFromClass(classId, studentId);
        if (!removed) {
            return ResponseEntity.status(400).body(new ApiResponse("student is not in class " ));
        }
        studentService.removeClassFromStudent(studentId, classId);
        return ResponseEntity.status(200).body(new ApiResponse("Student removed from class"));
    }

    @GetMapping("/get-number-of-students/{classId}")
    public ResponseEntity<?> getClassStudentCount(@PathVariable String classId) {
        Class schoolClass = classService.getClassById(classId);
        if (schoolClass == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse(schoolClass.getStudentCount()+""));
    }
}
