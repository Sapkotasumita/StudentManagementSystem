package com.example.dsgroup1.Controller;

import com.example.dsgroup1.model.BookRecord;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;

public class LibraryController {

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField publicationField;

    @FXML
    private TableView<BookRecord> libraryTable;

    @FXML
    private TableColumn<BookRecord, String> bookIdColumn;

    @FXML
    private TableColumn<BookRecord, String> bookTitleColumn;

    @FXML
    private TableColumn<BookRecord, String> authorColumn;

    @FXML
    private TableColumn<BookRecord, String> publicationColumn;

    private ObservableList<BookRecord> libraryData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publicationColumn.setCellValueFactory(new PropertyValueFactory<>("publication"));

        libraryTable.setItems(libraryData);
    }

    @FXML
    private void handleAddBook() {
        String bookId = bookIdField.getText().trim();
        String bookTitle = bookTitleField.getText().trim();
        String author = authorField.getText().trim();
        String publication = publicationField.getText().trim();

        // Validate fields
        if (bookId.isEmpty() || bookTitle.isEmpty() || author.isEmpty() || publication.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Add to TableView
        libraryData.add(new BookRecord(bookId, bookTitle, author, publication));
        clearFields();
    }

    @FXML
    private void handleRemoveBook() {
        int selectedIndex = libraryTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            libraryTable.getItems().remove(selectedIndex);
        } else {
            showAlert("Warning", "No Selection", "No book selected. Please select a book in the table.");
        }
    }

    @FXML
    private void handleSubmit() {
        String csvFile = "library.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile, true))) {
            for (BookRecord record : libraryData) {
                String[] csvRecord = {record.getBookId(), record.getBookTitle(), record.getAuthor(), record.getPublication()};
                writer.writeNext(csvRecord);
            }
            showAlert("Success", "Library details saved successfully.");
            libraryData.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save library details.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
    }

    private void clearFields() {
        bookIdField.clear();
        bookTitleField.clear();
        authorField.clear();
        publicationField.clear();
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
