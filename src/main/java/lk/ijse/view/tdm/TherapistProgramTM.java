package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TherapistProgramTM {
    private String therapistId;
    private String programId;
    private LocalDate assignedDate;

}
