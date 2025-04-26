package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.custom.ReportBO;
import lk.ijse.bo.custom.impl.ReportBOImpl;
import lk.ijse.dto.TherapistPerformanceDTO;
import lk.ijse.view.tdm.TherapistPerformanceTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {

    @FXML
    private TableColumn<TherapistPerformanceTM, String> colName;

    @FXML
    private TableColumn<TherapistPerformanceTM, Integer> colPatients;

    @FXML
    private TableColumn<TherapistPerformanceTM, Integer> colSessions;

    @FXML
    private TableColumn<TherapistPerformanceTM, String> colTherapistId;

    @FXML
    private ImageView imgHome;

    @FXML
    private BarChart<String, Number> sessionChart;

    @FXML
    private TableView<TherapistPerformanceTM> therapistTable;
    private final ReportBO reportBO = new ReportBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colSessions.setCellValueFactory(new PropertyValueFactory<>("sessions"));
        colPatients.setCellValueFactory(new PropertyValueFactory<>("totalPatients"));

        loadData();
    }

    private void loadData() {
        List<TherapistPerformanceDTO> dataList = reportBO.fetchTherapistPerformance();

        List<TherapistPerformanceTM> tmList = new ArrayList<>();
        for (TherapistPerformanceDTO dto : dataList) {
            tmList.add(new TherapistPerformanceTM(
                    dto.getTherapistId(),
                    dto.getTherapistName(),
                    dto.getSessions(),
                    dto.getTotalPatients()
            ));
        }

        ObservableList<TherapistPerformanceTM> observableList = FXCollections.observableArrayList(tmList);
        therapistTable.setItems(observableList);

        loadChart(tmList);
    }

    private void loadChart(List<TherapistPerformanceTM> tmList) {
        sessionChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sessions Count");

        for (TherapistPerformanceTM tm : tmList) {
            series.getData().add(new XYChart.Data<>(tm.getTherapistName(), tm.getSessions()));
        }

        sessionChart.getData().add(series);
    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    @FXML
    void onDownloadPdf(ActionEvent event) {
        List<TherapistPerformanceDTO> dataList = reportBO.fetchTherapistPerformance();
        reportBO.exportReportToPdf(dataList);

    }

    @FXML
    void onRefresh(ActionEvent event) {
        loadData();

    }


}
