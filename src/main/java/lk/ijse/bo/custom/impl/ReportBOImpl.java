package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ReportBO;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dto.TherapistPerformanceDTO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ReportBOImpl implements ReportBO {
    @Override
    public List<TherapistPerformanceDTO> fetchTherapistPerformance() {
        List<TherapistPerformanceDTO> list = new ArrayList<>();

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "SELECT t.therapistID, t.therapistName, COUNT(s.sessionId), COUNT(DISTINCT s.patient.id) " +
                    "FROM TherapySession s JOIN s.therapist t " +
                    "GROUP BY t.therapistID, t.therapistName";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> results = query.list();

            for (Object[] row : results) {
                String therapistID = (String) row[0];
                String therapistName = (String) row[1];
                Long sessions = (Long) row[2];
                Long totalPatients = (Long) row[3];

                list.add(new TherapistPerformanceDTO(
                        therapistID,
                        therapistName,
                        sessions.intValue(),
                        totalPatients.intValue()
                ));
            }
        }
        return list;
    }


    @Override
    public void exportReportToPdf(List<TherapistPerformanceDTO> dataList) {
        System.out.println("Generating PDF... (to be implemented)");

    }
}
