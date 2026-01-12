package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.StudentProblemRecord;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;

public class StudentProblemFormController {

    @FXML
    private TextField problemIdField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField scIdField;

    @FXML
    private TextField creatorNameField;

    @FXML
    private TextField questionIdField;

    @FXML
    private TextField questionPositionField;

    @FXML
    private TextField questionTextField;

    @FXML
    private TableView<StudentProblemRecord> problemTable;

    @FXML
    private TableColumn<StudentProblemRecord, String> problemIdColumn;

    @FXML
    private TableColumn<StudentProblemRecord, String> titleColumn;

    @FXML
    private TableColumn<StudentProblemRecord, String> scIdColumn;

    @FXML
    private TableColumn<StudentProblemRecord, String> creatorNameColumn;

    @FXML
    private TableColumn<StudentProblemRecord, String> questionIdColumn;

    @FXML
    private TableColumn<StudentProblemRecord, String> questionPositionColumn;

    @FXML
    private TableColumn<StudentProblemRecord, String> questionTextColumn;

    private ObservableList<StudentProblemRecord> problemData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize TableView columns
        problemIdColumn.setCellValueFactory(new PropertyValueFactory<>("problemId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        scIdColumn.setCellValueFactory(new PropertyValueFactory<>("scId"));
        creatorNameColumn.setCellValueFactory(new PropertyValueFactory<>("creatorName"));
        questionIdColumn.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        questionPositionColumn.setCellValueFactory(new PropertyValueFactory<>("questionPosition"));
        questionTextColumn.setCellValueFactory(new PropertyValueFactory<>("questionText"));

        // Set TableView data
        problemTable.setItems(problemData);
    }

    @FXML
    private void handleAdd() {
        String problemId = problemIdField.getText().trim();
        String title = titleField.getText().trim();
        String scId = scIdField.getText().trim();
        String creatorName = creatorNameField.getText().trim();
        String questionId = questionIdField.getText().trim();
        String questionPosition = questionPositionField.getText().trim();
        String questionText = questionTextField.getText().trim();

        // Validate fields
        if (problemId.isEmpty() || title.isEmpty() || scId.isEmpty() || creatorName.isEmpty() || questionId.isEmpty() || questionPosition.isEmpty() || questionText.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add to TableView
        problemData.add(new StudentProblemRecord(problemId, title, scId, creatorName, questionId, questionPosition, questionText));
        clearFields();
    }

    @FXML
    private void handleRemove() {
        int selectedIndex = problemTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            problemTable.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No record selected. Please select a record in the table.");
        }
    }

    @FXML
    private void handleSubmit() {
        String csvFile = "student_problems.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (StudentProblemRecord record : problemData) {
                String[] csvRecord = {
                        record.getProblemId(),
                        record.getTitle(),
                        record.getScId(),
                        record.getCreatorName(),
                        record.getQuestionId(),
                        record.getQuestionPosition(),
                        record.getQuestionText()
                };
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "All student problem details saved successfully.");
            problemData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save student problem details.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        problemIdField.clear();
        titleField.clear();
        scIdField.clear();
        creatorNameField.clear();
        questionIdField.clear();
        questionPositionField.clear();
        questionTextField.clear();
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
