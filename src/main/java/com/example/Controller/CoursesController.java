package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.CourseRecord;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;

public class CoursesController {

    @FXML
    private TextField courseIdField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField instructorField;

    @FXML
    private TextField courseDurationField;

    @FXML
    private ChoiceBox<String> courseTypeChoiceBox;

    @FXML
    private TableView<CourseRecord> coursesTable;

    @FXML
    private TableColumn<CourseRecord, String> courseIdColumn;

    @FXML
    private TableColumn<CourseRecord, String> courseNameColumn;

    @FXML
    private TableColumn<CourseRecord, String> instructorColumn;

    @FXML
    private TableColumn<CourseRecord, String> courseDurationColumn;

    @FXML
    private TableColumn<CourseRecord, String> courseTypeColumn;

    private ObservableList<CourseRecord> courseData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize ChoiceBox with options
        courseTypeChoiceBox.getItems().addAll("OSCN", "DBMS", "OOP", "Others");

        // Initialize TableView columns
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        instructorColumn.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        courseDurationColumn.setCellValueFactory(new PropertyValueFactory<>("courseDuration"));
        courseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("courseType"));

        // Set items to the TableView
        coursesTable.setItems(courseData);
    }

    @FXML
    private void handleAdd() {
        String courseId = courseIdField.getText().trim();
        String courseName = courseNameField.getText().trim();
        String instructor = instructorField.getText().trim();
        String duration = courseDurationField.getText().trim();
        String courseType = courseTypeChoiceBox.getValue(); // Get selected course type

        // Validate fields
        if (courseId.isEmpty() || courseName.isEmpty() || instructor.isEmpty() || duration.isEmpty() || courseType == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add to TableView
        courseData.add(new CourseRecord(courseId, courseName, instructor, duration, courseType));
        clearFields();
    }

    @FXML
    private void handleRemove() {
        int selectedIndex = coursesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            coursesTable.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No record selected. Please select a record in the table.");
        }
    }

    @FXML
    private void handleSave() {
        String csvFile = "courses.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (CourseRecord record : courseData) {
                String[] csvRecord = {record.getCourseId(), record.getCourseName(), record.getInstructor(), record.getCourseDuration(), record.getCourseType()};
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "Course details saved successfully.");
            courseData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save course details.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        courseIdField.clear();
        courseNameField.clear();
        instructorField.clear();
        courseDurationField.clear();
        courseTypeChoiceBox.getSelectionModel().clearSelection();
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
