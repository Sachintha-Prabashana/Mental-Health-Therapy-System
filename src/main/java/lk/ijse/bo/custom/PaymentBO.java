package lk.ijse.bo.custom;

import lk.ijse.dto.PatientDTO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;

import java.util.ArrayList;
import java.util.List;

public interface PaymentBO {

    boolean savePayment(PaymentDTO paymentDTO);
    String getNextPaymentID();

    ArrayList<PaymentDTO> loadAllPayments();
    List<PaymentDTO> getPaymentsByStatus(String status);

}
