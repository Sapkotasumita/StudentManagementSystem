package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.CounselingRecord;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class CounselingController {

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField counselorField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<CounselingRecord> counselingTable;

    @FXML
    private TableColumn<CounselingRecord, String> studentIdColumn;

    @FXML
    private TableColumn<CounselingRecord, String> counselorColumn;

    @FXML
    private TableColumn<CounselingRecord, String> dateColumn;

    private ObservableList<CounselingRecord> counselingData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        counselorColumn.setCellValueFactory(new PropertyValueFactory<>("counselor"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        counselingTable.setItems(counselingData);
    }

    @FXML
    private void handleAdd() {
        String studentId = studentIdField.getText().trim();
        String counselor = counselorField.getText().trim();
        LocalDate date = datePicker.getValue();

        // Validate fields
        if (studentId.isEmpty() || counselor.isEmpty() || date == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add to TableView
        counselingData.add(new CounselingRecord(studentId, counselor, date.toString()));
        clearFields();
    }

    @FXML
    private void handleRemove() {
        int selectedIndex = counselingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            counselingTable.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No record selected. Please select a record in the table.");
        }
    }

    @FXML
    private void handleSave() {
        String csvFile = "counseling.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (CounselingRecord record : counselingData) {
                String[] csvRecord = {record.getStudentId(), record.getCounselor(), record.getDate()};
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "All counseling records saved successfully.");
            counselingData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save counseling records.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        if (studentIdField != null) {
            studentIdField.clear();
        }
        if (counselorField != null) {
            counselorField.clear();
        }
        if (datePicker != null) {
            datePicker.setValue(null);
        }
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
