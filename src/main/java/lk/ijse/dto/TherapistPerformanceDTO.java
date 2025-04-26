package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapistPerformanceDTO {
    private String therapistId;
    private String therapistName;
    private int sessions;
    private int totalPatients;

}
