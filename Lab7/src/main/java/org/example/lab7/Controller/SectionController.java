package org.example.lab7.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.lab7.ApiResponse.ApiResponse;
import org.example.lab7.Model.Section;
import org.example.lab7.Service.SectionService;
import org.example.lab7.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    private final StudentService studentService;



    @GetMapping("/get-sections")
    public ResponseEntity<?> getAllSections() {
        return ResponseEntity.status(200).body(sectionService.getAllSections());
    }

    @GetMapping("/get-sections-by-id/{id}")
    public ResponseEntity<?> getSectionById(@PathVariable String id) {
        Section section = sectionService.getSectionById(id);
        if (section == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        return ResponseEntity.status(200).body(section);
    }

    @PostMapping("/add-section")
    public ResponseEntity<?> addSection(@Valid @RequestBody Section section, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean added = sectionService.addSection(section);
        if (!added) {
            return ResponseEntity.status(400).body(new ApiResponse("Id should be unique"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Section added"));
    }

    @PutMapping("/update-section/{id}")
    public ResponseEntity<?> updateSection(@PathVariable String id, @Valid @RequestBody Section section, Errors errors) {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        }
        boolean updated = sectionService.updateSection(id, section);
        if (!updated) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Section updated"));
    }

    @DeleteMapping("/delete-section/{id}")
    public ResponseEntity<?> deleteSection(@PathVariable String id) {
        boolean deleted = sectionService.deleteSection(id);
        if (!deleted) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Section deleted"));
    }

    @GetMapping("/get-section-by-student/{studentId}")
    public ResponseEntity<?> getSectionsByStudent(@PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        return ResponseEntity.status(200).body(sectionService.getSectionsByStudentId(studentId));
    }

    @PutMapping("/add-student-to-section/{studentId}/{sectionId}")
    public ResponseEntity<?> addStudentToSection( @PathVariable String studentId,@PathVariable String sectionId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        boolean added = sectionService.addStudentToSection(sectionId, studentId);
        if (!added) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Student added to section"));
    }

    @DeleteMapping("/remove-student-from-section/{studentId}/{sectionId}")
    public ResponseEntity<?> removeStudentFromSection(@PathVariable String sectionId, @PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }

        if (sectionService.getSectionById(sectionId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        boolean removed = sectionService.removeStudentFromSection(sectionId, studentId);
        if (!removed) {
            return ResponseEntity.status(400).body(new ApiResponse("Student is not in this section"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Student removed from section"));
    }

    @GetMapping("/get-number-of-student-in-section/{sectionId}")
    public ResponseEntity<?> getStudentCountInSection(@PathVariable String sectionId) {
        int count = sectionService.getStudentCountInSection(sectionId);
        if (count < 0) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse(count+""));
    }
}
