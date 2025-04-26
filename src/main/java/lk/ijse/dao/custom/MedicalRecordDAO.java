package lk.ijse.dao.custom;

import lk.ijse.entity.MedicalRecord;

public interface MedicalRecordDAO extends CrudDAO<MedicalRecord>{
    MedicalRecord search(String id);
}
