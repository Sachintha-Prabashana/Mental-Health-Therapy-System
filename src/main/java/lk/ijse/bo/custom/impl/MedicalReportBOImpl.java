package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.MedicalReportBO;
import lk.ijse.dao.custom.MedicalRecordDAO;
import lk.ijse.dao.custom.PatientDAO;
import lk.ijse.dao.custom.impl.MedicalRecordDAOImpl;
import lk.ijse.dao.custom.impl.PatientDAOImpl;
import lk.ijse.dto.MedicalRecordDTO;
import lk.ijse.entity.MedicalRecord;
import lk.ijse.entity.Patient;

import java.util.ArrayList;
import java.util.List;

public class MedicalReportBOImpl implements MedicalReportBO {

    private final MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();
    @Override
    public String getNextMedicalRecordID() {
        return medicalRecordDAO.getNextId();
    }

    @Override
    public MedicalRecordDTO searchMedicalRecord(String id) {
        MedicalRecord entity = medicalRecordDAO.search(id);
        if (entity != null) {
            return new MedicalRecordDTO(
                    entity.getId(),
                    entity.getRecordDate(),
                    entity.getDiagnosis(),
                    entity.getMedicalHistory(),
                    entity.getTherapyNotes(),
                    entity.getPatient().getId()
            );
        }
        return null;
    }

    @Override
    public ArrayList<MedicalRecordDTO> loadAllMedicalRecords() {
        List<MedicalRecord> entities = medicalRecordDAO.getAll();
        ArrayList<MedicalRecordDTO> dtoList = new ArrayList<>();
        for (MedicalRecord entity : entities) {
            dtoList.add(new MedicalRecordDTO(
                    entity.getId(),
                    entity.getRecordDate(),
                    entity.getDiagnosis(),
                    entity.getMedicalHistory(),
                    entity.getTherapyNotes(),
                    entity.getPatient().getId()
            ));
        }
        return dtoList;
    }

    @Override
    public boolean deleteMedicalRecord(String id) throws Exception {
        return medicalRecordDAO.deleteByPK(id);
    }

    @Override
    public boolean saveMedicalRecord(MedicalRecordDTO dto) {
        // First, retrieve the Patient entity using patientDAO
        Patient patient = patientDAO.search(dto.getPatientId());

        if (patient == null) {
            // Patient not found; cannot save the record
            return false;
        }

        // Create the MedicalRecord entity with the full Patient object
        MedicalRecord entity = new MedicalRecord(
                dto.getId(),
                dto.getRecordDate(),
                dto.getDiagnosis(),
                dto.getMedicalHistory(),
                dto.getTherapyNotes(),
                patient
        );

        // Save the entity through the DAO
        return medicalRecordDAO.save(entity);
    }
}
