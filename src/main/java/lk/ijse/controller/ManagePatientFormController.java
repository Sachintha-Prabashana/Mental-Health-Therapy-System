package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.PatientBO;
import lk.ijse.bo.custom.impl.PatientBOImpl;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dto.PatientDTO;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.view.tdm.PatientTM;
import lk.ijse.view.tdm.TherapistTM;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ManagePatientFormController implements Initializable {

    @FXML
    private JFXButton btnAddNewPatient;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<PatientTM, Integer> clmContact;

    @FXML
    private TableColumn<PatientTM, String> clmPatientId;

    @FXML
    private TableColumn<PatientTM, String> clmPatientName;

    @FXML
    private TableColumn<PatientTM, String > clmGender;


    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<PatientTM> tblPatient;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private AnchorPane patientPane;

    @FXML
    private ComboBox<String> cmbFilterBySession;

    private final PatientBO patientBO = new PatientBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));


        loadPatients();
        loadSessionIdsInCombo();
        try {
            generateNewPatientId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSessionIdsInCombo() {

    }

    private void generateNewPatientId() throws SQLException, ClassNotFoundException {
        txtPatientId.setText(patientBO.getNextPatientID());
    }

    private void loadPatients() {
        try  {
            ArrayList<PatientDTO> patients = patientBO.loadAllPatient();
            ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();

            for (PatientDTO patientDTO  : patients) {

                PatientTM patientTM = new PatientTM(
                        patientDTO.getId(),
                        patientDTO.getName(),
                        patientDTO.getContactInfo(),
                        patientDTO.getGender()
                );

                patientTMS.add(patientTM);
            }
            tblPatient.setItems(patientTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load Patient!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        PatientTM selectedItem = tblPatient.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Warning", "Please select a patient to delete!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this patient?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDelete = patientBO.deletePatient(selectedItem.getId());

                if (isDelete) {
                    showAlert("Success", "Patient deleted successfully!", Alert.AlertType.INFORMATION);

                } else {
                    showAlert("Error", "Failed to delete patient!", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            loadPatients();
            clearFields();
        }
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {
        String id = txtPatientId.getText();
        String name = txtPatientName.getText();
        String contact = txtContact.getText();
        String gender = cmbGender.getValue();

        // Validate input fields
        if (id.isEmpty() || name.isEmpty() || contact.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        PatientDTO patientDTO = new PatientDTO(id, name, contact, gender);

        try {
            boolean isSaved = patientBO.savePatient(patientDTO);

            if (isSaved) {
                showAlert("Success", "Patient saved successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to save patient! Possible duplicate ID.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log error details
            showAlert("Error", "An unexpected error occurred!", Alert.AlertType.ERROR);
        }

        loadPatients(); // Refresh therapist list
        clearFields(); // Clear input fields
    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {
        String id = txtPatientId.getText();
        String name = txtPatientName.getText();
        String contact = txtContact.getText();
        String gender = cmbGender.getValue();

        // Validate input fields
        if (id.isEmpty() || name.isEmpty() || contact.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        PatientDTO patientDTO = new PatientDTO(id, name, contact, gender);

        try {
            boolean isSaved = patientBO.updatePatient(patientDTO);

            if (isSaved) {
                showAlert("Success", "Patient update successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to update patient! Possible duplicate ID.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log error details
            showAlert("Error", "An unexpected error occurred!", Alert.AlertType.ERROR);
        }

        loadPatients(); // Refresh therapist list
        clearFields(); // Clear input fields
    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    @FXML
    void tblPatientOnClick(MouseEvent event) {
        PatientTM selectedItem = tblPatient.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a patient", Alert.AlertType.WARNING);
        }else {
            txtPatientId.setText(selectedItem.getId());
            txtPatientName.setText(selectedItem.getName());
            txtContact.setText(selectedItem.getContactInfo());
            cmbGender.setValue(selectedItem.getGender());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }

    private void clearFields() {
        txtPatientId.clear();
        txtPatientName.clear();
        txtContact.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void btnExport_OnAction(ActionEvent actionEvent) {
    }


    public void searchPatient(KeyEvent keyEvent) {
    }

    public void cmbFilterBySessionOnAction(ActionEvent actionEvent) {

    }


    private void loadUI(String resource) {
        patientPane.getChildren().clear();
        try {
            patientPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }
}
