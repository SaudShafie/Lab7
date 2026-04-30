package org.example.lab7.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lab7.ApiResponse.ApiResponse;
import org.example.lab7.Model.Teacher;
import org.example.lab7.Service.SectionService;
import org.example.lab7.Service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final SectionService sectionService;


    @GetMapping("/get-teachers")
    public ResponseEntity<?> getAllTeachers() {
        return ResponseEntity.status(200).body(teacherService.getAllTeachers());
    }

    @GetMapping("/get-teachers/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable String id) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
        }
        return ResponseEntity.status(200).body(teacher);
    }

    @PostMapping("/add-teacher")
    public ResponseEntity<?> addTeacher(@Valid @RequestBody Teacher teacher, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean added = teacherService.addTeacher(teacher);
        if (!added) {
            return ResponseEntity.status(400).body(new ApiResponse("Id should be unique"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Teacher added"));
    }

    @PutMapping("/update-teacher/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable String id, @Valid @RequestBody Teacher teacher, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean updated = teacherService.updateTeacher(id, teacher);
        if (!updated) {
            return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Teacher updated"));
    }

    @DeleteMapping("/delete-teacher/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable String id) {
        boolean deleted = teacherService.deleteTeacher(id);
        if (!deleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Teacher deleted"));
    }

    @GetMapping("/filter-teachers-by-min-salary/{minSalary}")
    public ResponseEntity<?> getTeachersByMinSalary(@PathVariable double minSalary) {
        return ResponseEntity.status(200).body(teacherService.getTeachersByMinSalary(minSalary));
    }

    @GetMapping("/get-teachers-by-section/{sectionId}")
    public ResponseEntity<?> getTeachersBySection(@PathVariable String sectionId) {
        if (sectionService.getSectionById(sectionId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        return ResponseEntity.status(200).body(teacherService.getTeachersBySectionId(sectionId));
    }

    @PutMapping("/add-section-to-teacher/{teacherId}/{sectionId}")
    public ResponseEntity<?> addSectionToTeacher(@PathVariable String teacherId, @PathVariable String sectionId) {
        if (sectionService.getSectionById(sectionId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        boolean connected = teacherService.addSectionToTeacher(teacherId, sectionId);
        if (!connected) {
            return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Section added to teacher"));
    }

    @DeleteMapping("/delete-section-from-teacher/{teacherId}/{sectionId}")
    public ResponseEntity<?> removeSectionFromTeacher(@PathVariable String teacherId, @PathVariable String sectionId) {
        if (sectionService.getSectionById(sectionId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        Teacher teacher = teacherService.getTeacherById(teacherId);
        if (teacher == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Teacher not found"));
        }
        boolean removed = teacherService.removeSectionFromTeacher(teacherId, sectionId);
        if (!removed) {
            return ResponseEntity.status(400).body(new ApiResponse("Teacher dose not have this section"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Section removed from teacher"));
    }
}
