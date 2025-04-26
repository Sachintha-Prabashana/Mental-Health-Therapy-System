package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.TherapySessionBO;
import lk.ijse.dao.custom.PatientDAO;
import lk.ijse.dao.custom.TherapistDAO;
import lk.ijse.dao.custom.TherapyProgramDAO;
import lk.ijse.dao.custom.TherapySessionDAO;
import lk.ijse.dao.custom.impl.PatientDAOImpl;
import lk.ijse.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.dto.TherapySessionDTO;
import lk.ijse.entity.Therapist;
import lk.ijse.entity.TherapySession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {
    private final TherapySessionDAO sessionDAO = new TherapySessionDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();
    private final TherapistDAO therapistDAO = new TherapistDAOImpl();
    private final TherapyProgramDAO programDAO = new TherapyProgramDAOImpl();
    private final PaymentBO paymentBO = new PaymentBOImpl();

    public boolean bookSession(TherapySessionDTO sessionDTO) {

        // Check for conflicts
        List<TherapySession> existing = sessionDAO.findByTherapistAndTime(sessionDTO.getTherapistId(),sessionDTO.getSessionDate(),sessionDTO.getStartTime(),sessionDTO.getEndTime());
        if (!existing.isEmpty()) {
            System.out.println("Therapist is not available at this time.");
            return false;
        }

        TherapySession session = new TherapySession();
        session.setSessionId(sessionDTO.getSessionId());
        session.setSessionDate(sessionDTO.getSessionDate());
        session.setStartTime(sessionDTO.getStartTime());
        session.setEndTime(sessionDTO.getEndTime());
        session.setStatus("BOOKED");
        session.setPatient(patientDAO.findById(sessionDTO.getPatientId()));
        session.setTherapist(therapistDAO.findById(sessionDTO.getTherapistId()));
        session.setTherapyProgram(programDAO.findById(sessionDTO.getProgramId()));

        boolean sessionSaved = sessionDAO.save(session);
        if (!sessionSaved) {
//            System.out.println("Failed to save therapy session.");
            return false;
        }

//         Optional: Set availability manually if you want to reflect it elsewhere
         Therapist therapist = session.getTherapist();
        therapist.setAvailability("NO");
        therapistDAO.update(therapist);

//        System.out.println("Session booked successfully.");
        return true;
    }

    // TherapySessionBOImpl.java
    @Override
    public boolean rescheduleSession(TherapySessionDTO sessionDTO) {
        TherapySession session = sessionDAO.findById(sessionDTO.getSessionId());
        if (session == null) {
            System.out.println("Session not found with ID: " + sessionDTO.getSessionId());
            return false;
        }

        // Check for time conflicts
        List<TherapySession> conflicts = sessionDAO.findByTherapistAndTime(
                sessionDTO.getTherapistId(),
                sessionDTO.getSessionDate(),
                sessionDTO.getStartTime(),
                sessionDTO.getEndTime()
        );

        // Exclude current session from conflict check
        conflicts.removeIf(s -> s.getSessionId().equals(sessionDTO.getSessionId()));

        if (!conflicts.isEmpty()) {
            System.out.println("Therapist is not available at the new time.");
            return false;
        }

        // Update session details
        session.setSessionDate(sessionDTO.getSessionDate());
        session.setStartTime(sessionDTO.getStartTime());
        session.setPatient(patientDAO.findById(sessionDTO.getPatientId()));
        session.setTherapist(therapistDAO.findById(sessionDTO.getTherapistId()));
        session.setTherapyProgram(programDAO.findById(sessionDTO.getProgramId()));

        return sessionDAO.update(session);
    }

    @Override
    public String getNextSessionID() {
        return sessionDAO.getNextId();
    }


    public boolean cancelSession(String sessionId) {
        TherapySession session = sessionDAO.findById(sessionId);
        session.setStatus("CANCELLED");
        sessionDAO.save(session);

        return true;
    }

    @Override
    public ArrayList<TherapySessionDTO> loadAllSessions() {
        ArrayList<TherapySessionDTO> sessionDTOS = new ArrayList<>();
        ArrayList<TherapySession> sessions = (ArrayList<TherapySession>) sessionDAO.getAll();

        for (TherapySession session : sessions) {
            sessionDTOS.add(
                    new TherapySessionDTO(
                            session.getSessionId(),
                            session.getSessionDate(),
                            session.getStartTime(),
                            session.getEndTime(),
                            session.getStatus(),
                            session.getPatient().getId(),
                            session.getTherapyProgram().getProgramId(),
                            session.getTherapist().getTherapistID()
                    ));

        }
        return sessionDTOS;
    }

    @Override
    public TherapySessionDTO getSessionByPatientName(String name) {
        TherapySession ts = sessionDAO.getSessionByPatientName(name);

        if (ts != null) {
            return new TherapySessionDTO(
                    ts.getSessionId(),
                    ts.getSessionDate(),
                    ts.getStartTime(),
                    ts.getEndTime(),
                    ts.getStatus(),
                    ts.getPatient().getId(),
                    ts.getTherapyProgram().getProgramId(),
                    ts.getTherapist().getTherapistID()
            );
        } else {
            return null;
        }
    }

}
