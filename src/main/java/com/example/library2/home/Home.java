package com.example.library2.home;

import com.example.library2.format.BookCell;
import com.example.library2.format.TopBookCell;
import com.example.library2.format.ViewBookCell;
import com.example.library2.menu.BookTableFind;
import com.example.library2.menu.FindBook;
import com.example.library2.menu.TopBar;
import com.example.library2.utilities.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable, ShowOnBack {

    @FXML
    AnchorPane overall;

    @FXML
    AnchorPane midBooks;

    @FXML
    AnchorPane leftMenu;

    @FXML
    AnchorPane rightMenu;

    @FXML
    AnchorPane tuningCellBar;

    @FXML
    AnchorPane bookFind;

    @FXML
    AnchorPane tableFind;



    public static final TopBookCell topBookCell = new TopBookCell();

    public static AnchorPane midBook;
    private static ViewBookCell viewHome;

    private boolean hasFull = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        leftMenu.setLayoutX(0);
        leftMenu.toFront();
        tuningCellBar.getChildren().add(TopBar.getInstance());
        showDuplicate();

        BookCell bookCell = new BookCell();
        bookCell.next(1);
        ViewBookCell viewHome = new ViewBookCell(bookCell);
        viewHome.setSize(700);
        midBooks.getChildren().add(viewHome);

        midBook = midBooks;
        Home.viewHome = viewHome;

        midBooks.getChildren().add(Rank.getInstance());
        Rank.getInstance().setVisible(false);

        midBook.getChildren().add(NewBook.getInstance());
        NewBook.getInstance().setVisible(false);

        midBook.getChildren().add(HadLike.getInstance());
        HadLike.getInstance().setVisible(false);

        midBook.getChildren().add(Borrowed.getInstance());
        Borrowed.getInstance().setVisible(false);

    }

    public static void showHome() {
        Rank.getInstance().setVisible(false);
        NewBook.getInstance().setVisible(false);
        HadLike.getInstance().setVisible(false);
        Borrowed.getInstance().setVisible(false);
        viewHome.setVisible(true);
    }

    public static void showRank() {
        Rank.getInstance().setVisible(true);
        viewHome.setVisible(false);
        NewBook.getInstance().setVisible(false);
        HadLike.getInstance().setVisible(false);
        Borrowed.getInstance().setVisible(false);
    }

    public static void showNew() {
        Rank.getInstance().setVisible(false);
        viewHome.setVisible(false);
        NewBook.getInstance().setVisible(true);
        HadLike.getInstance().setVisible(false);
        Borrowed.getInstance().setVisible(false);
    }

    public static void showHadLike() {
        Rank.getInstance().setVisible(false);
        viewHome.setVisible(false);
        NewBook.getInstance().setVisible(false);
        HadLike.getInstance().setVisible(true);
        Borrowed.getInstance().setVisible(false);
    }

    public static void showHadBorrowed() {
        Rank.getInstance().setVisible(false);
        viewHome.setVisible(false);
        NewBook.getInstance().setVisible(false);
        HadLike.getInstance().setVisible(false);
        Borrowed.getInstance().setVisible(true);
    }

    @Override
    public void showDuplicate() {
        overall.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!hasFull) {
                    if (!bookFind.getChildren().contains(FindBook.getInstance())) {
                        bookFind.getChildren().add(FindBook.getInstance());
                    }
                    if (!tableFind.getChildren().contains(BookTableFind.getInstance())) {
                        tableFind.getChildren().add(BookTableFind.getInstance());
                    }
                    if (!rightMenu.getChildren().contains(topBookCell)) {
                        rightMenu.getChildren().add(topBookCell);
                        topBookCell.toBack();
                    }
                    showLeftMenu(null);
                }
                hasFull = true;
            }
        });
        overall.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hasFull = false;
            }
        });
    }



    public void showLeftMenu(MouseEvent event) {
        SubMenu.getInstance().showAt(leftMenu);
    }

    public void hideLeftMenu(MouseEvent event) {
        SubMenu.getInstance().hideAt(leftMenu);
    }


    private static final AnchorPane home = getHome();
    public static Home controller;

    private static AnchorPane getHome() {
        AnchorPane pane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\home\\home.fxml").toURI().toURL());


            pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }



    public static AnchorPane getInstance(){
        return home;
    }


    public static Home getController() {
        return controller;
    }


}
