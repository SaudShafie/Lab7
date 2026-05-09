package org.example.lab7.Service;

import lombok.RequiredArgsConstructor;
import org.example.lab7.Model.Section;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final ArrayList<Section> sections = new ArrayList<>();
    private final TeacherService teacherService;

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
                teacherService.clearSectionIdReferences(id);
                sections.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Section> getSectionsByStudentId(String studentId) {
        ArrayList<Section> result = new ArrayList<>();
        for (Section section : sections) {
            if (java.util.Objects.equals(section.getStudentId(), studentId)) {
                result.add(section);
            }
        }
        return result;
    }

    /** Section owns {@link Section#getStudentId()}; student appears in at most one section. */
    public boolean addStudentToSection(String sectionId, String studentId) {
        Section section = getSectionById(sectionId);
        if (section == null) {
            return false;
        }
        for (Section s : sections) {
            if (java.util.Objects.equals(s.getStudentId(), studentId)) {
                s.setStudentId(null);
            }
        }
        section.setStudentId(studentId);
        return true;
    }

    public boolean removeStudentFromSection(String sectionId, String studentId) {
        Section section = getSectionById(sectionId);
        if (section == null) {
            return false;
        }
        if (!java.util.Objects.equals(section.getStudentId(), studentId)) {
            return false;
        }
        section.setStudentId(null);
        return true;
    }

    public int getStudentCountInSection(String sectionId) {
        Section section = getSectionById(sectionId);
        if (section == null) {
            return -1;
        }
        String sid = section.getStudentId();
        return (sid == null || sid.isBlank()) ? 0 : 1;
    }

    public void removeStudentFromAllSections(String studentId) {
        for (Section section : sections) {
            if (java.util.Objects.equals(section.getStudentId(), studentId)) {
                section.setStudentId(null);
            }
        }
    }
}
