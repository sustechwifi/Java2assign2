module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.client to javafx.fxml;
    exports com.example.client;
}