package org.example.lab7.Service;

import org.example.lab7.Model.Class;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClassService {
    private final ArrayList<Class> classes = new ArrayList<>();

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
        schoolClass.refreshStudentCount();
        classes.add(schoolClass);
        return true;
    }

    public boolean updateClass(String id, Class schoolClass) {
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getId().equals(id)) {
                schoolClass.setId(id);
                schoolClass.refreshStudentCount();
                classes.set(i, schoolClass);
                return true;
            }
        }
        return false;
    }

    public boolean deleteClass(String id) {
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).getId().equals(id)) {
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

    public ArrayList<Class> getClassesByStudentId(String studentId) {
        ArrayList<Class> result = new ArrayList<>();
        for (Class schoolClass : classes) {
            if (schoolClass.getStudentIds().contains(studentId)) {
                result.add(schoolClass);
            }
        }
        return result;
    }

    public boolean addStudentToClass(String classId, String studentId) {
        Class schoolClass = getClassById(classId);
if (schoolClass==null) return false;
        schoolClass.addStudentId(studentId);
        return true;
    }

    public boolean removeStudentFromClass(String classId, String studentId) {
        Class schoolClass = getClassById(classId);
        if (schoolClass == null) {
            return false;
        }
        return schoolClass.removeStudentId(studentId);
    }
}
