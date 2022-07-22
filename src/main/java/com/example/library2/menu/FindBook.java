package com.example.library2.menu;

import com.example.library2.bookinf.BookIntroduce;
import com.example.library2.client.Client;
import com.example.library2.direction.GoTo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FindBook implements Initializable {
    @FXML
    Button find;

    @FXML
    TextField text;

    public static List<Object> list = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        find.setGraphic(new ImageView(new Image(new File(".\\Icon\\find.png").toURI().toString())));
        text.setPromptText("Tìm sách");
        list.add(text);

        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.trim().equals("")) {
                    BookTableFind.clear();
                    BookTableFind.hide();
                    System.out.println(newValue);
                    return;
                }
                if (!newValue.trim().equals(oldValue)) {
                    List<String> list = Client.getInstance().getBookAsFind(newValue.trim());
                    if (list == null) return;
                    if (!list.isEmpty()) {
                        BookTableFind.clear();
                        BookTableFind.show();
                        for (String s : list) {
                            BookTableFind.addBook(s);
                        }
                    } else {
                        BookTableFind.hide();
                    }

                }
            }
        });
    }

    private static String isFind = "";
    public static void find(String s) {
        if (s.trim().equals(isFind)) {
            return;
        }
        isFind = s.trim();
        ((TextField) list.get(0)).setText(s);
        overFind(((TextField) list.get(0)).getScene());
        BookTableFind.hide();
    }

    private static final AnchorPane bookFind = getBookFind();
    private static FindBook controller;

    private static AnchorPane getBookFind() {
        AnchorPane pane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\menu\\findbook.fxml").toURI().toURL());
            pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    public static AnchorPane getInstance() {
        return bookFind;
    }

    public static FindBook getController() {
        return controller;
    }

    public void find(MouseEvent event) {
        find(((TextField) list.get(0)).getText());
    }

    public static void overFind(Scene scene) {
        Scene curScene = scene;
        GoTo.getInstance().put(curScene);
        Stage stage = (Stage) (scene.getWindow());
        Scene nextScene = AllBookFind.getCopy();
        stage.setScene(nextScene);
    }



}
