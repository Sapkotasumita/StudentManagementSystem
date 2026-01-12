package com.example.dsgroup1.Controller;

import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class QuestionBankcontroller {

    @FXML
    private ListView<String> questionListView;

    @FXML
    private TextField questionField;

    @FXML
    private TextField answerField;

    @FXML
    private ChoiceBox<String> gradeChoiceBox;

    @FXML
    private TableView<Question> questionTable;

    @FXML
    private TableColumn<Question, String> questionColumn;

    @FXML
    private TableColumn<Question, String> answerColumn;

    @FXML
    private TableColumn<Question, String> gradeColumn;

    private ObservableList<Question> questionData = FXCollections.observableArrayList();
    private ObservableList<String> gradeOptions = FXCollections.observableArrayList("A", "B", "C");

    @FXML
    public void initialize() {
        // Initialize the question list view with some default questions
        questionListView.getItems().addAll(
                "What is polymorphism in OOP?",
                "Explain the concept of inheritance.",
                "What is a binary tree?",
                "Describe the MVC pattern.",
                "Write a code to throw custom exception.",
                "Write 5 types of exceptions."
        );

        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        questionTable.setItems(questionData);
        gradeChoiceBox.setItems(gradeOptions);
    }

    @FXML
    public void handleAddQuestion() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Question");
        dialog.setHeaderText("Add a new question to the question bank");
        dialog.setContentText("Question:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(question -> questionListView.getItems().add(question));
    }

    @FXML
    public void handleRemoveQuestion() {
        int selectedIndex = questionListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            questionListView.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No question selected. Please select a question in the list.");
        }
    }

    @FXML
    public void handleSaveQuestion() {
        String question = questionField.getText().trim();
        String answer = answerField.getText().trim();
        String grade = gradeChoiceBox.getValue();

        // Validate fields
        if (question.isEmpty() || answer.isEmpty() || grade == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Save to CSV
        String csvFile = "questionbank.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            String[] record = {question, answer, grade};
            writer.writeNext(record);
            showAlert("Success", "Question saved successfully.");
            questionData.add(new Question(question, answer, grade));
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save question.");
        }
    }

    @FXML
    public void handleSaveAll() {
        String csvFile = "questionbank.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (Question question : questionData) {
                String[] csvRecord = {question.getQuestion(), question.getAnswer(), question.getGrade()};
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "All questions saved successfully.");
            questionData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save questions.");
        }
    }

    @FXML
    public void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        questionField.clear();
        answerField.clear();
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

    public static class Question {
        private final String question;
        private final String answer;
        private final String grade;

        public Question(String question, String answer, String grade) {
            this.question = question;
            this.answer = answer;
            this.grade = grade;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getGrade() {
            return grade;
        }

        @Override
        public String toString() {
            return "Question: " + question + "\nAnswer: " + answer + "\nGrade: " + grade;
        }
    }
}
