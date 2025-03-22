package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.TherapistBOImpl;
import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ManageTherapistFormController {

    @FXML
    private JFXButton btnAddNewTherapist, btnDelete, btnSave, btnUpdate, btnViewTherapyPrograme;

    @FXML
    private TableColumn<Therapist, String> clmTherapisstAvailability, clmTherapistId, clmTherapistName, clmTherapistSpeciality;

    @FXML
    private TableView<Therapist> tblTherapists;

    @FXML
    private TextField txtTherapistAvailability, txtTherapistId, txtTherapistName, txtTherapistSpecialty;

    @FXML
    private AnchorPane viewTherapyProgramePane;

    private final ObservableList<Therapist> therapistList = FXCollections.observableArrayList();

    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    private final TherapistBO therapistBO = new TherapistBOImpl();

    @FXML
    public void initialize() {
        // Configure table columns
//        clmTherapistId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
//        clmTherapistName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
//        clmTherapistSpeciality.setCellValueFactory(cellData -> cellData.getValue().specializationProperty());
//        clmTherapisstAvailability.setCellValueFactory(cellData -> cellData.getValue().availabilityProperty());

        clmTherapistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmTherapistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmTherapistSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        clmTherapisstAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load data into table
        loadTherapists();
    }

    private void loadTherapists() {
        therapistList.clear();
        try (Session session = factoryConfiguration.getInstance().getSession()) {
            Query<Therapist> query = session.createQuery("FROM Therapist", Therapist.class);
            List<Therapist> therapists = query.list();
            therapistList.addAll(therapists);
            tblTherapists.setItems(therapistList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
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
        String availability = txtTherapistAvailability.getText();

        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty() || availability.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        TherapistDTO therapistDTO = new TherapistDTO(id, name, specialty, availability);

//        try (Session session = factoryConfiguration.getInstance().getSession()) {
//            Transaction tx = session.beginTransaction();
//            session.save(therapist);
//            tx.commit();
//        } catch (Exception e) {
//
//        }
        try{
            boolean isSaved = therapistBO.saveTherapist(therapistDTO);

            if (isSaved) {
                showAlert("Success", "Therapist save successfully!", Alert.AlertType.INFORMATION);

            } else {
                showAlert("Error", "Failed to save therapist!", Alert.AlertType.ERROR);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        loadTherapists();
        clearFields();
    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {
        String id = txtTherapistId.getText();
        String name = txtTherapistName.getText();
        String specialty = txtTherapistSpecialty.getText();
        String availability = txtTherapistAvailability.getText();

        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty() || availability.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        try (Session session = factoryConfiguration.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            Therapist therapist = session.get(Therapist.class, id);
            if (therapist != null) {
                therapist.setName(name);
                therapist.setSpecialization(specialty);
                therapist.setAvailability(availability);
                session.update(therapist);
                tx.commit();
            } else {
                showAlert("Error", "Therapist not found!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to update therapist!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        loadTherapists();
        clearFields();
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        Therapist selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();
        if (selectedTherapist == null) {
            showAlert("Warning", "Please select a therapist to delete!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapist?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try (Session session = factoryConfiguration.getInstance().getSession()) {
                Transaction tx = session.beginTransaction();
                session.delete(selectedTherapist);
                tx.commit();
            } catch (Exception e) {
                showAlert("Error", "Failed to delete therapist!", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
            loadTherapists();
            clearFields();
        }
    }

    @FXML
    void btnViewTherapyProgrameOnAction(ActionEvent event) {
        loadUI("/view/Therapist-Table-View.fxml");
    }

    @FXML
    void navigateToHome(MouseEvent event) {
        loadUI("/view/Home.fxml");
    }

    private void loadUI(String resource) {
        viewTherapyProgramePane.getChildren().clear();
        try {
            viewTherapyProgramePane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load the requested view!", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        txtTherapistId.clear();
        txtTherapistName.clear();
        txtTherapistSpecialty.clear();
        txtTherapistAvailability.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
