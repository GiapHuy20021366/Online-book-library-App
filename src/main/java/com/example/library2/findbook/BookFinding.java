package com.example.library2.findbook;

import com.example.library2.entity.Book;
import com.example.library2.format.BookCell;
import com.example.library2.format.ViewBookCell;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookFinding {
    private final Scene scene;
    private final Stage stage;

    public BookFinding(Stage stage) {
        this.stage = stage;
        Group root = new Group();
        scene = new Scene(root, 1800, 850);
        BookCell bookCell = new BookCell();
        bookCell.next(1);
        ViewBookCell view = new ViewBookCell(bookCell);
        view.setSize(600);
        view.setLayoutY(100);
        root.getChildren().add(view);
        stage.setFullScreen(true);
        stage.setMaximized(true);

    }

    public void show() {
        stage.setScene(scene);
    }
}
