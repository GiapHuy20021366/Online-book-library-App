package com.example.library2.menu;

import com.example.library2.bookinf.BookIntroduce;
import com.example.library2.direction.GoTo;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.SubMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AccountUtility implements Initializable {
    @FXML
    AnchorPane overall;

    @FXML
    Button myBook;

    @FXML
    Button goBack;

    @FXML
    Button borrowBook;

    @FXML
    Button favorite;

    @FXML
    Button newest;

    @FXML
    Button forYou;

    @FXML
    Button rank;

    @FXML
    MenuButton account;

    @FXML
    MenuButton manageTool;

    @FXML
    MenuItem statistic;

    @FXML
    MenuItem check;

    @FXML
    MenuItem update;

    @FXML
    MenuItem log;

    public static HashMap<String, MenuItem> menuItems = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.setText("Đăng nhập");
        menuItems.put("Log", log);
        setVisibleManage(false);
        statistic.setVisible(false);

        Image openIcon = new Image(new File(".\\Icon\\order.png").toURI().toString());
        ImageView openView = new ImageView(openIcon);
        openView.setFitWidth(15);
        openView.setFitHeight(15);
        check.setGraphic(openView);

        Image severI = new Image(new File(".\\Icon\\sever.png").toURI().toString());
        ImageView severIcon = new ImageView(severI);
        severIcon.setFitWidth(15);
        severIcon.setFitHeight(15);
        update.setGraphic(severIcon);

    }

    public void setVisibleManage(boolean b) {
        manageTool.setVisible(b);
        check.setVisible(b);
    }

    public void setVisibleSeverManage(boolean b) {
        update.setVisible(b);
    }

    private static final AnchorPane acc = getAcc();
    public static AccountUtility controller;

    private static AnchorPane getAcc() {
        AnchorPane pane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\menu\\AccountUtility.fxml").toURI().toURL());
            pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }


    public static AnchorPane getInstance() {
        return acc;
    }


    public static AccountUtility getController() {
        return controller;
    }


    public void goToBack(MouseEvent event) {
        if (GoTo.getInstance().hasPrev()) {
            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            Scene scene = GoTo.getInstance().getPrev();
            stage.setScene(scene);
        }
    }

    public void goToMyBook(MouseEvent event) {
        if (event.getButton().equals(MouseButton.SECONDARY)) {
            return;
        }

    }

    public void goToFavorite(MouseEvent event) {
        goHome(event);
        TopBar.getController().onClick(TopBar.getController().favorite);
        TopBar.getController().parseAction(TopBar.getController().getFavorite());
    }

    public void goToNewest(MouseEvent event) {
        goHome(event);
        TopBar.getController().onClick(TopBar.getController().newS);
        TopBar.getController().parseAction(TopBar.getController().getNewS());
    }

    public void goToSpendToYou(MouseEvent event) {
        goHome(event);
        TopBar.getController().onClick(TopBar.getController().home);
        TopBar.getController().parseAction(TopBar.getController().getHome());
    }

    public void goToRank(MouseEvent event) {
        goHome(event);
        TopBar.getController().onClick(TopBar.getController().rank);
        TopBar.getController().parseAction(TopBar.getController().getRank());
    }

    public void goToBorrow(MouseEvent event) {
        if (event.getButton().equals(MouseButton.SECONDARY)) {
            return;
        }
        goHome(event);
        TopBar.getController().onClick(TopBar.getController().borrowed);
        TopBar.getController().parseAction(TopBar.getController().getBorrowed());
    }

    public void goToLog(ActionEvent event) {
        if (!menuItems.containsKey("Log")) {
            menuItems.put("Log", log);
        }
        SubMenu.getInstance().openLoginMenu();
    }

    public void goToStatistic(ActionEvent event) {

    }

    public void goToCheck(ActionEvent event) {
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return;
        }
        SubMenu.getInstance().showManagement();
//        Scene curScene = ((MenuItem) event.getTarget()).getParentPopup().getScene();
//        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
//        GoTo.getInstance().put(curScene);
//        stage.setScene(OrderManage.getInstance());
    }

    public void goToUpdate(ActionEvent event) {
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return;
        }
        SubMenu.getInstance().showSeverManagement();
    }

    public void goHome(MouseEvent event) {
        Scene curScene = ((Node) event.getSource()).getScene();
        Scene FScene = GoTo.getInstance().getFirst();
        if (FScene != null && curScene != FScene) {
            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            stage.setScene(FScene);
        }
    }

    public void goBackEnter(MouseEvent event) {
        goBack.getStyleClass().remove("buttonLeft");
        goBack.getStyleClass().add("buttonLeftEnter");
    }

    public void goBackExit(MouseEvent event) {
        goBack.getStyleClass().add("buttonLeft");
        goBack.getStyleClass().remove("buttonLeftEnter");
    }

    public void onEnterButton(MouseEvent event) {
        ((ButtonBase) event.getSource()).getStyleClass().remove("myBookButton");
        ((ButtonBase) event.getSource()).getStyleClass().add("buttonOnEnter");
    }

    public void onExitButton(MouseEvent event) {
        ((ButtonBase) event.getSource()).getStyleClass().add("myBookButton");
        ((ButtonBase) event.getSource()).getStyleClass().remove("buttonOnEnter");
    }

    public void onEnterButtonBorrow(MouseEvent event) {
        ((ButtonBase) event.getSource()).getStyleClass().remove("borowBookButton");
        ((ButtonBase) event.getSource()).getStyleClass().add("borowBookButtonOnEnter");
    }

    public void onExitButtonBorrow(MouseEvent event) {
        ((ButtonBase) event.getSource()).getStyleClass().add("borowBookButton");
        ((ButtonBase) event.getSource()).getStyleClass().remove("borowBookButtonOnEnter");
    }



}
