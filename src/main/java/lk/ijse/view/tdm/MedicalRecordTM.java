package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MedicalRecordTM {
    private String id;
    private LocalDate recordDate;
    private String diagnosis;
    private String medicalHistory;
    private String therapyNotes;

    // Patient information
    private String patientId;

}
