package org.example.lab7.Service;

import lombok.RequiredArgsConstructor;
import org.example.lab7.Model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final ArrayList<Student> students = new ArrayList<>();
    private final SectionService sectionService;

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public Student getStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public boolean addStudent(Student student) {
        if (getStudentById(student.getId()) != null) {
            return false;
        }
        students.add(student);
        return true;
    }

    public boolean updateStudent(String id, Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                student.setId(id);
                students.set(i, student);
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudent(String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                sectionService.removeStudentFromAllSections(id);
                students.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Student> getStudentsByMinAge(int minAge) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getAge() >= minAge) {
                result.add(student);
            }
        }
        return result;
    }

    public ArrayList<Student> getStudentsByClassId(String classId) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (java.util.Objects.equals(student.getClassId(), classId)) {
                result.add(student);
            }
        }
        return result;
    }

    public int countStudentsInClass(String classId) {
        int n = 0;
        for (Student student : students) {
            if (java.util.Objects.equals(student.getClassId(), classId)) {
                n++;
            }
        }
        return n;
    }

    /** Students with no class assignment (onboarding / placement queue). */
    public ArrayList<Student> getStudentsNotEnrolledInAnyClass() {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students) {
            String cid = student.getClassId();
            if (cid == null || cid.isBlank()) {
                result.add(student);
            }
        }
        return result;
    }

    public boolean addClassToStudent(String studentId, String classId) {
        Student student = getStudentById(studentId);
        if (student == null) {
            return false;
        }
        student.setClassId(classId);
        return true;
    }

    public boolean removeClassFromStudent(String studentId, String classId) {
        Student student = getStudentById(studentId);
        if (student == null) {
            return false;
        }
        if (!java.util.Objects.equals(student.getClassId(), classId)) {
            return false;
        }
        student.setClassId(null);
        return true;
    }
}
