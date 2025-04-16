package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ManageInvoiceFormController {

    @FXML
    private JFXButton btnAddNewPatient;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnExportPDF;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colInvoiceId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private DatePicker dpInvoiceDate;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<?> tblInvoice;

    @FXML
    private TextField txtInvoiceId;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtTotalAmount;

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {

    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {

    }

    @FXML
    void btnExportPDF_OnAction(ActionEvent event) {

    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    @FXML
    void searchPatient(KeyEvent event) {

    }

}
