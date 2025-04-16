package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUI("/view/Patient-Table-View.fxml");

    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {

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

    }

    @FXML
    void btnSeeAllOnAction(ActionEvent event) {

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


}
