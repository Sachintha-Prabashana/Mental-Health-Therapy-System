package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "medical_records")

public class MedicalRecord {
    @Id
    @Column(name = "record_id")
    private String id;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(length = 2000)
    private String diagnosis;

    @Column(length = 2000, name = "medical_history")
    private String medicalHistory;

    @Column(length = 2000, name = "therapy_notes")
    private String therapyNotes;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
