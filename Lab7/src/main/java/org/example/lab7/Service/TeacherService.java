package org.example.lab7.Service;

import org.example.lab7.Model.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeacherService {
    private final ArrayList<Teacher> teachers = new ArrayList<>();

    public ArrayList<Teacher> getAllTeachers() {
        return teachers;
    }

    public Teacher getTeacherById(String id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId().equals(id)) {
                return teacher;
            }
        }
        return null;
    }

    public boolean addTeacher(Teacher teacher) {
        if (getTeacherById(teacher.getId()) != null) {
            return false;
        }
        teachers.add(teacher);
        return true;
    }

    public boolean updateTeacher(String id, Teacher teacher) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId().equals(id)) {
                teacher.setId(id);
                teachers.set(i, teacher);
                return true;
            }
        }
        return false;
    }

    public boolean deleteTeacher(String id) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getId().equals(id)) {
                teachers.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Teacher> getTeachersByMinSalary(double minSalary) {
        ArrayList<Teacher> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if (teacher.getSalary() >= minSalary) {
                result.add(teacher);
            }
        }
        return result;
    }

    public ArrayList<Teacher> getTeachersBySectionId(String sectionId) {
        ArrayList<Teacher> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if (java.util.Objects.equals(teacher.getSectionId(), sectionId)) {
                result.add(teacher);
            }
        }
        return result;
    }

    /** Teachers not assigned to any section (staffing follow-up). */
    public ArrayList<Teacher> getTeachersWithoutSection() {
        ArrayList<Teacher> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            String sid = teacher.getSectionId();
            if (sid == null || sid.isBlank()) {
                result.add(teacher);
            }
        }
        return result;
    }

    /** Teacher owns {@link Teacher#getSectionId()}; at most one teacher per section. */
    public boolean addSectionToTeacher(String teacherId, String sectionId) {
        Teacher teacher = getTeacherById(teacherId);
        if (teacher == null) {
            return false;
        }
        for (Teacher t : teachers) {
            if (java.util.Objects.equals(t.getSectionId(), sectionId) && !t.getId().equals(teacherId)) {
                t.setSectionId(null);
            }
        }
        teacher.setSectionId(sectionId);
        return true;
    }

    public boolean removeSectionFromTeacher(String teacherId, String sectionId) {
        Teacher teacher = getTeacherById(teacherId);
        if (teacher == null) {
            return false;
        }
        if (!java.util.Objects.equals(teacher.getSectionId(), sectionId)) {
            return false;
        }
        teacher.setSectionId(null);
        return true;
    }

    public void clearSectionIdReferences(String sectionId) {
        for (Teacher t : teachers) {
            if (java.util.Objects.equals(t.getSectionId(), sectionId)) {
                t.setSectionId(null);
            }
        }
    }
}
