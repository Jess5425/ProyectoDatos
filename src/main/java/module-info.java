module com.example.ctd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ctd to javafx.fxml;
    exports com.example.ctd;
}