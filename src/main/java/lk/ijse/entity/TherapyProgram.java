package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "therapy_program")
public class TherapyProgram {
    @Id
    @Column(name = "program_id", nullable = false, length = 50)
    private String programId;

    @Column(nullable = false, unique = true)
    private String programName;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private double fee;

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TherapistProgram> therapistPrograms = new ArrayList<>();

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;
}
