package lk.ijse.dao.custom;

import lk.ijse.bo.custom.impl.PaymentBOImpl;
import lk.ijse.entity.Payment;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    List<Payment> findByStatus(String status);
}
