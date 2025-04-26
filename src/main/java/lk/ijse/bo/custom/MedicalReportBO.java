package lk.ijse.bo.custom;

import lk.ijse.dto.MedicalRecordDTO;

import java.util.ArrayList;

public interface MedicalReportBO {
    String getNextMedicalRecordID();

    MedicalRecordDTO searchMedicalRecord(String id);

    ArrayList<MedicalRecordDTO> loadAllMedicalRecords();

    boolean deleteMedicalRecord(String id) throws Exception;

    boolean saveMedicalRecord(MedicalRecordDTO medicalRecordDTO);
}
