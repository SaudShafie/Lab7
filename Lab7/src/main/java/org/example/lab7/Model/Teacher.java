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
public class Teacher {
    @NotEmpty
    @Size(min = 2)
    private String id;
    @NotEmpty
    @Size(min = 4)
    private String name;
    @NotNull
    @Min(0)
    private double salary;
    private ArrayList<String> sectionsId = new ArrayList<>();

    public ArrayList<String> getSectionsId() {
        if (sectionsId==null)sectionsId=new ArrayList<>();

        return sectionsId;
    }

    public void addSectionsId(String id) {
        if (sectionsId==null)sectionsId=new ArrayList<>();
        sectionsId.add(id);
    }
}
