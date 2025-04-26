package lk.ijse.dao.custom;

import lk.ijse.entity.Therapist;
import lk.ijse.entity.TherapistProgram;

import java.util.List;

public interface TherapistProgramDAO extends CrudDAO<TherapistProgram> {
    boolean save(TherapistProgram entity);
    List<Therapist> getTherapistsByProgramId(String programId) ;

}
