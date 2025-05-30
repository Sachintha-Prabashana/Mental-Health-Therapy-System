package lk.ijse.dao.custom;

import lk.ijse.entity.Therapist;
import lk.ijse.entity.User;

public interface TherapistDAO extends CrudDAO<Therapist> {
    Therapist getById(String therapistId);

    String getTherapistIdByName(String selectedTherapistName);
}
