package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.QuestionRecord;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;

public class QuestionformController {

    @FXML
    private TextField questionIdField;

    @FXML
    private TextField questionText;

    @FXML
    private TextArea questionDescription;

    @FXML
    private TableView<QuestionRecord> questionTable;

    @FXML
    private TableColumn<QuestionRecord, String> questionIdColumn;

    @FXML
    private TableColumn<QuestionRecord, String> questionTextColumn;

    @FXML
    private TableColumn<QuestionRecord, String> questionDescriptionColumn;

    private ObservableList<QuestionRecord> questionData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize the columns.
        questionIdColumn.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        questionTextColumn.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        questionDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("questionDescription"));

        // Add data to the table.
        questionTable.setItems(questionData);
    }

    @FXML
    private void handleSave() {
        String questionId = questionIdField.getText().trim();
        String question = questionText.getText().trim();
        String description = questionDescription.getText().trim();

        // Validate fields
        if (questionId.isEmpty() || question.isEmpty() || description.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add to TableView
        questionData.add(new QuestionRecord(questionId, question, description));
        clearFields();

        // Save to CSV
        String csvFile = "questions.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            String[] record = {questionId, question, description};
            writer.writeNext(record);
            showAlert("Success", "Question details saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save question details.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        questionIdField.clear();
        questionText.clear();
        questionDescription.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
