package org.example.lab7.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Min(value = 7, message = "Student should be over 7")
    private int age;

    /** Owning side: at most one class per student. */
    private String classId;
}
