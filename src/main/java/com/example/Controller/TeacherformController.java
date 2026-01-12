package com.example.dsgroup1.Controller;

import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.FileWriter;
import java.io.IOException;

public class TeacherformController {

    @FXML
    private TextField teacherIdField;

    @FXML
    private TextField teacherNameField;

    @FXML
    private TextField teacherEmailField;

    @FXML
    private TextField teacherPhoneField;

    @FXML
    private TableView<Teacher> teacherTable;

    @FXML
    private TableColumn<Teacher, String> teacherIdColumn;

    @FXML
    private TableColumn<Teacher, String> teacherNameColumn;

    @FXML
    private TableColumn<Teacher, String> teacherEmailColumn;

    @FXML
    private TableColumn<Teacher, String> teacherPhoneColumn;

    private final ObservableList<Teacher> teacherList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize TableView columns
        teacherIdColumn.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        teacherEmailColumn.setCellValueFactory(new PropertyValueFactory<>("teacherEmail"));
        teacherPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("teacherPhone"));

        // Set items in the TableView
        teacherTable.setItems(teacherList);
    }

    @FXML
    private void handleAdd() {
        String teacherId = teacherIdField.getText().trim();
        String teacherName = teacherNameField.getText().trim();
        String teacherEmail = teacherEmailField.getText().trim();
        String teacherPhone = teacherPhoneField.getText().trim();

        if (teacherId.isEmpty() || teacherName.isEmpty() || teacherEmail.isEmpty() || teacherPhone.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add new teacher to the list
        Teacher teacher = new Teacher(teacherId, teacherName, teacherEmail, teacherPhone);
        teacherList.add(teacher);

        // Clear fields
        clearFields();
    }

    @FXML
    private void handleRemove() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            teacherList.remove(selectedTeacher);
        } else {
            showAlert("Error", "Please select a teacher to remove.");
        }
    }

    @FXML
    private void handleSubmit() {
        String csvFile = "teachers.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (Teacher teacher : teacherList) {
                String[] record = {teacher.getTeacherId(), teacher.getTeacherName(), teacher.getTeacherEmail(), teacher.getTeacherPhone()};
                writer.writeNext(record);
            }
            showAlert("Success", "Teachers saved successfully.");
            teacherList.clear(); // Clear the list after saving
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save teachers.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        teacherIdField.clear();
        teacherNameField.clear();
        teacherEmailField.clear();
        teacherPhoneField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class for Teacher
    public static class Teacher {
        private final String teacherId;
        private final String teacherName;
        private final String teacherEmail;
        private final String teacherPhone;

        public Teacher(String teacherId, String teacherName, String teacherEmail, String teacherPhone) {
            this.teacherId = teacherId;
            this.teacherName = teacherName;
            this.teacherEmail = teacherEmail;
            this.teacherPhone = teacherPhone;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public String getTeacherEmail() {
            return teacherEmail;
        }

        public String getTeacherPhone() {
            return teacherPhone;
        }
    }
}
