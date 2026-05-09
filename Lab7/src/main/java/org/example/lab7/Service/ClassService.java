package org.example.lab7.Service;

import lombok.RequiredArgsConstructor;
import org.example.lab7.Model.Class;
import org.example.lab7.Model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ArrayList<Class> classes = new ArrayList<>();
    private final StudentService studentService;

    public ArrayList<Class> getAllClasses() {
        return classes;
    }

    public Class getClassById(String id) {
        for (Class schoolClass : classes) {
            if (schoolClass.getId().equals(id)) {
                return schoolClass;
            }
        }
        return null;
    }

    public boolean addClass(Class schoolClass) {
        if (getClassById(schoolClass.getId()) != null) {
            return false;
        }
        classes.add(schoolClass);
        return true;
    }

    public boolean updateClass(String id, Class schoolClass) {
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getId().equals(id)) {
                schoolClass.setId(id);
                classes.set(i, schoolClass);
                return true;
            }
        }
        return false;
    }

    public boolean deleteClass(String id) {
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getId().equals(id)) {
                for (Student s : studentService.getAllStudents()) {
                    if (java.util.Objects.equals(s.getClassId(), id)) {
                        s.setClassId(null);
                    }
                }
                classes.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Class> getClassesBySubjectName(String subjectName) {
        ArrayList<Class> result = new ArrayList<>();
        for (Class schoolClass : classes) {
            if (schoolClass.getSubjectName().equalsIgnoreCase(subjectName)) {
                result.add(schoolClass);
            }
        }
        return result;
    }

    /** Student owns {@link Student#getClassId()}; resolves at most one class. */
    public ArrayList<Class> getClassesByStudentId(String studentId) {
        ArrayList<Class> result = new ArrayList<>();
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return result;
        }
        String cid = student.getClassId();
        if (cid == null || cid.isBlank()) {
            return result;
        }
        Class schoolClass = getClassById(cid);
        if (schoolClass != null) {
            result.add(schoolClass);
        }
        return result;
    }
}
