package org.example.lab7.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @NotEmpty
    @Size(min = 2)
    private String id;
    @NotEmpty
    @Size(min = 4)
    private String name;
    @NotNull
    @Min(value = 7,message = "Student should be over 7")
    private int age;

    private ArrayList<String> subjectsId = new ArrayList<>();
    private ArrayList<String> classesId = new ArrayList<>();

    public void addSubjectsId(String id) {
        if(subjectsId==null)subjectsId=new ArrayList<>();
        subjectsId.add(id);
    }

    public ArrayList<String> getClassesId() {
        if (classesId==null)classesId=new ArrayList<>();

        return classesId;
    }

    public ArrayList<String> getSubjectsId() {
        if(subjectsId==null)subjectsId=new ArrayList<>();

        return subjectsId;
    }

    public void addClassesId(String id) {
        if (classesId==null)classesId=new ArrayList<>();
        classesId.add(id);
    }
}
