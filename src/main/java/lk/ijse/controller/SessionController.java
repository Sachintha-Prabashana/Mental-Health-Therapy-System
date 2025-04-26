package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lk.ijse.bo.custom.*;
import lk.ijse.bo.custom.impl.*;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.dto.TherapySessionDTO;
import lk.ijse.entity.Patient;
import lk.ijse.entity.TherapistProgram;
import lk.ijse.entity.TherapyProgram;

import lk.ijse.view.tdm.TherapistTM;
import lk.ijse.view.tdm.TherapySessionTM;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class SessionController implements Initializable {

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnViewSchedule;

    @FXML
    private ComboBox<LocalTime> cmbEndTime;

    @FXML
    private ComboBox<String> cmbPatient;

    @FXML
    private ComboBox<String> cmbProgram;

    @FXML
    private ComboBox<LocalTime> cmbStartTime;

    @FXML
    private TableColumn<TherapistTM, String> colAvailability;

    @FXML
    private TableColumn<TherapySessionTM, LocalDate> colDate;

    @FXML
    private TableColumn<TherapySessionTM, LocalTime> colEndTime;

    @FXML
    private TableColumn<TherapySessionTM, String> colPatient;

    @FXML
    private TableColumn<TherapySessionTM, String> colProgram;

    @FXML
    private TableColumn<TherapySessionTM, String> colSessionId;

    @FXML
    private TableColumn<TherapistTM, String> colSpecialization;

    @FXML
    private TableColumn<TherapySessionTM, LocalTime> colStartTime;

    @FXML
    private TableColumn<TherapySessionTM, String> colTherapist;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistId;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistName;

    @FXML
    private TableColumn<TherapySessionTM, String> colStatus;

    @FXML
    private TableColumn<TherapistTM, Integer> colContact;

    @FXML
    private TableColumn<TherapistTM, String> colMail;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<TherapySessionTM> tblSessions;

    @FXML
    private TableView<TherapistTM> tblTherapists;

    @FXML
    private TextField txtSessionId;

    @FXML
    private AnchorPane sessionPane;

    private Map<String, String> patientNameIdMap = new HashMap<>();

    private Map<String, String> programsIdMap = new HashMap<>();

    // Models
    private final PatientBO patientBO = new PatientBOImpl();
    private final TherapyProgramsBO  programsBO = new TherapyProgramsBOImpl();
    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();
    private final TherapistBO therapistBO = new TherapistBOImpl();
    private final TherapistProgramBO therapistProgramBO = new TherapistProgramBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Load time options
        loadTimeOptions();

        // Load patients and programs
        loadPatients();
        loadPrograms();

        // Initialize table columns
        initializeTableColumns();
        getNextSessionID();
    }

    private void getNextSessionID() {
        txtSessionId.setText(therapySessionBO.getNextSessionID());
    }

    private void loadTimeOptions() {
        ObservableList<LocalTime> timeOptions = FXCollections.observableArrayList();
        // Add time options in 30-minute intervals from 8 AM to 5 PM
        for (int hour = 8; hour <= 17; hour++) {
            timeOptions.add(LocalTime.of(hour, 0));
            timeOptions.add(LocalTime.of(hour, 30));
        }

        cmbStartTime.setItems(timeOptions);
        cmbEndTime.setItems(timeOptions);
    }

    private void loadPatients() {
        try {
            ArrayList<Patient> patients = patientBO.loadAllpatientsInCombo();
            patientNameIdMap.clear();

            for (Patient patient : patients) {
                String fullName = patient.getName(); // If needed, format first + last name here
                patientNameIdMap.put(fullName, patient.getId());
            }

            cmbPatient.getItems().setAll(patientNameIdMap.keySet());
        } catch (Exception e) {
            showAlert("Error", "Failed to load patients: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void loadPrograms() {
        try {
            ArrayList<TherapyProgram> programs = programsBO.loadAllProgramsInCombo();
            programsIdMap.clear();

            for (TherapyProgram program : programs) {
                String fullName = program.getProgramName(); // If needed, format first + last name here
                programsIdMap.put(fullName, program.getProgramId());
            }

            cmbProgram.getItems().setAll(programsIdMap.keySet());

        } catch (Exception e) {
            showAlert("Error", "Failed to load programs: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void initializeTableColumns() {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));

        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistID"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        loadSessionTable();
        loadTherapistTable();
    }

    private void loadSessionTable() {
        ArrayList<TherapySessionDTO> sessions =  therapySessionBO.loadAllSessions();
        ObservableList<TherapySessionTM> sessionTMS = FXCollections.observableArrayList();

        for (TherapySessionDTO dto : sessions) {

            TherapySessionTM sessionTM = new TherapySessionTM(
                    dto.getSessionId(),
                    dto.getSessionDate(),
                    dto.getStartTime(),
                    dto.getEndTime(),
                    dto.getStatus(),
                    dto.getPatientId(),
                    dto.getProgramId(),
                    dto.getTherapistId()

            );

            sessionTMS.add(sessionTM);
        }
        tblSessions.setItems(sessionTMS);

    }

    private void loadTherapistTable() {
        try  {
            ArrayList<TherapistDTO> therapists = therapistBO.loadAllTherapists();
            ObservableList<TherapistTM> therapistTMList = FXCollections.observableArrayList();

            for (TherapistDTO therapistDTO : therapists) {

                TherapistTM therapistTM = new TherapistTM(
                        therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(),
                        therapistDTO.getContactNumber(),
                        therapistDTO.getMail(),
                        therapistDTO.getAvailability()
                );

                therapistTMList.add(therapistTM);
            }
            tblTherapists.setItems(therapistTMList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        cmbPatient.getSelectionModel().clearSelection();
        cmbProgram.getSelectionModel().clearSelection();
        cmbStartTime.getSelectionModel().clearSelection();
        cmbEndTime.getSelectionModel().clearSelection();
        dpDate.setValue(null);
        txtSessionId.clear();
        tblTherapists.getSelectionModel().clearSelection();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            if (!validateInputs()) {
                return;
            }

            TherapySessionDTO sessionDTO = collectSessionData();

            boolean isSaved = therapySessionBO.bookSession(sessionDTO);

            if (isSaved) {
                showAlert("Success", "Session saved successfully!", Alert.AlertType.INFORMATION);
                goToPaymentPage(sessionDTO);
            } else {
                showAlert("Warning", "Session could not be saved (may be a conflict).", Alert.AlertType.WARNING);
            }

            btnRefreshOnAction(null);

        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void goToPaymentPage(TherapySessionDTO sessionDTO) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Manage-Payment-Form.fxml"));
            AnchorPane pane = loader.load();

            // Get the actual controller instance associated with the FXML
            ManagePaymentFormController paymentController = loader.getController();

            // Set session details
            paymentController.setSessionId(sessionDTO.getSessionId());
            paymentController.setParentController(this);

            // Now display the pane
            sessionPane.getChildren().setAll(pane);

            // Simulate the user completing the form and saving payment
            paymentController.savePaymentWithSession();


//            if (dto != null) {
//                handlePaymentComplete(sessionDTO);
//            }

        } catch (IOException e) {
            showAlert("Error", "Failed to load payment form!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private TherapySessionDTO collectSessionData() {
        TherapySessionDTO sessionDTO = new TherapySessionDTO();
        sessionDTO.setSessionId(txtSessionId.getText());
        sessionDTO.setSessionDate(dpDate.getValue());
        sessionDTO.setStartTime(cmbStartTime.getValue());
        sessionDTO.setEndTime(cmbEndTime.getValue());

        // Get the selected patient and program IDs
        String selectedPatient = cmbPatient.getValue();
        String selectedProgram = cmbProgram.getValue();

        String patientId = patientNameIdMap.get(selectedPatient);
        String programId = programsIdMap.get(selectedProgram);

        // Set the IDs in the DTO
        sessionDTO.setPatientId(patientId);
        sessionDTO.setProgramId(programId);

        // Get the selected therapist ID
        TherapistTM selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();
        if (selectedTherapist != null) {
            sessionDTO.setTherapistId(selectedTherapist.getTherapistID());
        }

        return sessionDTO;
    }

    private boolean validateInputs() {
        // Validate session ID
        if (txtSessionId.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Session ID cannot be empty", Alert.AlertType.ERROR);
            txtSessionId.requestFocus();
            return false;
        }

        // Validate patient
        if (cmbPatient.getValue() == null) {
            showAlert("Validation Error", "Please select a patient", Alert.AlertType.ERROR);
            cmbPatient.requestFocus();
            return false;
        }

        // Validate therapist selection
        if (tblTherapists.getSelectionModel().getSelectedItem() == null) {
            showAlert("Validation Error", "Please select a therapist", Alert.AlertType.ERROR);
            tblTherapists.requestFocus();
            return false;
        }

        if (cmbStartTime.getValue() == null) {
            showAlert("Validation Error", "Start Time cannot be empty", Alert.AlertType.ERROR);
            cmbStartTime.requestFocus();
            return false;
        }

        if (cmbEndTime.getValue() == null) {
            showAlert("Validation Error", "End Time cannot be empty", Alert.AlertType.ERROR);
            cmbEndTime.requestFocus();
            return false;
        }

        // Validate program
        if (cmbProgram.getValue() == null) {
            showAlert("Validation Error", "Please select a program", Alert.AlertType.ERROR);
            cmbProgram.requestFocus();
            return false;
        }

        // Validate date
        if (dpDate.getValue() == null) {
            showAlert("Validation Error", "Session date must be selected", Alert.AlertType.ERROR);
            dpDate.requestFocus();
            return false;
        }

        return true;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            // Step 1: Validate input fields
            if (!validateInputs()) {
                return;
            }

            TherapySessionDTO updatedSession = collectSessionData();

            // Step 2: Confirm with user
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Update");
            confirmation.setContentText("Are you sure you want to update/reschedule this session?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                boolean isUpdated = therapySessionBO.rescheduleSession(updatedSession);

                if (isUpdated) {
                    showAlert("Success", "Session rescheduled successfully!", Alert.AlertType.INFORMATION);
                    loadSessionTable(); // reload updated sessions
                    btnRefreshOnAction(null); // clear fields
                    btnSave.setDisable(false);
                    btnUpdate.setDisable(true);
                } else {
                    showAlert("Warning", "Failed to reschedule session.", Alert.AlertType.WARNING);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Something went wrong while rescheduling!", Alert.AlertType.ERROR);
        }
    }


    @FXML
    void btnViewScheduleOnAction(ActionEvent event) {
        // Implement view schedule functionality
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void cmbProgramOnAction(ActionEvent event) {
        String selectedProgramName = cmbProgram.getValue();
        if (selectedProgramName == null) return;

        String programId = programsIdMap.get(selectedProgramName); // ✅ Correct mapping

        if (programId == null) {
            showAlert("Error", "Invalid program selected.", Alert.AlertType.ERROR);
            return;
        }

        try {
            List<TherapistDTO> enrolledTherapists = therapistProgramBO.getTherapistsByProgram(programId);

            ObservableList<TherapistTM> therapistTMList = FXCollections.observableArrayList();

            for (TherapistDTO dto : enrolledTherapists) {
                therapistTMList.add(new TherapistTM(
                        dto.getTherapistID(),
                        dto.getTherapistName(),
                        dto.getSpecialization(),
                        dto.getContactNumber(),
                        dto.getMail(),
                        dto.getAvailability()
                ));
            }

            tblTherapists.setItems(therapistTMList);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load enrolled therapists!", Alert.AlertType.ERROR);
        }
    }

    public void navigateToHome(MouseEvent mouseEvent) {

    }

    public void tblSessionsOnClick(MouseEvent mouseEvent) {
        TherapySessionTM selectedSession = tblSessions.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            txtSessionId.setText(selectedSession.getSessionId());
            dpDate.setValue(selectedSession.getSessionDate());
            cmbStartTime.setValue(selectedSession.getStartTime());
            cmbEndTime.setValue(selectedSession.getEndTime());
//            cmbPatient.setValue(selectedSession.getPatientId());
//            cmbProgram.setValue(selectedSession.getProgramId());
            tblTherapists.getSelectionModel().clearSelection();

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
        }
    }

    public void onPaymentCompleted() throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Go Back To Session Page",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Session.fxml"));
            AnchorPane pane = loader.load();
            sessionPane.getChildren().setAll(pane);
        }
    }
}