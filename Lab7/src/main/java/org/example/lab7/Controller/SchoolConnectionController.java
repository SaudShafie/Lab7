package org.example.lab7.Controller;

import lombok.RequiredArgsConstructor;
import org.example.lab7.ApiResponse.ApiResponse;
import org.example.lab7.Service.ClassService;
import org.example.lab7.Service.SchoolConnectionService;
import org.example.lab7.Service.StudentService;
import org.example.lab7.Service.SubjectService;
import org.example.lab7.dto.SectionOverviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/connect")
@RequiredArgsConstructor
public class SchoolConnectionController {
    private final SchoolConnectionService schoolConnectionService;
    private final StudentService studentService;
    private final ClassService classService;
    private final SubjectService subjectService;



    @PutMapping("/enroll-student-in-class/{studentId}/{classId}")
    public ResponseEntity<?> enrollStudentInClass(@PathVariable String studentId, @PathVariable String classId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found "));
        }
        if (classService.getClassById(classId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found " ));
        }
        boolean ok = schoolConnectionService.enrollStudentInClass(studentId, classId);
        if (!ok) {
            return ResponseEntity.status(400).body(new ApiResponse("Enrollment failed"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Student enrolled in class"));
    }

    @PutMapping("/add-subject-to-student/{subjectId}/{studentId}")
    public ResponseEntity<?> assignSubjectToStudent(@PathVariable String subjectId, @PathVariable String studentId) {
        if (studentService.getStudentById(studentId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Student not found"));
        }
        if (subjectService.getSubjectById(subjectId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Subject not found "));
        }
        boolean ok = schoolConnectionService.assignSubjectToStudent(subjectId, studentId);
        if (!ok) {
            return ResponseEntity.status(400).body(new ApiResponse("Assignment failed"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Subject assigned to student"));
    }

    @PutMapping("/update-class-subject-name/{classId}/{subjectName}")
    public ResponseEntity<?> setClassSubjectName(@PathVariable String classId, @PathVariable String subjectName) {
        if (classService.getClassById(classId) == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Class not found "));
        }
        boolean updated = schoolConnectionService.setClassSubjectName(classId, subjectName);
        if (!updated) {
            return ResponseEntity.status(400).body(new ApiResponse("Invalid subjectName (it can not be a blank or null)"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Class subject name updated"));
    }

    /**
     * One request for a section’s snapshot: section row, enrolled student (if any), assigned teacher (if any).
     */
    @GetMapping("/section-overview/{sectionId}")
    public ResponseEntity<?> getSectionOverview(@PathVariable String sectionId) {
        SectionOverviewDto overview = schoolConnectionService.getSectionOverview(sectionId);
        if (overview == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Section not found"));
        }
        return ResponseEntity.status(200).body(overview);
    }
}
