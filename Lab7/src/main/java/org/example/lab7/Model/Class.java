package org.example.lab7.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @NotEmpty
    @Size(min = 2)
    private String id;
    @NotEmpty
    @Size(min = 3)
    private String subjectName;
    private ArrayList<String> studentIds ;
    @Min(value = 0,message = "student Count should be 0")
    @Max(value = 0,message = "student Count should be 0")
    private int studentCount;

    public void addStudentId(String id) {
        if (studentIds==null){
            studentIds=new ArrayList<>();
        }
        if (!studentIds.contains(id)) {
            studentIds.add(id);
            studentCount = studentIds.size();
        }
    }

    public boolean removeStudentId(String id) {
        if (studentIds==null){
            studentIds=new ArrayList<>();
        }
        boolean removed = studentIds.remove(id);
        if (removed) {
            studentCount = studentIds.size();
        }
        return removed;
    }

    public void refreshStudentCount() {
        studentCount = (studentIds==null)?0:studentIds.size();
    }
}
