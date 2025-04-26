package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TherapistProgramBO;
import lk.ijse.bo.custom.TherapyProgramsBO;
import lk.ijse.dao.custom.TherapistProgramDAO;
import lk.ijse.dao.custom.impl.TherapistProgramDAOImpl;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.dto.TherapistProgramDTO;
import lk.ijse.entity.Therapist;
import lk.ijse.entity.TherapistProgram;
import lk.ijse.entity.TherapistProgramId;
import lk.ijse.entity.TherapyProgram;
import lk.ijse.view.tdm.TherapistProgramTM;

import java.util.ArrayList;
import java.util.List;

public class TherapistProgramBOImpl implements TherapistProgramBO {
    private final TherapistProgramDAO therapistProgramDAO = new TherapistProgramDAOImpl();
    @Override
    public boolean saveAssignment(TherapistProgramDTO dto) throws Exception {

        Therapist therapist = new Therapist();
        therapist.setTherapistID(dto.getTherapistId());

        TherapyProgram program = new TherapyProgram();
        program.setProgramId(dto.getProgramId());

        TherapistProgram entity = new TherapistProgram();
        entity.setId(new TherapistProgramId(dto.getTherapistId(), dto.getProgramId()));
        entity.setTherapist(therapist);
        entity.setTherapyProgram(program);
        entity.setAssignedDate(dto.getAssignedDate());

        return therapistProgramDAO.save(entity);
    }

    @Override
    public ArrayList<TherapistProgramDTO> loadAllData() {
        ArrayList<TherapistProgramDTO> therapistProgramDTOS = new ArrayList<>();
        ArrayList<TherapistProgram> therapistPrograms = (ArrayList<TherapistProgram>) therapistProgramDAO.getAll();

        for (TherapistProgram tp : therapistPrograms) {
            therapistProgramDTOS.add(
                    new TherapistProgramDTO(
                            tp.getTherapyProgram().getProgramId(),
                            tp.getTherapist().getTherapistID(),
                            tp.getAssignedDate()
                    )
            );
        }

        return therapistProgramDTOS;
    }

    @Override
    public List<TherapistDTO> getTherapistsByProgram(String programId) {
        List<Therapist> therapistList = therapistProgramDAO.getTherapistsByProgramId(programId);
        List<TherapistDTO> dtoList = new ArrayList<>();

        for (Therapist therapist : therapistList) {
            dtoList.add(new TherapistDTO(
                    therapist.getTherapistID(),
                    therapist.getTherapistName(),
                    therapist.getSpecialization(),
                    therapist.getContactNumber(),
                    therapist.getMail(),
                    therapist.getAvailability()
                    // add other fields as needed
            ));
        }
        return dtoList;
    }


}
