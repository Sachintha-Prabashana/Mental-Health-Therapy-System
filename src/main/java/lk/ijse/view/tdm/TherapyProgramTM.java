package lk.ijse.view.tdm;

import jakarta.persistence.*;
import lk.ijse.entity.Therapist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapyProgramTM {
    private String id;
    private String name;
    private int duration;
    private double fee;
    private Therapist therapist;
}
