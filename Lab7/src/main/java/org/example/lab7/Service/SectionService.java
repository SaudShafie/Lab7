package org.example.lab7.Service;

import org.example.lab7.Model.Section;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SectionService {
    private final ArrayList<Section> sections = new ArrayList<>();

    public ArrayList<Section> getAllSections() {
        return sections;
    }

    public Section getSectionById(String id) {
        for (Section section : sections) {
            if (section.getId().equals(id)) {
                return section;
            }
        }
        return null;
    }

    public boolean addSection(Section section) {
        if (getSectionById(section.getId()) != null) {
            return false;
        }
        sections.add(section);
        return true;
    }

    public boolean updateSection(String id, Section section) {
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).getId().equals(id)) {
                section.setId(id);
                sections.set(i, section);
                return true;
            }
        }
        return false;
    }

    public boolean deleteSection(String id) {
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).getId().equals(id)) {
                sections.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Section> getSectionsByStudentId(String studentId) {
        ArrayList<Section> result = new ArrayList<>();
        for (Section section : sections) {
            if (section.getStudentIds().contains(studentId)) {
                result.add(section);
            }
        }
        return result;
    }

    public boolean addStudentToSection(String sectionId, String studentId) {
        Section section = getSectionById(sectionId);
        if (section == null) {
            return false;
        }
        if (!section.getStudentIds().contains(studentId)) {
            section.addStudentId(studentId);
        }
        return true;
    }

    public boolean removeStudentFromSection(String sectionId, String studentId) {
        Section section = getSectionById(sectionId);
        if (section == null) {
            return false;
        }
        return section.getStudentIds().remove(studentId);
    }

    public int getStudentCountInSection(String sectionId) {
        Section section = getSectionById(sectionId);
        if (section == null) {
            return -1;
        }
        return section.getStudentIds().size();
    }
}
