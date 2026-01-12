package com.example.dsgroup1.Controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private Button signInButton;

    @FXML
    private Button createAccountButton;

    @FXML
    public void initialize() {
        createAccountButton.setOnAction(event -> showRegisterPage());
        roleChoiceBox.getItems().addAll("Teacher", "Student", "Staff", "Librarian");
        roleChoiceBox.setValue("Student"); // Set a default value
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleChoiceBox.getValue();

        // Check credentials against CSV file
        if (authenticateUser(username, password, role)) {
            // Login successful, navigate to dashboard
            System.out.println("Login successful!");
            closeCurrentStage(); // Close login window
            showDashboard();
        } else {
            // Login failed, show error message
            System.out.println("Login failed!");
            showAlert("Login Failed", "Invalid username, password, or role.");
        }
    }

    private boolean authenticateUser(String username, String password, String role) {
        String csvFile = "users.csv";
        boolean authenticated = false;

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = reader.readAll();

            for (String[] record : records) {
                if (record.length >= 3 && record[0].equals(username) && record[1].equals(password) && record[2].equals(role)) {
                    authenticated = true;
                    break;
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return authenticated;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showRegisterPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dsgroup1/view/register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the registration page.");
        }
    }

    private void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dsgroup1/view/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();

            // Set the onCloseRequest event handler for the dashboard stage
            DashboardController controller = loader.getController();
            stage.setOnCloseRequest(event -> controller.handlelogout(event));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the dashboard page.");
        }
    }

    private void closeCurrentStage() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        if (stage != null) {
            stage.close();
        } else {
            System.out.println("Stage is null. Unable to close.");
        }
    }
}
