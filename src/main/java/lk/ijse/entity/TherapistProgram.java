package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "therapist_program")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TherapistProgram {

    @EmbeddedId
    private TherapistProgramId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("therapistId")
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("programId")
    @JoinColumn(name = "program_id")
    private TherapyProgram therapyProgram;

    @Column(name = "assigned_date", nullable = false)
    private LocalDate assignedDate;
}
