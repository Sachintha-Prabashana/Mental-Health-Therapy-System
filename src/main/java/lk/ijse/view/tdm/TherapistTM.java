package lk.ijse.view.tdm;

import jakarta.persistence.*;
import lk.ijse.entity.TherapyProgram;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapistTM {
    private String therapistID;
    private String therapistName;
    private String specialization;
    private String availability;


}
