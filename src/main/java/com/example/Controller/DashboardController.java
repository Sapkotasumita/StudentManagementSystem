package com.example.dsgroup1.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DashboardController {

    private Map<String, Stage> openStages = new HashMap<>();

    @FXML
    private void loadAttendance() {
        loadForm("attendance.fxml");
    }

    @FXML
    private void loadCounseling() {
        loadForm("counseling.fxml");
    }

    @FXML
    private void loadCourses() {
        loadForm("Courses.fxml");
    }

    @FXML
    private void loadLibrary() {
        loadForm("library.fxml");
    }

    @FXML
    private void loadReports() {
        loadForm("reports.fxml");
    }

    @FXML
    private void loadTasks() {
        loadForm("tasks.fxml");
    }

    @FXML
    private void loadAdminForm() {
        loadForm("adminform.fxml");
    }

    @FXML
    private void loadStudentForm() {
        loadForm("studentform.fxml");
    }

    @FXML
    private void loadStudentProblemForm() {
        loadForm("studentproblemform.fxml");
    }

    @FXML
    private void loadTeacherForm() {
        loadForm("teacherform.fxml");
    }

    @FXML
    private void loadQuestionForm() {
        loadForm("questionform.fxml");
    }

    @FXML
    private void loadQuestionBank() {
        loadForm("questionbank.fxml");
    }

    private void loadForm(String fxmlFile) {
        try {
            String resourcePath = "/com/example/dsgroup1/View/" + fxmlFile;
            URL resourceUrl = getClass().getResource(resourcePath);

            if (resourceUrl == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(fxmlFile.replace(".fxml", ""));
            stage.show();

            openStages.put(fxmlFile, stage);
            stage.setOnCloseRequest(event -> openStages.remove(fxmlFile));
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlFile);
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Do you want to logout?");
        alert.setContentText("Are you sure you want to logout?");

        // Get user response
        Optional<ButtonType> result = alert.showAndWait();

        // User chose OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Close all open stages
            for (Stage stage : openStages.values()) {
                stage.close();
            }
            openStages.clear();

            // Close the current stage (dashboard)
            Stage currentStage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Load the login view
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dsgroup1/View/login.fxml"));
                Parent root = loader.load();

                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(root));
                loginStage.setTitle("Login");
                loginStage.show();
            } catch (IOException e) {
                System.err.println("Error loading login.fxml");
                e.printStackTrace();
            }
        }
        // No need to call event.consume() here
    }

    public void handlelogout(WindowEvent event) {
    }
}
