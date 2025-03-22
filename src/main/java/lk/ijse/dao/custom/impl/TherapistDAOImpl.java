package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.TherapistDAO;
import lk.ijse.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    @Override
    public boolean save(Therapist entity) {
        Session session = factoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        session.save(entity);
        tx.commit();
        return true;
    }

    @Override
    public String getNextId() {
        return "";
    }
}
