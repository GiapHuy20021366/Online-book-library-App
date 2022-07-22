package com.example.library2.utilities;

import com.example.library2.direction.GoTo;
import com.example.library2.menu.AccountUtility;
import com.example.library2.menu.AdminManagement;
import com.example.library2.menu.OrderManage;
import com.example.library2.transitionTool.Direction;
import com.example.library2.transitionTool.TransitionService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class SubMenu extends AnchorPane {
    private final ImageView size = new ImageView();
    private static final SubMenu menu = new SubMenu();
    private Transition fade;
    private Transition move;
    private Timeline closeLeftMenuAfter;
    private Button goBack;
    private final ImageView homeIcon = new ImageView();
    private final AnchorPane login = new AnchorPane();
    private final AnchorPane accountMenu = new AnchorPane();
    private final AnchorPane managementMenu = new AnchorPane();
    private final AnchorPane severManagement = new AnchorPane();

    private SubMenu() {
        size.setFitWidth(280);
        size.setFitHeight(850);
        getChildren().add(size);

        login.setLayoutX(550);
        login.setLayoutY(100);

        login.getChildren().add(LoginMenu.getInstance());

        accountMenu.getChildren().add(AccountUtility.getInstance());

        getChildren().add(accountMenu);
        accountMenu.setLayoutY(0);

        managementMenu.setLayoutX(290);
        severManagement.setLayoutX(290);
    }


    public void hideAt(AnchorPane pane) {
        if (!pane.getChildren().contains(this)) {
            pane.getChildren().add(this);
        }
//        closeAfter(pane, 2500L);
    }

    public void showManagement() {
        hideSeverManagement();
        if (!managementMenu.getChildren().contains(OrderManage.getInstance())) {
            managementMenu.getChildren().add(OrderManage.getInstance());
        }

        if (!getChildren().contains(managementMenu)) {
            getChildren().add(managementMenu);
        }

    }

    public void hideManagement() {

        getChildren().remove(managementMenu);
    }

    public void showSeverManagement() {
        hideManagement();
        if (!severManagement.getChildren().contains(AdminManagement.getInstance())) {
            severManagement.getChildren().add(AdminManagement.getInstance());
        }

        if (!getChildren().contains(severManagement)) {
            getChildren().add(severManagement);
        }
    }

    public void hideSeverManagement() {

        getChildren().remove(severManagement);
    }

    private void closeAfter(AnchorPane pane, long time) {
        if (!pane.getChildren().contains(this)) {
            pane.getChildren().add(this);
        }

        if (closeLeftMenuAfter != null) {
            closeLeftMenuAfter.stop();
        }
        closeLeftMenuAfter = new Timeline(new KeyFrame(Duration.millis(time), ev -> {
            stopTransition();
            closeLoginMenu();
            double distance = this.getBoundsInParent().getMaxX();
            long timeL = (long) (400 * (Math.abs(distance) / size.getFitWidth()));
            fade = TransitionService.fade(this, 0, 1, 1,
                    (long) (400 * (distance / size.getFitWidth())));
            move = TransitionService.movePlace(this, distance, 1, Direction.LEFT, timeL);
            fade.play();
            move.play();
        }));
        closeLeftMenuAfter.play();
    }

    public void showAt(AnchorPane pane) {
        if (!pane.getChildren().contains(this)) {
            pane.getChildren().add(this);
        }
//        if (closeLeftMenuAfter != null) {
//            closeLeftMenuAfter.stop();
//        }
//        stopTransition();
//        if (this.getBoundsInParent().getMaxX() >= size.getFitWidth()) return;
//        double distance = Math.min(280, -this.getBoundsInParent().getMinX());
//        long time = (long) (400 * (Math.abs(distance) / size.getFitWidth()));
//        fade = TransitionService.fade(this, 0, 1, 1,
//                (long) (400 * (distance / size.getFitWidth())));
//        move = TransitionService.movePlace(this, distance, 1, Direction.RIGHT, time);
//        fade.play();
//        move.play();

    }

    private void stopTransition() {
        if (fade != null) {
            fade.stop();
        }
        if (move != null) {
            move.stop();
        }
    }

    public void openLoginMenu() {
        if (!getChildren().contains(login)) {
            getChildren().add(login);
        }
    }

    public void closeLoginMenu() {
        getChildren().remove(login);
    }

    public static SubMenu getInstance() {
        return menu;
    }
}
