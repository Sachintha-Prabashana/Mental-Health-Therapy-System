package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MedicalRecordDTO {
    private String id;
    private LocalDate recordDate;
    private String diagnosis;
    private String medicalHistory;
    private String therapyNotes;

    // Patient information
    private String patientId;
}
