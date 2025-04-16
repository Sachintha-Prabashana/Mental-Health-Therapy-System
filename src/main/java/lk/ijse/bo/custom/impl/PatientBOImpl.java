package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PatientBO;
import lk.ijse.dao.custom.PatientDAO;
import lk.ijse.dao.custom.impl.PatientDAOImpl;
import lk.ijse.dto.PatientDTO;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.entity.Patient;
import lk.ijse.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;

public class PatientBOImpl implements PatientBO {

    private final PatientDAO patientDAO = new PatientDAOImpl();
    @Override
    public boolean savePatient(PatientDTO patientDTO) {
        return patientDAO.save(new Patient(
                patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getContactInfo(),
                patientDTO.getGender(),
                patientDTO.getMedicalHistory(),
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    @Override
    public boolean updatePatient(PatientDTO patientDTO) {
        return false;
    }

    @Override
    public boolean deletePatient(String id) throws Exception {
        return false;
    }

    @Override
    public ArrayList<PatientDTO> loadAllPatient() throws SQLException, ClassNotFoundException {
        ArrayList<PatientDTO> patientDTOS = new ArrayList<>();
        ArrayList<Patient> patients = (ArrayList<Patient>) patientDAO.getAll();

        for (Patient patient : patients) {
            patientDTOS.add(
                    new PatientDTO(
                          patient.getId(),
                          patient.getName(),
                          patient.getContactInfo(),
                          patient.getGender(),
                          patient.getMedicalHistory()
                    ));

        }
        return patientDTOS;
    }

    @Override
    public String getNextPatientID() throws SQLException, ClassNotFoundException {
        return patientDAO.getNextId();
    }
}
