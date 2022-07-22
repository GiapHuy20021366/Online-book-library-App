package com.example.library2.menu;

import com.example.library2.home.Home;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.Borrowed;
import com.example.library2.utilities.HadLike;
import com.example.library2.utilities.SubMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TopBar implements Initializable {
    @FXML
    Button home;

    @FXML
    Button rank;

    @FXML
    Button newS;

    @FXML
    Button favorite;

    @FXML
    Button borrowed;

    public Button getBorrowed() {
        return borrowed;
    }

    private ButtonBase onChoose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onClick(home);
    }


    private static final AnchorPane topBar = getTopBar();
    private static TopBar controller;

    private static AnchorPane getTopBar() {
        AnchorPane pane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\menu\\TopBar.fxml").toURI().toURL());
            pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    public static AnchorPane getInstance() {
        return topBar;
    }

    public Button getFavorite() {
        return favorite;
    }

    public static TopBar getController() {
        return controller;
    }

    public void onEnter(MouseEvent event) {
        onEnter((ButtonBase) event.getSource());
    }

    private void onEnter(ButtonBase base) {
        base.getStyleClass().remove("buttonNormal");
        base.getStyleClass().add("buttonEnter");
    }

    public void onExit(MouseEvent event) {
        onExit((ButtonBase) event.getSource());
    }

    private void onExit(ButtonBase base) {
        base.getStyleClass().add("buttonNormal");
        base.getStyleClass().remove("buttonEnter");
    }

    public void onClick(MouseEvent event) {
        onClick((ButtonBase) event.getSource());
        parseAction(((Button) event.getSource()));
    }

    public void parseAction(Button button) {
        if (button.equals(rank)) {
            gotoRank();
        } else if (button.equals(home)) {
            gotoHome();
        } else if (button.equals(newS)) {
            gotoNewS();
        } else if (button.equals(favorite)) {
            gotoHadLike();
        } else if (button.equals(borrowed)) {
            gotoBorrowed();
        }
    }

    private void gotoBorrowed() {
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return;
        }
        Borrowed.getInstance().load();
        Home.showHadBorrowed();
    }

    private void gotoRank() {
        Home.showRank();
    }

    private void gotoHome() {
        Home.showHome();
    }

    private void gotoNewS() {
        Home.showNew();
    }

    private void gotoHadLike() {
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return;
        }
        HadLike.getInstance().load();
        Home.showHadLike();
    }

    public void onClick(ButtonBase base) {
        if ((base.equals(favorite) || base.equals(borrowed)) && !Account.getInstance().isLogin()) {
            return;
        }

        base.getStyleClass().remove("buttonNormal");
        base.getStyleClass().add("buttonChoose");
        if (onChoose != null) {
            onChoose.getStyleClass().add("buttonNormal");
            onChoose.getStyleClass().remove("buttonChoose");
        }
        onChoose = base;
    }

    public Button getHome() {
        return home;
    }

    public Button getRank() {
        return rank;
    }

    public Button getNewS() {
        return newS;
    }
}
