package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TherapySessionTableFormController {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnReschedule;

    @FXML
    private TableColumn<?, ?> clmDate;

    @FXML
    private TableColumn<?, ?> clmPatientId;

    @FXML
    private TableColumn<?, ?> clmProgramId;

    @FXML
    private TableColumn<?, ?> clmSessionId;

    @FXML
    private TableColumn<?, ?> clmStatus;

    @FXML
    private TableColumn<?, ?> clmTherapistId;

    @FXML
    private TableColumn<?, ?> clmTime;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<?> tblTherapySession;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnRescheduleOnAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

}
