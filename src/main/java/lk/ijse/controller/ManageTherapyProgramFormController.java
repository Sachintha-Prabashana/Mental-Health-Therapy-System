package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.TherapyProgramsBO;
import lk.ijse.bo.custom.impl.TherapyProgramsBOImpl;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.dto.TherapyProgramDTO;
import lk.ijse.view.tdm.TherapistTM;
import lk.ijse.view.tdm.TherapyProgramTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageTherapyProgramFormController implements Initializable {

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
    private TableColumn<TherapyProgramTM, Integer> clmDuration;

    @FXML
    private TableColumn<TherapyProgramTM, Double> clmFee;

    @FXML
    private TableColumn<TherapyProgramTM, String> clmProgramId;

    @FXML
    private TableColumn<TherapyProgramTM, String> clmProgramName;

    @FXML
    private TableColumn<TherapyProgramTM, String> clmTherapistsId;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<TherapyProgramTM> tblTherapyPrograms;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtProgramDuration;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtProgramName;

    @FXML
    private TextField txtTherapistId;

    private final TherapyProgramsBO therapyProgramsBO = new TherapyProgramsBOImpl();
    private static ManageTherapyProgramFormController instance;

     // set therapist id to text field
    public ManageTherapyProgramFormController(){
        instance = this;
    }

    public static ManageTherapyProgramFormController getInstance() {
        return instance;
    }

    public void setTherapistId(String id) {
        txtTherapistId.setText(id);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmProgramId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        clmFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        clmTherapistsId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));

        loadTherapyPrograms();
        generateNewID();
    }

    private void generateNewID() {
        txtProgramId.setText(therapyProgramsBO.getNextTherapyProgramId());
    }

    private void loadTherapyPrograms() {
        try  {
            ArrayList<TherapyProgramDTO> therapyProgramDTOS = therapyProgramsBO.loadAllTherapyPrograms();
            ObservableList<TherapyProgramTM> therapyProgramTMSList = FXCollections.observableArrayList();

            for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {

                TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                        therapyProgramDTO.getId(),
                        therapyProgramDTO.getName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee(),
                        therapyProgramDTO.getTherapistId()
                );

                therapyProgramTMSList.add(therapyProgramTM);
            }
            tblTherapyPrograms.setItems(therapyProgramTMSList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }

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
        String id = txtProgramId.getText();
        String name = txtProgramName.getText();
        int duration = Integer.parseInt(txtProgramDuration.getText());
        double fee = Double.parseDouble(txtFee.getText());
        String therapistId = txtTherapistId.getText();

        if(id.isEmpty() || name.isEmpty() || duration < 0 || fee < 0 || therapistId.isEmpty())  {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(id, name, duration, fee, therapistId);
        try{
            boolean isSaved = therapyProgramsBO.saveTherapyPrograms(therapyProgramDTO);

            if (isSaved) {
                showAlert("Success", "Therapy Program save successfully!", Alert.AlertType.INFORMATION);

            } else {
                showAlert("Error", "Failed to save Therapy Program!", Alert.AlertType.ERROR);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
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
