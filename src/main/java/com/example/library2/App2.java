package com.example.library2;

import com.example.library2.home.Home;
import com.example.library2.menu.OrderManage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App2 extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Scene scene = new Scene(Home.getInstance());
//        Scene scene = new Scene(OrderManage.getTest());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
