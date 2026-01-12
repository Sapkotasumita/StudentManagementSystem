package com.example.dsgroup1.Controller;

import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class Registercontroller {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    public void initialize() {
        // Initialize the ChoiceBox with roles
        roleChoiceBox.getItems().addAll("Teacher", "Student", "Staff", "Librarian");
        roleChoiceBox.setValue("Student"); // Set a default value
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleChoiceBox.getValue();

        // Validate password and confirm password match
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        String csvFile = "users.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            String[] record = {username, password, role};
            writer.writeNext(record);
            showAlert("Success", "User registered successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to register user.");
        }
    }

    @FXML
    private void handleCancel() {
        // Logic to handle cancellation, if needed
        // For example, close the registration window
        Stage stage = (Stage) usernameField.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
