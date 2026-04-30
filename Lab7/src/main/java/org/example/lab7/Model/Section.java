package org.example.lab7.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Section {
    @NotEmpty
    @Size(min = 2)
    private String id;
    private ArrayList<String> studentIds = new ArrayList<>();

    public ArrayList<String> getStudentIds() {
        if (studentIds==null)studentIds=new ArrayList<>();
        return studentIds;
    }

    public void addStudentId(String id) {
        if (studentIds==null)studentIds=new ArrayList<>();

        studentIds.add(id);
    }

}
