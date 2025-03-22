package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.dao.custom.TherapistDAO;
import lk.ijse.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAO therapistDAO = new TherapistDAOImpl();

    @Override
    public ArrayList<TherapistDTO> loadAllTherapists() throws SQLException, ClassNotFoundException {
        ArrayList<TherapistDTO> therapistDTOS = new ArrayList<>();
        ArrayList<Therapist> therapists = (ArrayList<Therapist>) therapistDAO.getAll();

        for (Therapist therapist : therapists) {
            therapistDTOS.add(
                    new TherapistDTO
                            (therapist.getId(), therapist.getName(), therapist.getSpecialization(), therapist.getAvailability()

            ));
        }
        return therapistDTOS;
    }

    @Override
    public boolean saveTherapist(TherapistDTO therapistDTO) {
        return therapistDAO.save(
                new Therapist(therapistDTO.getId(), therapistDTO.getName(), therapistDTO.getSpecialization(), therapistDTO.getAvailability(), null)
        );

    }

    @Override
    public boolean updateTherapist(TherapistDTO therapistDTO) {
        return therapistDAO.update(
                new Therapist(therapistDTO.getId(), therapistDTO.getName(), therapistDTO.getSpecialization(), therapistDTO.getAvailability(), null)
        );
    }

    @Override
    public boolean deleteTherapist(String therapistId) throws Exception {
        return therapistDAO.deleteByPK(therapistId);
    }




}
