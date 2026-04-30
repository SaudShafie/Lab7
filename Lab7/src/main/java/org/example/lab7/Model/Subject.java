package org.example.lab7.Model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @NotEmpty
    @Size(min = 2)
    private String id;
    @NotEmpty
    @Size(min = 4)
    private String name;

    @NotEmpty
    @Size(min = 1)
    private String grade;
    @Min(value = 2,message = "hours should be between 2 and 4 ")
    @Max(value = 4,message = "hours should be between 2 and 4 ")
    private int hours;

    private String studentId;


}
