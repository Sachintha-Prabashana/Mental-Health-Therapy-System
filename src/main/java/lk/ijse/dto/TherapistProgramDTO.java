package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TherapistProgramDTO {
    private TherapistDTO therapist;
    private TherapyProgramDTO program;
}
