package lk.ijse.bo;

import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.dao.custom.TherapistDAO;
import lk.ijse.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.entity.Therapist;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAO therapistDAO = new TherapistDAOImpl();

    @Override
    public boolean saveTherapist(TherapistDTO therapistDTO) {
        return therapistDAO.save(
                new Therapist(therapistDTO.getId(), therapistDTO.getName(), therapistDTO.getSpecialization(), therapistDTO.getAvailability(), null)
        );

    }
}
