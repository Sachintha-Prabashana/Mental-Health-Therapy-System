package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapyProgramDTO {
    private String programId;
    private String programName;
    private String  duration;
    private double fee;

//    private List<String> therapistIds = new ArrayList<>();
//
//    public void addTherapist(String therapistId) {
//        this.therapistIds.add(therapistId);
//    }
//
//    public void removeTherapist(String therapistId) {
//        this.therapistIds.remove(therapistId);
//    }
}
