package com.example.library2.menu;

import com.example.library2.home.Home;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookTableFind implements Initializable {
    public static class BookName {
        String name;

        public BookName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @FXML
    TableView tableView;

    @FXML
    TableColumn column;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setVisible(false);

        column = new TableColumn<BookName, String>("Book Name");
        column.setPrefWidth(800);
        column.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(column);

        tableView.setRowFactory(tableView -> {
            final TableRow<BookName> row = new TableRow<>();
            row.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    final BookName s = row.getItem();
                    if (s != null) {
                        row.getStyleClass().add("rowOnEnter");
                        row.getStyleClass().remove("rowOnExit");
                    }
                }
            });
            row.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    row.getStyleClass().remove("rowOnEnter");
                    row.getStyleClass().add("rowOnExit");
                }
            });
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    final BookName s = row.getItem();
                    if (s != null) {
                        FindBook.find(s.name);
                        hide();
                    }
                }
            });
            return row;
        });

        tableView.setFixedCellSize(35);
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));
        tableView.minHeightProperty().bind(tableView.prefHeightProperty());
        tableView.maxHeightProperty().bind(tableView.prefHeightProperty());

        view = tableView;
    }

    private static TableView view;

    public static void clear() {
        view.getItems().clear();
    }

    public static void show() {
        view.setVisible(true);
        view.getParent().getParent().toFront();
    }

    public static void hide() {
        view.setVisible(false);
        view.getParent().getParent().toBack();
    }
    public static void addBook(String name) {
        view.getItems().add(new BookName(name));
    }

    private static final AnchorPane bookFind = getBookFind();
    private static BookTableFind controller;

    private static AnchorPane getBookFind() {
        AnchorPane pane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\menu\\BookTableFind.fxml").toURI().toURL());
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

}
