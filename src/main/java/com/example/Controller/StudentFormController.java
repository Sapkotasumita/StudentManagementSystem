package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.StudentRecord;
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
import javafx.scene.control.TableView.TableViewSelectionModel;

import java.io.FileWriter;
import java.io.IOException;

public class StudentFormController {

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField facultyField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNoField;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private TableView<StudentRecord> studentTable;

    @FXML
    private TableColumn<StudentRecord, String> studentIdColumn;

    @FXML
    private TableColumn<StudentRecord, String> usernameColumn;

    @FXML
    private TableColumn<StudentRecord, String> passwordColumn;

    @FXML
    private TableColumn<StudentRecord, String> firstNameColumn;

    @FXML
    private TableColumn<StudentRecord, String> lastNameColumn;

    @FXML
    private TableColumn<StudentRecord, String> facultyColumn;

    @FXML
    private TableColumn<StudentRecord, String> emailColumn;

    @FXML
    private TableColumn<StudentRecord, String> phoneNoColumn;

    @FXML
    private TableColumn<StudentRecord, String> genderColumn;

    private ObservableList<StudentRecord> studentData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize ChoiceBox
        genderChoiceBox.getItems().addAll("Male", "Female", "Others");

        // Initialize TableView columns
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Set TableView data
        studentTable.setItems(studentData);
    }

    @FXML
    private void handleAdd() {
        String studentId = studentIdField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String faculty = facultyField.getText().trim();
        String email = emailField.getText().trim();
        String phoneNo = phoneNoField.getText().trim();
        String gender = genderChoiceBox.getValue();

        // Validate fields
        if (studentId.isEmpty() || username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || faculty.isEmpty() || email.isEmpty() || phoneNo.isEmpty() || gender == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add to TableView
        studentData.add(new StudentRecord(studentId, username, password, firstName, lastName, faculty, email, phoneNo, gender));
        clearFields();
    }

    @FXML
    private void handleRemove() {
        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            studentTable.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No record selected. Please select a record in the table.");
        }
    }

    @FXML
    private void handleSubmit() {
        String csvFile = "students.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (StudentRecord record : studentData) {
                String[] csvRecord = {
                        record.getStudentId(),
                        record.getUsername(),
                        record.getPassword(),
                        record.getFirstName(),
                        record.getLastName(),
                        record.getFaculty(),
                        record.getEmail(),
                        record.getPhoneNo(),
                        record.getGender()
                };
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "All student details saved successfully.");
            studentData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save student details.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        studentIdField.clear();
        usernameField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        facultyField.clear();
        emailField.clear();
        phoneNoField.clear();
        genderChoiceBox.setValue(null);
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
