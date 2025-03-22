package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TherapistTableFormController {

    @FXML
    private TableColumn<?, ?> clmAvailability;

    @FXML
    private TableColumn<?, ?> clmSpeciality;

    @FXML
    private TableColumn<?, ?> clmTherapistId;

    @FXML
    private TableColumn<?, ?> clmTherapistName;

    @FXML
    private TableView<?> tblTherapist;

}
