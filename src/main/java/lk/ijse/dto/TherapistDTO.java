package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapistDTO {

    private String therapistID;
    private String therapistName;
    private String specialization;
    private String availability;

//    private List<String> programIds = new ArrayList<>();
//
//    public void addProgram(String programId) {
//        this.programIds.add(programId);
//    }
//
//    public void removeProgram(String programId) {
//        this.programIds.remove(programId);
//    }
}
