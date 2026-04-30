package org.example.lab7.Service;

import org.example.lab7.Model.Subject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubjectService {
    private final ArrayList<Subject> subjects = new ArrayList<>();

    public ArrayList<Subject> getAllSubjects() {
        return subjects;
    }

    public Subject getSubjectById(String id) {
        for (Subject subject : subjects) {
            if (subject.getId().equals(id)) {
                return subject;
            }
        }
        return null;
    }

    public boolean addSubject(Subject subject) {
        if (getSubjectById(subject.getId()) != null) {
            return false;
        }
        subjects.add(subject);
        return true;
    }

    public boolean updateSubject(String id, Subject subject) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getId().equals(id)) {
                subject.setId(id);
                subjects.set(i, subject);
                return true;
            }
        }
        return false;
    }

    public boolean deleteSubject(String id) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getId().equals(id)) {
                subjects.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Subject> getSubjectsByGrade(String grade) {
        ArrayList<Subject> result = new ArrayList<>();
        for (Subject subject : subjects) {
            if (subject.getGrade().equalsIgnoreCase(grade)) {
                result.add(subject);
            }
        }
        return result;
    }

    public ArrayList<Subject> getSubjectsByHours(int hours) {
        ArrayList<Subject> result = new ArrayList<>();
        for (Subject subject : subjects) {
            if (subject.getHours() == hours) {
                result.add(subject);
            }
        }
        return result;
    }

    public ArrayList<Subject> getSubjectsByStudentId(String studentId) {
        ArrayList<Subject> result = new ArrayList<>();
        for (Subject subject : subjects) {
            if (subject.getStudentId().equals(studentId)) {
                result.add(subject);
            }
        }
        return result;
    }

    public boolean assignSubjectToStudent(String subjectId, String studentId) {
        Subject subject = getSubjectById(subjectId);
        if (subject == null) {
            return false;
        }
        subject.setStudentId(studentId);
        return true;
    }
}
