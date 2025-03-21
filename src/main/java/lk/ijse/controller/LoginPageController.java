package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.AuthService;
import lk.ijse.util.Role;
import java.io.IOException;
import java.util.Objects;

public class LoginPageController {

    @FXML
    private Button btnSignIn;

    @FXML
    private AnchorPane loginPage;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblCreateAcc;

    @FXML
    private Label lblForgotPassword;

    private final AuthService authService = new AuthService(); // Injecting AuthService

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and Password cannot be empty!", Alert.AlertType.ERROR);
            return;
        }

        Role userRole = authService.authenticateUser(username, password);

        if (userRole != null) {
            navigateToDashBoard(userRole);
        } else {
            showAlert("Login Failed", "Invalid Username or Password!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void lblCreateAccOnClick(MouseEvent event) {
        loadUI("/view/SignUpPage.fxml");

    }

    private void navigateToDashBoard(Role role) {
        if (role == Role.ADMIN) { // enum ekn ena role ek blnv . ek constant value ekk
            loadUI("/view/AdminDashboard.fxml");
        } else if (role == Role.RECEPTIONIST) {
            loadUI("/view/ReceptionistDashboard.fxml");
        } else if (role == Role.THERAPIST) {
            loadUI("/view/TherapistDashboard.fxml");
        } else {
            showAlert("Error", "Unauthorized access!", Alert.AlertType.ERROR);
        }
    }

    private void loadUI(String resource) {
        loginPage.getChildren().clear();
        try {
            loginPage.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
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
