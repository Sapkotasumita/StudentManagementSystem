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
import java.time.LocalDate;

public class TaskController {

    @FXML
    private TextField taskIdField;

    @FXML
    private TextArea taskDescription;

    @FXML
    private DatePicker taskDueDate;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> taskIdColumn;

    @FXML
    private TableColumn<Task, String> taskDescriptionColumn;

    @FXML
    private TableColumn<Task, String> taskDueDateColumn;

    private final ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize TableView columns
        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskDueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        // Set items in the TableView
        taskTable.setItems(taskList);
    }

    @FXML
    private void handleAdd() {
        String taskId = taskIdField.getText().trim();
        String description = taskDescription.getText().trim();
        LocalDate dueDate = taskDueDate.getValue();

        if (taskId.isEmpty() || description.isEmpty() || dueDate == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add new task to the list
        Task task = new Task(taskId, description, dueDate.toString());
        taskList.add(task);

        // Clear fields
        clearFields();
    }

    @FXML
    private void handleRemove() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskList.remove(selectedTask);
        } else {
            showAlert("Error", "Please select a task to remove.");
        }
    }

    @FXML
    private void handleSubmit() {
        String csvFile = "tasks.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (Task task : taskList) {
                String[] record = {task.getTaskId(), task.getDescription(), task.getDueDate()};
                writer.writeNext(record);
            }
            showAlert("Success", "Tasks saved successfully.");
            taskList.clear(); // Clear the list after saving
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save tasks.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        taskIdField.clear();
        taskDescription.clear();
        taskDueDate.setValue(null); // Clear the DatePicker
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class for Task
    public static class Task {
        private final String taskId;
        private final String description;
        private final String dueDate;

        public Task(String taskId, String description, String dueDate) {
            this.taskId = taskId;
            this.description = description;
            this.dueDate = dueDate;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getDescription() {
            return description;
        }

        public String getDueDate() {
            return dueDate;
        }
    }
}

