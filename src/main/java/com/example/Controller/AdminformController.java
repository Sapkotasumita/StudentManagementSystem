package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.AdminRecord;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;

public class AdminformController {

    @FXML
    private TextField adminIdField;

    @FXML
    private TextField adminNameField;

    @FXML
    private TextField adminEmailField;

    @FXML
    private TextField adminPhoneField;

    @FXML
    private TableView<AdminRecord> adminTable;

    @FXML
    private TableColumn<AdminRecord, String> adminIdColumn;

    @FXML
    private TableColumn<AdminRecord, String> adminNameColumn;

    @FXML
    private TableColumn<AdminRecord, String> adminEmailColumn;

    @FXML
    private TableColumn<AdminRecord, String> adminPhoneColumn;

    private ObservableList<AdminRecord> adminData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (adminIdField == null || adminNameField == null || adminEmailField == null || adminPhoneField == null) {
            System.err.println("One or more FXML fields are null!");
        } else {
            System.out.println("All FXML fields are properly initialized.");
        }

        adminIdColumn.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<>("adminName"));
        adminEmailColumn.setCellValueFactory(new PropertyValueFactory<>("adminEmail"));
        adminPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("adminPhone"));

        adminTable.setItems(adminData);
    }

    @FXML
    private void handleAdd() {
        String adminId = adminIdField.getText().trim();
        String adminName = adminNameField.getText().trim();
        String adminEmail = adminEmailField.getText().trim();
        String adminPhone = adminPhoneField.getText().trim();

        if (adminId.isEmpty() || adminName.isEmpty() || adminEmail.isEmpty() || adminPhone.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        adminData.add(new AdminRecord(adminId, adminName, adminEmail, adminPhone));
        clearFields();
    }

    @FXML
    private void handleRemove() {
        int selectedIndex = adminTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            adminTable.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No record selected. Please select a record in the table.");
        }
    }

    @FXML
    private void handleSave() {
        String csvFile = "admins.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (AdminRecord record : adminData) {
                String[] csvRecord = {record.getAdminId(), record.getAdminName(), record.getAdminEmail(), record.getAdminPhone()};
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "All admin records saved successfully.");
            adminData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save admin details.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        adminIdField.clear();
        adminNameField.clear();
        adminEmailField.clear();
        adminPhoneField.clear();
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
