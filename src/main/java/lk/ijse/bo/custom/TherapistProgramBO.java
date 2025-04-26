package lk.ijse.bo.custom;

import lk.ijse.dto.TherapistDTO;
import lk.ijse.dto.TherapistProgramDTO;


import java.util.ArrayList;
import java.util.List;

public interface TherapistProgramBO {
    boolean saveAssignment(TherapistProgramDTO assignment) throws Exception;

    ArrayList<TherapistProgramDTO> loadAllData();
    List<TherapistDTO> getTherapistsByProgram(String programId) ;

}
