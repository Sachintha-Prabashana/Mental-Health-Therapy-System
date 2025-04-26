package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TherapistProgramDTO {
    private String therapistId;
    private String programId;
    private LocalDate assignedDate;

}
