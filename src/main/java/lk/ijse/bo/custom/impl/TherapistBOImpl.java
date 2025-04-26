package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.dao.custom.TherapistDAO;
import lk.ijse.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.entity.Patient;
import lk.ijse.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAO therapistDAO = new TherapistDAOImpl();

    @Override
    public ArrayList<TherapistDTO> loadAllTherapists() throws SQLException, ClassNotFoundException {
        ArrayList<TherapistDTO> therapistDTOS = new ArrayList<>();
        ArrayList<Therapist> therapists = (ArrayList<Therapist>) therapistDAO.getAll();

        for (Therapist therapist : therapists) {
            therapistDTOS.add(
                    new TherapistDTO
                            (therapist.getTherapistID(), therapist.getTherapistName(), therapist.getSpecialization(), therapist.getContactNumber(), therapist.getMail(), therapist.getAvailability()

            ));
        }
        return therapistDTOS;
    }

    @Override
    public String getNaxtTherapistID() {
        return therapistDAO.getNextId();
    }

    @Override
    public ArrayList<Therapist> loadAllTherapistsInCombo() {
        ArrayList<Therapist> therapists = new ArrayList<>();
        try {
            List<Therapist> allTherapists = therapistDAO.getAll(); // DAO method
            therapists.addAll(allTherapists);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return therapists;
    }

    @Override
    public String getTherapistIdByName(String selectedTherapistName) {
        return therapistDAO.getTherapistIdByName(selectedTherapistName);
    }

    @Override
    public boolean saveTherapist(TherapistDTO therapistDTO) {
        return therapistDAO.save(
                new Therapist(therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(),
                        therapistDTO.getContactNumber(),
                        therapistDTO.getMail(),
                        therapistDTO.getAvailability(),
                        new ArrayList<>(),
                        new ArrayList<>())
        );

    }

    @Override
    public boolean updateTherapist(TherapistDTO therapistDTO) {
        return therapistDAO.update(
                new Therapist(therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(), therapistDTO.getSpecialization(),
                        therapistDTO.getContactNumber(),
                        therapistDTO.getMail(),
                        therapistDTO.getAvailability(),
                        new ArrayList<>(),
                        new ArrayList<>())
        );
    }

    @Override
    public boolean deleteTherapist(String therapistId) throws Exception {
        return therapistDAO.deleteByPK(therapistId);
    }




}
