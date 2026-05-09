package org.example.lab7.dto;

import org.example.lab7.Model.Section;
import org.example.lab7.Model.Student;
import org.example.lab7.Model.Teacher;

/**
 * Single response for admin dashboards: one section with its linked student and teacher, if any.
 */
public record SectionOverviewDto(Section section, Student student, Teacher teacher) {
}
