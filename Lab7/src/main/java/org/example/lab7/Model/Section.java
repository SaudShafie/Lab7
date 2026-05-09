package org.example.lab7.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Section {
    @NotEmpty
    @Size(min = 2)
    private String id;

    /** Owning side: at most one student in this section. */
    private String studentId;
}
