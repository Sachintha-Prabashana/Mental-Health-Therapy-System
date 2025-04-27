package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    @FXML
    private JFXButton checkMedicalRecordsButton;

    @FXML
    private JFXButton checkPerformanceButton;

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private Button logoutButton;

    @FXML
    private Label timeLabel;

    @FXML
    private JFXButton viewFinancialReportsButton;

    @FXML
    void checkMedicalRecordsButtonOnAction(ActionEvent event) {
        loadUI("/view/Medical-Record-Form.fxml");
    }

    @FXML
    void checkPerformanceButtonOnAction(ActionEvent event) {
        loadUI("/view/ReportForm.fxml");
    }

    @FXML
    void logoutbtnOnAction(ActionEvent event) {

    }


    private void loadUI(String resource) {
        dashboardPane.getChildren().clear();
        try {
            dashboardPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the page!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
