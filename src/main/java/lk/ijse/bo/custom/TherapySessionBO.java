package lk.ijse.bo.custom;

import lk.ijse.dto.TherapySessionDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface TherapySessionBO {
    boolean bookSession(TherapySessionDTO sessionDTO);
    boolean cancelSession(String sessionId);
    ArrayList<TherapySessionDTO> loadAllSessions();
    TherapySessionDTO getSessionByPatientName(String name);

    boolean rescheduleSession(TherapySessionDTO updatedSession);

    String getNextSessionID();
}
