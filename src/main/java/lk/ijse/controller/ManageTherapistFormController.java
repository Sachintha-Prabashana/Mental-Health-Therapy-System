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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.TherapistBOImpl;
import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.entity.Therapist;
import lk.ijse.view.tdm.TherapistTM;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ManageTherapistFormController implements Initializable {

    @FXML
    private JFXButton btnAddNewTherapist, btnDelete, btnSave, btnUpdate;

    @FXML
    private TableColumn<TherapistTM, String>  clmTherapistId, clmTherapistName, clmTherapistSpeciality, clmEmail;

    @FXML
    private TableColumn<TherapistTM, String> clmAvailability;

    @FXML
    private TableColumn<TherapistTM, Integer> clmContactNumber;

    @FXML
    private TableView<TherapistTM> tblTherapists;

    @FXML
    private TextField  txtTherapistId, txtTherapistName, txtTherapistSpecialty;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAvilability;


    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    private final TherapistBO therapistBO = new TherapistBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistID"));
        clmTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        clmTherapistSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        clmContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        clmAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        txtAvilability.setText("YES");

        // Load data into table
        loadTherapists();
        generateNewId();
    }

    private void generateNewId() {
        txtTherapistId.setText(therapistBO.getNaxtTherapistID());
    }

    private void loadTherapists() {
//        therapistList.clear();
        try  {
            ArrayList<TherapistDTO> therapists = therapistBO.loadAllTherapists();
            ObservableList<TherapistTM> therapistTMList = FXCollections.observableArrayList();

            for (TherapistDTO therapistDTO : therapists) {

                TherapistTM therapistTM = new TherapistTM(
                        therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(),
                        therapistDTO.getContactNumber(),
                        therapistDTO.getMail(),
                        therapistDTO.getAvailability()
                );

                therapistTMList.add(therapistTM);
            }
            tblTherapists.setItems(therapistTMList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        //table ek reload wenn hdnn
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {
        String id = txtTherapistId.getText();
        String name = txtTherapistName.getText();
        String specialty = txtTherapistSpecialty.getText();
        int contact = Integer.parseInt(txtContactNumber.getText());
        String mail = txtEmail.getText();
        String availability = txtAvilability.getText();

        // Validate input fields
        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty() || mail.isEmpty() || availability.isEmpty() ) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        TherapistDTO therapistDTO = new TherapistDTO(id, name, specialty, contact, mail, availability);

        try {
            boolean isSaved = therapistBO.saveTherapist(therapistDTO);

            if (isSaved) {
                showAlert("Success", "Therapist saved successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to save therapist! Possible duplicate ID.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log error details
            showAlert("Error", "An unexpected error occurred!", Alert.AlertType.ERROR);
        }

        loadTherapists(); // Refresh therapist list
        clearFields(); // Clear input fields
    }


    @FXML
    void btnUpdate_OnAction(ActionEvent event) {
        String id = txtTherapistId.getText();
        String name = txtTherapistName.getText();
        String specialty = txtTherapistSpecialty.getText();
        int contact = Integer.parseInt(txtContactNumber.getText());
        String mail = txtEmail.getText();
        String availability = txtAvilability.getText();

        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty() || mail.isEmpty() || availability.isEmpty() ) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        try {
            TherapistDTO therapistDTO = new TherapistDTO(id, name, specialty, contact, mail, availability);

            boolean isUpdate = therapistBO.updateTherapist(therapistDTO);
            if (isUpdate) {
                showAlert("Success", "Therapist update successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to update therapist!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadTherapists();
        clearFields();
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        TherapistTM selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();
        if (selectedTherapist == null) {
            showAlert("Warning", "Please select a therapist to delete!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapist?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDelete = therapistBO.deleteTherapist(selectedTherapist.getTherapistID());

                if (isDelete) {
                    showAlert("Success", "Therapist deleted successfully!", Alert.AlertType.INFORMATION);

                } else {
                    showAlert("Error", "Failed to delete therapist!", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            loadTherapists();
            clearFields();
        }
    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    @FXML
    void tblTherapistsOnClick(MouseEvent event) {
        TherapistTM selectedItem = tblTherapists.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a therapist", Alert.AlertType.WARNING);
        }else {
            txtTherapistId.setText(selectedItem.getTherapistID());
            txtTherapistName.setText(selectedItem.getTherapistName());
            txtTherapistSpecialty.setText(selectedItem.getSpecialization());
            txtContactNumber.setText(String.valueOf(selectedItem.getContactNumber()));
            txtEmail.setText(selectedItem.getMail());
            txtAvilability.setText(selectedItem.getAvailability());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }

    private void clearFields() {
        txtTherapistId.clear();
        txtTherapistName.clear();
        txtTherapistSpecialty.clear();
        txtContactNumber.clear();
        txtEmail.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
