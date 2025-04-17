package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.TherapySessionBO;
import lk.ijse.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.dto.TherapySessionDTO;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageTherapySessionController implements Initializable {

    @FXML
    private JFXButton btnAddNewTherapist;

    @FXML
    private JFXButton btnAddPatient;

    @FXML
    private JFXButton btnAddTherapist;

    @FXML
    private JFXButton btnAddTherapyProgram;

    @FXML
    private JFXButton btnCompleteSetup;

    @FXML
    private JFXButton btnSeeAll;

    @FXML
    private DatePicker dpSessionDate;

    @FXML
    private ImageView imgHome;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane sessionPane;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtSessionId;

    @FXML
    private TextField txtSessionTime;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtprogramId;

        private static ManageTherapySessionController instance;

    // set therapist id to text field
    public ManageTherapySessionController(){
        instance = this;
    }

    public static ManageTherapySessionController getInstance() {
        return instance;
    }

    public void setProgramId(String id) {txtprogramId.setText(id);}

    public void setPatientId(String id) {txtPatientId.setText(id);}

    public void setTherapistId(String id) {txtTherapistId.setText(id);}

    private final  TherapySessionBO therapySessionBO = new TherapySessionBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUI("/view/Patient-Table-View.fxml");

    }

    @FXML
    void btnAddPatient_OnAction(ActionEvent event) {
        loadUI("/view/Patient-Table-View.fxml");
    }

    @FXML
    void btnAddTherapist_OnAction(ActionEvent event) {
        loadUI("/view/Therapist-Table-View.fxml");
    }

    @FXML
    void btnAddTherapyProgram_OnAction(ActionEvent event) {
        loadUI("/view/TherapyProgram-Table-View.fxml");

    }

    @FXML
    void btnCompleteSetupOnAction(ActionEvent event) {
        try {
            String sessionId = txtSessionId.getText();
            String patientId = txtPatientId.getText();
            String therapistId = txtTherapistId.getText();
            String programId = txtprogramId.getText();
            LocalDate date = dpSessionDate.getValue();
            LocalTime time = LocalTime.parse(txtSessionTime.getText());

            therapySessionBO.bookSession(sessionId, patientId, therapistId, programId, date, time);

            showAlert("Success", "Therapy session booked!", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnRescheduleOnAction(ActionEvent event) {

    }

    @FXML
    void searchPatient(KeyEvent event) {
        String name = ((TextField) event.getSource()).getText();

        try {
            TherapySessionDTO sessionDTO = therapySessionBO.getSessionByPatientName(name);

            if (sessionDTO != null) {
                txtSessionId.setText(sessionDTO.getSessionId());
                txtPatientId.setText(sessionDTO.getPatientId());
                txtTherapistId.setText(sessionDTO.getTherapistId());
                txtprogramId.setText(sessionDTO.getProgramId());
                dpSessionDate.setValue(sessionDTO.getSessionDate());
                txtSessionTime.setText(sessionDTO.getSessionTime().toString());
            } else {
                clearFields();
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to search therapy session", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    @FXML
    void btnSeeAllOnAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/TherapySession-Table-View.fxml")));
            sessionPane.getChildren().setAll(pane);
        } catch (IOException e) {
            showAlert("Error", "Failed to load session list!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    private void loadUI(String resource) {
        mainPane.getChildren().clear();
        try {
            mainPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtSessionId.clear();
        txtPatientId.clear();
        txtTherapistId.clear();
        txtprogramId.clear();
        txtSessionTime.clear();
        dpSessionDate.setValue(null);
    }



}
