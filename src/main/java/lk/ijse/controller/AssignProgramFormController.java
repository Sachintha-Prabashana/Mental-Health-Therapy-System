package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.bo.custom.TherapistProgramBO;
import lk.ijse.bo.custom.TherapyProgramsBO;
import lk.ijse.bo.custom.impl.TherapistBOImpl;
import lk.ijse.bo.custom.impl.TherapistProgramBOImpl;
import lk.ijse.bo.custom.impl.TherapyProgramsBOImpl;
import lk.ijse.dto.TherapistProgramDTO;
import lk.ijse.dto.TherapyProgramDTO;
import lk.ijse.entity.Patient;
import lk.ijse.entity.Therapist;
import lk.ijse.view.tdm.TherapistProgramTM;
import lk.ijse.view.tdm.TherapyProgramTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AssignProgramFormController implements Initializable {

    @FXML
    private TableColumn<TherapyProgramTM, String> ColDuration;

    @FXML
    private Button btnAssign;

    @FXML
    private Button btnClear;

    @FXML
    private ComboBox<String> cmbTherapist;

    @FXML
    private TableColumn<TherapyProgramTM, Double> colFee;

    @FXML
    private TableColumn<TherapyProgramTM, String> colName;

    @FXML
    private TableColumn<TherapistProgramTM, LocalDate> colAssignedDate;

    @FXML
    private TableColumn<TherapyProgramTM, String> colProgramId;

    @FXML
    private TableColumn<TherapistProgramTM, String> colProgramName;

    @FXML
    private TableColumn<TherapistProgramTM, String> colTherapistName;

    @FXML
    private TableView<TherapistProgramTM> tblAssignedPrograms;

    @FXML
    private TableView<TherapyProgramTM> tblPrograms;

    @FXML
    private TextField txtFilterPrograms;

    private Map<String, String> therapistNameIDMap = new HashMap<>();
    private final TherapistBO therapistBO = new TherapistBOImpl();
    private final TherapyProgramsBO therapyProgramsBO = new TherapyProgramsBOImpl();
    private TherapistProgramBO assignmentBO = new TherapistProgramBOImpl();

    @FXML
    private void btnAssignOnAction(ActionEvent event) {
        String selectedTherapistName = cmbTherapist.getValue();
        TherapyProgramTM selectedProgram = tblPrograms.getSelectionModel().getSelectedItem();

        if (selectedTherapistName == null || selectedProgram == null) {
            new Alert(Alert.AlertType.WARNING, "Please select both therapist and program.").show();
            return;
        }

        try {
            // Fetch actual therapist & program IDs
            String therapistId = therapistBO.getTherapistIdByName(selectedTherapistName); // Assume this method exists
            String programId = selectedProgram.getProgramId();

            TherapistProgramDTO assignment = new TherapistProgramDTO(
                    therapistId,
                    programId,
                    LocalDate.now()
            );

            boolean isSaved = assignmentBO.saveAssignment(assignment);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Program assigned successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Assignment failed.").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong: " + e.getMessage()).show();
            e.printStackTrace();
        }
        loadAssignmentsTable();
    }

    private void loadAssignmentsTable() {
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colAssignedDate.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));

        loadDataToTable();


    }

    private void loadDataToTable() {
        try  {
            ArrayList<TherapistProgramDTO> DTOs = assignmentBO.loadAllData();
            ObservableList<TherapistProgramTM> therapistProgramTMSList = FXCollections.observableArrayList();

            for (TherapistProgramDTO dto : DTOs) {

                TherapistProgramTM therapistProgramTM = new TherapistProgramTM(
                        dto.getProgramId(),
                        dto.getTherapistId(),
                        dto.getAssignedDate()
                );

                therapistProgramTMSList.add(therapistProgramTM);
            }
            tblAssignedPrograms.setItems(therapistProgramTMSList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapy programs!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        ColDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        loadTherapistCombo();
        loadAllPrograms();
        loadAssignmentsTable();
    }

    private void loadAllPrograms() {
        try  {
            ArrayList<TherapyProgramDTO> therapyProgramDTOS = therapyProgramsBO.loadAllTherapyPrograms();
            ObservableList<TherapyProgramTM> therapyProgramTMSList = FXCollections.observableArrayList();

            for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {

                TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                        therapyProgramDTO.getProgramId(),
                        therapyProgramDTO.getProgramName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee()
                );

                therapyProgramTMSList.add(therapyProgramTM);
            }
            tblPrograms.setItems(therapyProgramTMSList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapy programs!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void loadTherapistCombo() {
        try {
            ArrayList<Therapist> allTherapists = therapistBO.loadAllTherapistsInCombo();

            therapistNameIDMap.clear(); // Clear old data

            for (Therapist therapist : allTherapists) {
                String fullName = therapist.getTherapistName(); // If needed, format first + last name here
                therapistNameIDMap.put(fullName, therapist.getTherapistID());
            }

            cmbTherapist.getItems().setAll(therapistNameIDMap.keySet());

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load patients!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void navigateToHome(MouseEvent mouseEvent) {

    }
}
