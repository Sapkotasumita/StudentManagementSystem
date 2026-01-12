package com.example.dsgroup1.Controller;

import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private void handleSignup() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String csvFile = "users.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            String[] record = {username, password};
            writer.writeNext(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        // Logic to handle cancellation
    }
}
