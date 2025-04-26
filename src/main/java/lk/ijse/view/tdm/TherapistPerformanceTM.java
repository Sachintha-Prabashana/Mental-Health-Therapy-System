package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapistPerformanceTM {
    private String therapistId;
    private String therapistName;
    private int sessions;
    private int totalPatients;
}
