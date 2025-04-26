package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.TherapistProgramDAO;
import lk.ijse.entity.Therapist;
import lk.ijse.entity.TherapistProgram;
import lk.ijse.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TherapistProgramDAOImpl implements TherapistProgramDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    @Override
    public boolean save(TherapistProgram entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

// Fetch referenced entities
        Therapist therapist = session.get(Therapist.class, entity.getTherapist().getTherapistID());
        TherapyProgram program = session.get(TherapyProgram.class, entity.getTherapyProgram().getProgramId());

        if (therapist == null || program == null) {
            System.out.println("Therapist or Program not found!");
            transaction.rollback();
            session.close();
            return false;
        }

// Set the actual persisted references
        entity.setTherapist(therapist);
        entity.setTherapyProgram(program);

// Save the relationship
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public boolean update(TherapistProgram entity) {
        return false;
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        return false;
    }

    @Override
    public List<TherapistProgram> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            List<TherapistProgram> list = session.createQuery("FROM TherapistProgram", TherapistProgram.class).list();
            transaction.commit();
            return list;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }

    @Override
    public String getNextId() {
        return "";
    }

    @Override
    public TherapistProgram findById(String id) {
        return null;
    }

    public List<Therapist> getTherapistsByProgramId(String programId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Query<Therapist> query = session.createQuery(
                    "SELECT tp.therapist FROM TherapistProgram tp WHERE tp.therapyProgram.programId = :pid",
                    Therapist.class
            );
            query.setParameter("pid", programId);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

}
