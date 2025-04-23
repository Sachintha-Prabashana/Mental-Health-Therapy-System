package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReceptionistPageController implements Initializable {

    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnHistory;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnReports;

    @FXML
    private ImageView imgLogout;

    @FXML
    private ImageView imgSettings;

    @FXML
    private Label lblReceptionistId;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblWelcome;

    @FXML
    private AnchorPane mainContent;

    @FXML
    void btnAppointmentOnAction(ActionEvent event) {
        loadUI("/view/Manage-Therapy-Session-Form.fxml");
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        loadUI("/view/MainDashboardView.fxml");
    }

    @FXML
    void btnHistoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {
        loadUI("/view/Manage-Patient-Form.fxml");
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        loadUI("/view/Manage-Payment-Form.fxml");
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) {
        loadUI("/view/ReportForm.fxml");

    }

    @FXML
    void onLogoutClick(MouseEvent event) {

    }

    @FXML
    void onSettingsClick(MouseEvent event) {

    }

    private void loadUI(String resource) {
        mainContent.getChildren().clear();
        try {
            mainContent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUI("/view/MainDashboardView.fxml");
    }

}
