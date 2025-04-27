package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.TherapySessionDAO;
import lk.ijse.entity.Therapist;
import lk.ijse.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    @Override
    public boolean save(TherapySession entity) {
        Session s = factoryConfiguration.getSession();
        Transaction tx = s.beginTransaction();
        s.saveOrUpdate(entity);
        tx.commit();
        s.close();
        return true;
    }

    @Override
    public boolean update(TherapySession entity) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        return false;
    }

    @Override
    public List<TherapySession> getAll() {
        Session session = factoryConfiguration.getSession();
        Query<TherapySession> query = session.createQuery("FROM TherapySession ", TherapySession.class);
        ArrayList<TherapySession> sessions = (ArrayList<TherapySession>) query.list();
        return sessions;
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        // Get the last user ID from the database
        String lastId = session.createQuery("SELECT s.id FROM TherapySession s ORDER BY s.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
            return String.format("S00-%03d", numericPart);
        } else {
            return "S00-001"; // First user ID
        }
    }

    @Override
    public TherapySession findById(String sessionId) {
        Session s = factoryConfiguration.getSession();
        TherapySession ts = s.get(TherapySession.class, sessionId);
        s.close();
        return ts;
    }

    @Override
    public List<TherapySession> findByTherapistAndTime(String therapistId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Session session = factoryConfiguration.getSession();
        try {
            Query<TherapySession> query = session.createQuery(
                    "FROM TherapySession WHERE therapist.therapistID = :tid " +
                            "AND sessionDate = :date " +
                            "AND startTime < :endTime " +
                            "AND endTime > :startTime", TherapySession.class
            );
            query.setParameter("tid", therapistId);
            query.setParameter("date", date);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);

            return query.getResultList();
        } finally {
            session.close();
        }
    }


    @Override
    public TherapySession getSessionByPatientName(String name) {
        Session session = factoryConfiguration.getSession();
        try {
            Query<TherapySession> query = session.createQuery(
                    "FROM TherapySession ts WHERE ts.patient.name = :name ORDER BY ts.sessionDate DESC",
                    TherapySession.class
            );
            query.setParameter("name", name);
            query.setMaxResults(1); // Most recent session

            return query.uniqueResult();
        } finally {
            session.close();
        }
    }



}
