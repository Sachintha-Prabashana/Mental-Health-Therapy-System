package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.custom.MedicalReportBO;
import lk.ijse.bo.custom.PatientBO;
import lk.ijse.bo.custom.impl.MedicalReportBOImpl;
import lk.ijse.bo.custom.impl.PatientBOImpl;
import lk.ijse.dto.MedicalRecordDTO;
import lk.ijse.dto.PatientDTO;
import lk.ijse.entity.Patient;
import lk.ijse.view.tdm.MedicalRecordTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MedicalRecordFormController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<MedicalRecordTM, LocalDate> colDate;

    @FXML
    private TableColumn<MedicalRecordTM, String > colDiagnosis;

    @FXML
    private TableColumn<MedicalRecordTM, String> colPatientId;

    @FXML
    private TableColumn<?, String> colPatientName;

    @FXML
    private TableColumn<MedicalRecordTM, String> colRecordId;

    @FXML
    private TableColumn<MedicalRecordTM, String> colHistory;

    @FXML
    private TableColumn<?, ?> colRecorded;

    @FXML
    private TableColumn<MedicalRecordTM, String> colTherapyNotes;

    @FXML
    private ComboBox<String> comboPatient;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<MedicalRecordTM> tblMedicalRecords;

    @FXML
    private TextArea txtDiagnosis;

    @FXML
    private TextArea txtMedicalHistory;

    @FXML
    private TextField txtRecordId;

    @FXML
    private TextArea txtTherapyNotes;


    private final MedicalReportBO medicalRecordBO = new MedicalReportBOImpl();
    private final PatientBO patientBO = new PatientBOImpl();

    private Map<String, String> patientNameIdMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colRecordId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("recordDate"));
        colDiagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        colHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
        colTherapyNotes.setCellValueFactory(new PropertyValueFactory<>("therapyNotes"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
//        colRecorded.setCellValueFactory(new PropertyValueFactory<>("recordedBy"));


        // Set today's date in date picker
        datePicker.setValue(LocalDate.now());

        // Load data
        loadMedicalRecords();
        loadPatients();
        generateNewId();

    }

    private void generateNewId() {
        txtRecordId.setText(medicalRecordBO.getNextMedicalRecordID());
    }

    private void loadPatients() {
        try {
            ArrayList<Patient> patients = patientBO.loadAllpatientsInCombo();
            patientNameIdMap.clear();

            for (Patient patient : patients) {
                String fullName = patient.getName(); // If needed, format first + last name here
                patientNameIdMap.put(fullName, patient.getId());
            }

            comboPatient.getItems().setAll(patientNameIdMap.keySet());
        } catch (Exception e) {
            showAlert("Error", "Failed to load patients: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }



    private void loadMedicalRecords() {
        try {
            ArrayList<MedicalRecordDTO> records = medicalRecordBO.loadAllMedicalRecords();
            ObservableList<MedicalRecordTM> recordTMList = FXCollections.observableArrayList();

            for (MedicalRecordDTO recordDTO : records) {
                MedicalRecordTM recordTM = new MedicalRecordTM(
                        recordDTO.getId(),
                        recordDTO.getRecordDate(),
                        recordDTO.getDiagnosis(),
                        recordDTO.getMedicalHistory(),
                        recordDTO.getTherapyNotes(),
                        recordDTO.getPatientId()

                );

                recordTMList.add(recordTM);
            }
            tblMedicalRecords.setItems(recordTMList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load medical records!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void handleClear(ActionEvent event) {
        clearFields();
        generateNewId();
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
    }

    @FXML
    void handleDelete(ActionEvent event) {
        MedicalRecordTM selectedRecord = tblMedicalRecords.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("Warning", "Please select a record to delete!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this medical record?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = medicalRecordBO.deleteMedicalRecord(selectedRecord.getId());

                if (isDeleted) {
                    showAlert("Success", "Medical record deleted successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete medical record!", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "An unexpected error occurred!", Alert.AlertType.ERROR);
            }

            loadMedicalRecords();
            clearFields();
            generateNewId();
        }

    }

    @FXML
    void handleSave(ActionEvent event) {
        String id = txtRecordId.getText();
        LocalDate recordDate = datePicker.getValue();
        String diagnosis = txtDiagnosis.getText();
        String medicalHistory = txtMedicalHistory.getText();
        String therapyNotes = txtTherapyNotes.getText();

        // Extract patient ID from combobox selection
        String selectedPatient = comboPatient.getValue();
        String patientId = patientNameIdMap.get(selectedPatient);

        // Validate input fields
        if (id.isEmpty() || recordDate == null || diagnosis.isEmpty() || medicalHistory.isEmpty() || therapyNotes.isEmpty() || selectedPatient == null || selectedPatient.isEmpty()) {
            showAlert("Warning", "Please fill all required fields and select a valid patient!", Alert.AlertType.WARNING);
            return;
        }

        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO(
                id, recordDate, diagnosis, medicalHistory, therapyNotes, patientId
        );

        try {
            boolean isSaved = medicalRecordBO.saveMedicalRecord(medicalRecordDTO);

            if (isSaved) {
                showAlert("Success", "Medical record saved successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to save medical record! Possible duplicate ID or invalid patient.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred!", Alert.AlertType.ERROR);
        }

        loadMedicalRecords();
        clearFields();
        generateNewId();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleTableClick(MouseEvent event) {
        MedicalRecordTM selectedItem = tblMedicalRecords.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtRecordId.setText(selectedItem.getId());
            datePicker.setValue(selectedItem.getRecordDate());

            // Find and select the correct patient in combobox
//            String patientComboValue = selectedItem.getPatientId() + " - " + selectedItem.get();
//            comboPatient.setValue(patientComboValue);

            //hdnn tiynv

            txtDiagnosis.setText(selectedItem.getDiagnosis());
            txtMedicalHistory.setText(selectedItem.getMedicalHistory());
            txtTherapyNotes.setText(selectedItem.getTherapyNotes());




            btnSave.setDisable(true);
            btnDelete.setDisable(false);
        }
    }

    private void clearFields() {
        txtRecordId.clear();
        datePicker.setValue(LocalDate.now());
        comboPatient.getSelectionModel().clearSelection();
        txtDiagnosis.clear();
        txtMedicalHistory.clear();
        txtTherapyNotes.clear();
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
    }


    public void navigateToHome(MouseEvent mouseEvent) {
    }

    public void cmbFilterByPatientOnAction(ActionEvent actionEvent) {

    }
}
