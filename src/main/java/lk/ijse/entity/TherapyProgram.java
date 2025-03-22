package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapy_program")
public class TherapyProgram {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private double fee;

    @ManyToOne
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;
}
