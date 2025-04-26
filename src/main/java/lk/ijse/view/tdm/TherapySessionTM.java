package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapySessionTM {
    private String sessionId;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private String patientId;
    private String programId;
//    private String programName;
    private String therapistId;
//    private String therapistName;
}
