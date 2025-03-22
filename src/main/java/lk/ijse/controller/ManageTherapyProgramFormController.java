package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class ManageTherapyProgramFormController {

    @FXML
    private AnchorPane addTherapistsPane;

    @FXML
    private JFXButton btnAddNewTherapist;

    @FXML
    private JFXButton btnAddTherapists;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> clmDuration;

    @FXML
    private TableColumn<?, ?> clmFee;

    @FXML
    private TableColumn<?, ?> clmProgramId;

    @FXML
    private TableColumn<?, ?> clmProgramName;

    @FXML
    private TableColumn<?, ?> clmTherapistsId;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<?> tblTherapists;

    @FXML
    private TextField txtTherapistAvailability;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    @FXML
    private TextField txtTherapistSpecialty;

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {

    }

    @FXML
    void btnAddTherapistsOnAction(ActionEvent event) {
        loadUI("/view/Therapist-Table-View.fxml");
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {

    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    private void loadUI(String resource) {
        addTherapistsPane.getChildren().clear();
        try {
            addTherapistsPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
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
