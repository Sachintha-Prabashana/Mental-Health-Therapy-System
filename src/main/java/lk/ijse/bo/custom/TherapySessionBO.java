package lk.ijse.bo.custom;

import lk.ijse.dto.TherapySessionDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface TherapySessionBO {
    void bookSession(String sessionId, String patientId, String therapistId, String programId, LocalDate date, LocalTime time) ;
    boolean rescheduleSession(String sessionId, LocalDate newDate, LocalTime newTime) ;
    boolean cancelSession(String sessionId) ;

    ArrayList<TherapySessionDTO> loadAllSessions();
    TherapySessionDTO getSessionByPatientName(String name);

}
