package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.ReportRecord;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;

public class ReportsController {

    @FXML
    private TextField reportIdField;

    @FXML
    private TextField reportNameField;

    @FXML
    private TextField reportContentField;

    @FXML
    private ChoiceBox<String> progressChoiceBox; // ChoiceBox for progress

    @FXML
    private ChoiceBox<String> gradeChoiceBox; // ChoiceBox for grade

    @FXML
    private TableView<ReportRecord> reportTable;

    @FXML
    private TableColumn<ReportRecord, String> reportIdColumn;

    @FXML
    private TableColumn<ReportRecord, String> reportNameColumn;

    @FXML
    private TableColumn<ReportRecord, String> reportContentColumn;

    @FXML
    private TableColumn<ReportRecord, String> progressColumn;

    @FXML
    private TableColumn<ReportRecord, String> gradeColumn;

    private ObservableList<ReportRecord> reportData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize ChoiceBoxes
        progressChoiceBox.getItems().addAll("A", "B", "C");
        gradeChoiceBox.getItems().addAll("A", "B", "C");

        // Initialize TableView columns
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        reportNameColumn.setCellValueFactory(new PropertyValueFactory<>("reportName"));
        reportContentColumn.setCellValueFactory(new PropertyValueFactory<>("reportContent"));
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // Set the TableView data
        reportTable.setItems(reportData);
    }

    @FXML
    private void handleAdd() {
        String reportId = reportIdField.getText().trim();
        String reportName = reportNameField.getText().trim();
        String reportContent = reportContentField.getText().trim();
        String progress = progressChoiceBox.getValue();
        String grade = gradeChoiceBox.getValue();

        // Validate fields
        if (reportId.isEmpty() || reportName.isEmpty() || reportContent.isEmpty() || progress == null || grade == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add to TableView
        reportData.add(new ReportRecord(reportId, reportName, reportContent, progress, grade));
        clearFields();
    }

    @FXML
    private void handleRemove() {
        int selectedIndex = reportTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            reportTable.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No record selected. Please select a record in the table.");
        }
    }

    @FXML
    private void handleSubmit() {
        String csvFile = "reports.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (ReportRecord record : reportData) {
                String[] csvRecord = {record.getReportId(), record.getReportName(), record.getReportContent(), record.getProgress(), record.getGrade()};
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "All report details saved successfully.");
            reportData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save report details.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        reportIdField.clear();
        reportNameField.clear();
        reportContentField.clear();
        progressChoiceBox.setValue(null);
        gradeChoiceBox.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
