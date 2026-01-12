package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.AttendanceRecord;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AttendanceController {

    @FXML
    private TextField studentIdField;

    @FXML
    private DatePicker dateField;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private TableView<AttendanceRecord> attendanceTable;

    @FXML
    private TableColumn<AttendanceRecord, String> studentIdColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> dateColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> statusColumn;

    private ObservableList<AttendanceRecord> attendanceData = FXCollections.observableArrayList();
    private ObservableList<String> statusOptions = FXCollections.observableArrayList("Present", "Absent");

    @FXML
    private void initialize() {
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        attendanceTable.setItems(attendanceData);
        statusChoiceBox.setItems(statusOptions);
    }

    @FXML
    private void handleAdd() {
        String studentId = studentIdField.getText().trim();
        String date = dateField.getValue() != null ? dateField.getValue().toString() : "";
        String status = statusChoiceBox.getValue();

        // Validate fields
        if (studentId.isEmpty() || date.isEmpty() || status == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Parse date
        LocalDate attendanceDate;
        try {
            attendanceDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            showAlert("Error", "Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        // Add to TableView
        attendanceData.add(new AttendanceRecord(studentId, attendanceDate.toString(), status));
        clearFields();
    }

    @FXML
    private void handleRemove() {
        int selectedIndex = attendanceTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            attendanceTable.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No record selected. Please select a record in the table.");
        }
    }

    @FXML
    private void handleSaveNewRecord() {
        String studentId = studentIdField.getText().trim();
        String date = dateField.getValue() != null ? dateField.getValue().toString() : "";
        String status = statusChoiceBox.getValue();

        // Validate fields
        if (studentId.isEmpty() || date.isEmpty() || status == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Parse date
        LocalDate attendanceDate;
        try {
            attendanceDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            showAlert("Error", "Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        // Save to CSV
        String csvFile = "attendance.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            String[] record = {studentId, attendanceDate.toString(), status};
            writer.writeNext(record);
            showAlert("Success", "Attendance saved successfully.");
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save attendance.");
        }
    }

    @FXML
    private void handleSaveAll() {
        String csvFile = "attendance.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (AttendanceRecord record : attendanceData) {
                String[] csvRecord = {record.getStudentId(), record.getDate(), record.getStatus()};
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "All attendance records saved successfully.");
            attendanceData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save attendance.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        studentIdField.clear();
        dateField.setValue(null);
        statusChoiceBox.setValue(null);
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
