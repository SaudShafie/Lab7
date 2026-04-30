package org.example.lab7.Service;

import lombok.RequiredArgsConstructor;
import org.example.lab7.Model.Class;
import org.example.lab7.Model.Student;
import org.example.lab7.Model.Subject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolConnectionService {
    private final StudentService studentService;
    private final ClassService classService;
    private final SubjectService subjectService;



    public boolean enrollStudentInClass(String studentId, String classId) {
        Student student = studentService.getStudentById(studentId);
        Class schoolClass = classService.getClassById(classId);


        if (!student.getClassesId().contains(classId)) {
            student.addClassesId(classId);
        }
        schoolClass.addStudentId(studentId);
        return true;
    }

    public boolean assignSubjectToStudent(String subjectId, String studentId) {
        Subject subject = subjectService.getSubjectById(subjectId);
        Student student = studentService.getStudentById(studentId);
if (subject==null||student==null)return false;


        subject.setStudentId(studentId);
        if (!student.getSubjectsId().contains(subjectId)) {
            student.addSubjectsId(subjectId);
        }
        return true;
    }

    public boolean setClassSubjectName(String classId, String subjectName) {
        Class schoolClass = classService.getClassById(classId);
        if (subjectName == null || subjectName.isBlank()) {
            return false;
        }

        schoolClass.setSubjectName(subjectName);
        return true;
    }
}
