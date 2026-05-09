package org.example.lab7.Service;

import lombok.RequiredArgsConstructor;
import org.example.lab7.Model.Class;
import org.example.lab7.Model.Section;
import org.example.lab7.Model.Student;
import org.example.lab7.Model.Subject;
import org.example.lab7.Model.Teacher;
import org.example.lab7.dto.SectionOverviewDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SchoolConnectionService {
    private final StudentService studentService;
    private final ClassService classService;
    private final SubjectService subjectService;
    private final SectionService sectionService;
    private final TeacherService teacherService;

    public boolean enrollStudentInClass(String studentId, String classId) {
        Student student = studentService.getStudentById(studentId);
        Class schoolClass = classService.getClassById(classId);
        if (student == null || schoolClass == null) {
            return false;
        }
        student.setClassId(classId);
        return true;
    }

    public boolean assignSubjectToStudent(String subjectId, String studentId) {
        Subject subject = subjectService.getSubjectById(subjectId);
        Student student = studentService.getStudentById(studentId);
        if (subject == null || student == null) {
            return false;
        }
        subject.setStudentId(studentId);
        return true;
    }

    public boolean setClassSubjectName(String classId, String subjectName) {
        Class schoolClass = classService.getClassById(classId);
        if (subjectName == null || subjectName.isBlank()) {
            return false;
        }
        if (schoolClass == null) {
            return false;
        }
        schoolClass.setSubjectName(subjectName);
        return true;
    }

    /** Section plus optional student and teacher for dashboards. */
    public SectionOverviewDto getSectionOverview(String sectionId) {
        Section section = sectionService.getSectionById(sectionId);
        if (section == null) {
            return null;
        }
        Student student = null;
        String enrolledId = section.getStudentId();
        if (enrolledId != null && !enrolledId.isBlank()) {
            student = studentService.getStudentById(enrolledId);
        }
        Teacher teacher = null;
        ArrayList<Teacher> forSection = teacherService.getTeachersBySectionId(sectionId);
        if (!forSection.isEmpty()) {
            teacher = forSection.get(0);
        }
        return new SectionOverviewDto(section, student, teacher);
    }
}
