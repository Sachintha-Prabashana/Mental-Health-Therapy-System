package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "therapist")
public class Therapist {
    @Id
    @Column(name = "therapist_id", nullable = false, length = 50)
    private String therapistID;

    @Column(nullable = false)
    private String therapistName;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private int contactNumber;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String availability;

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TherapistProgram> therapistPrograms = new ArrayList<>();

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TherapySession> therapySessions;
}
