package lk.ijse.dao.custom.impl;

import lk.ijse.bo.exception.DuplicateException;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.MedicalRecordDAO;
import lk.ijse.entity.MedicalRecord;
import lk.ijse.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MedicalRecordDAOImpl implements MedicalRecordDAO {
    @Override
    public boolean save(MedicalRecord entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        try{
            Payment existRecord = session.get(Payment.class, entity.getId());

            if(existRecord != null){
                throw new DuplicateException("Medical Record id duplicated");
            }
            session.persist(entity);
            tx.commit();
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(MedicalRecord entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        try {
            session.update(entity);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        try {
            MedicalRecord record = session.get(MedicalRecord.class, id);
            if (record != null) {
                session.delete(record);
                tx.commit();
                return true;
            }
            return false;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<MedicalRecord> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            Query<MedicalRecord> query = session.createQuery("FROM MedicalRecord", MedicalRecord.class);
            return query.list();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public String getNextId() {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            Query<String> query = session.createQuery("SELECT m.id FROM MedicalRecord m ORDER BY m.id DESC", String.class);
            query.setMaxResults(1);
            String lastId = query.uniqueResult();

            if (lastId != null) {
                int num = Integer.parseInt(lastId.replace("MR-", ""));
                return String.format("MR-%03d", num + 1);
            } else {
                return "MR-001";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public MedicalRecord findById(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            return session.get(MedicalRecord.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public MedicalRecord search(String id) {
        return findById(id);
    }
}
