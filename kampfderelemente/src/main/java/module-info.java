module com.example.kampfderelemente {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kampfderelemente to javafx.fxml;
    exports com.example.kampfderelemente;
}