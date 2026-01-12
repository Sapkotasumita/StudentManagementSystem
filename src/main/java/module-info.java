module com.example.dsgroup1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires com.opencsv;

    opens com.example.dsgroup1.model to javafx.base;
    opens com.example.dsgroup1.Controller to javafx.fxml;
    opens com.example.dsgroup1 to javafx.fxml;

    exports com.example.dsgroup1;
    exports com.example.dsgroup1.model;
    exports com.example.dsgroup1.Controller;
}




