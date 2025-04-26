package lk.ijse.bo.custom;

import lk.ijse.dto.TherapistPerformanceDTO;

import java.util.List;

public interface ReportBO {
    List<TherapistPerformanceDTO> fetchTherapistPerformance();

    void exportReportToPdf(List<TherapistPerformanceDTO> dataList);
}
