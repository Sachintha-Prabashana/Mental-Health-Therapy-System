package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.UserBoImpl;
import lk.ijse.bo.custom.UserBo;
import lk.ijse.util.Role;

import java.io.IOException;

public class SignupPageController {

    @FXML
    private AnchorPane signupPage;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private Button btnSignUp;

    private final UserBo userBo = new UserBoImpl();

    // Regex patterns
    private static final String NAME_REGEX = "^[A-Za-z]{2,30}$";
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.\\w{2,}$";
    private static final String USERNAME_REGEX = "^[A-Za-z0-9_]{4,20}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

    @FXML
    void initialize() {
        cmbRole.getItems().addAll("ADMIN", "RECEPTIONIST");
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String selectedRole = cmbRole.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty() || selectedRole == null) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        if (!firstName.matches(NAME_REGEX)) {
            showAlert("Validation Error", "Invalid first name! Only letters allowed (2–30 characters).", Alert.AlertType.WARNING);
            return;
        }

        if (!lastName.matches(NAME_REGEX)) {
            showAlert("Validation Error", "Invalid last name! Only letters allowed (2–30 characters).", Alert.AlertType.WARNING);
            return;
        }

        if (!email.matches(EMAIL_REGEX)) {
            showAlert("Validation Error", "Invalid email format!", Alert.AlertType.WARNING);
            return;
        }

        if (!username.matches(USERNAME_REGEX)) {
            showAlert("Validation Error", "Username must be 4–20 characters and only contain letters, numbers, or underscores.", Alert.AlertType.WARNING);
            return;
        }

        if (!password.matches(PASSWORD_REGEX)) {
            showAlert("Validation Error", "Password must be at least 6 characters and include both letters and numbers.", Alert.AlertType.WARNING);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
            return;
        }

        // Convert string to Role Enum
        Role role = Role.valueOf(selectedRole);

        // Save user to database
        boolean success = userBo.registerUser(firstName, lastName, email, username, password, role);

        if (success) {
            showAlert("Success", "Account created successfully!", Alert.AlertType.INFORMATION);
            goToLogin(null);
        } else {
            showAlert("Error", "Username already exists!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void goToLogin(ActionEvent event) {
        loadUI("/view/LoginPage.fxml");
    }

    private void loadUI(String resource) {
        signupPage.getChildren().clear();
        try {
            signupPage.getChildren().add(FXMLLoader.load(getClass().getResource(resource)));
        } catch (IOException e) {
            showAlert("Error", "Failed to load page!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
