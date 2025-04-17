package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.custom.TherapySessionBO;
import lk.ijse.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.dto.TherapySessionDTO;
import lk.ijse.view.tdm.TherapistTM;
import lk.ijse.view.tdm.TherapySessionTM;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TherapySessionTableFormController implements Initializable {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnReschedule;

    @FXML
    private TableColumn<TherapySessionTM, LocalDate> clmDate;

    @FXML
    private TableColumn<TherapySessionTM, String> clmPatientId;

    @FXML
    private TableColumn<TherapySessionTM, String> clmProgramId;

    @FXML
    private TableColumn<TherapySessionTM, String> clmSessionId;

    @FXML
    private TableColumn<TherapySessionTM, String> clmStatus;

    @FXML
    private TableColumn<TherapySessionTM, String> clmTherapistId;

    @FXML
    private TableColumn<TherapySessionTM, LocalTime> clmTime;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<TherapySessionTM> tblTherapySession;

    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        clmDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        clmTime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        clmPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        clmProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        clmTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));

        loadSessions();
    }

    private void loadSessions() {
        ArrayList<TherapySessionDTO> sessions =  therapySessionBO.loadAllSessions();
        ObservableList<TherapySessionTM> sessionTMS = FXCollections.observableArrayList();

        for (TherapySessionDTO dto : sessions) {

            TherapySessionTM sessionTM = new TherapySessionTM(
                    dto.getSessionId(),
                    dto.getSessionDate(),
                    dto.getSessionTime(),
                    dto.getStatus(),
                    dto.getPatientId(),
                    dto.getProgramId(),
                    dto.getTherapistId()

            );

            sessionTMS.add(sessionTM);
        }
        tblTherapySession.setItems(sessionTMS);
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        TherapySessionTM selected = tblTherapySession.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Please select a session to cancel!", Alert.AlertType.WARNING);
            return;
        }

        boolean isUpdated = therapySessionBO.cancelSession(selected.getSessionId());

        if (isUpdated) {
            showAlert("Success", "Session cancelled successfully!", Alert.AlertType.INFORMATION);
            loadSessions(); // refresh table
        } else {
            showAlert("Error", "Failed to cancel the session.", Alert.AlertType.ERROR);
        }

    }

    @FXML
    void btnRescheduleOnAction(ActionEvent event) {
        TherapySessionTM selected = tblTherapySession.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Please select a session to reschedule!", Alert.AlertType.WARNING);
            return;
        }

        // For now, reschedule to a new hardcoded time (e.g., next day, same time)
        LocalDate newDate = selected.getSessionDate().plusDays(1); // you can show date/time pickers
        LocalTime newTime = selected.getSessionTime(); // or change time here

        boolean isUpdated = therapySessionBO.rescheduleSession(
                selected.getSessionId(),
                newDate,
                newTime
        );

        if (isUpdated) {
            showAlert("Success", "Session rescheduled successfully!", Alert.AlertType.INFORMATION);
            loadSessions();
        } else {
            showAlert("Error", "Failed to reschedule the session.", Alert.AlertType.ERROR);
        }

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
