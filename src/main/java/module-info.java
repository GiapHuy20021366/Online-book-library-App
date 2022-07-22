module com.example.library2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base;
    requires java.sql;


    opens com.example.library2 to javafx.fxml;
    exports com.example.library2;
    opens com.example.library2.bookinf to javafx.fxml;
    exports com.example.library2.bookinf;
    opens com.example.library2.home to javafx.fxml;
    exports com.example.library2.home;
    opens com.example.library2.menu to javafx.fxml;
    exports com.example.library2.menu;
    opens com.example.library2.dataSend to javafx.base;
}