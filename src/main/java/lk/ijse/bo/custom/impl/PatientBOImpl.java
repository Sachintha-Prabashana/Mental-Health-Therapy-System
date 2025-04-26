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
import java.util.List;

public class PatientBOImpl implements PatientBO {



    private final PatientDAO patientDAO = new PatientDAOImpl();
    @Override
    public boolean savePatient(PatientDTO patientDTO) {
        return patientDAO.save(new Patient(
                patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getContactInfo(),
                patientDTO.getGender(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    @Override
    public boolean updatePatient(PatientDTO patientDTO) {
        return  patientDAO.update(new Patient(
                patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getContactInfo(),
                patientDTO.getGender(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    @Override
    public boolean deletePatient(String id) throws Exception {
        return patientDAO.deleteByPK(id);
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
                          patient.getGender()
                    ));

        }
        return patientDTOS;
    }

    @Override
    public String getNextPatientID() throws SQLException, ClassNotFoundException {
        return patientDAO.getNextId();
    }

    @Override
    public Patient findById(String id) {
        return null;
    }

    @Override
    public ArrayList<Patient> loadAllpatientsInCombo() {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            List<Patient> allPatients = patientDAO.getAll(); // DAO method
            patients.addAll(allPatients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }



}
