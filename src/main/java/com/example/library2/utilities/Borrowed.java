package com.example.library2.utilities;

import com.example.library2.client.Client;
import com.example.library2.dataSend.BookData;
import com.example.library2.entity.Book;
import com.example.library2.format.BookCell;
import com.example.library2.format.FixedBookCell;
import com.example.library2.format.ViewBookCell;
import com.example.library2.transitionTool.MyTask;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Borrowed extends Pane {
    private static final Borrowed r = new Borrowed();
    private final FixedBookCell bookBorrowed = new FixedBookCell();
    private final FixedBookCell bookOrdered = new FixedBookCell();
    private final Text textBorrow = new Text("Đã mượn");
    private final Text textOrder = new Text("Đã đặt");

    private Borrowed() {
        ScrollPane pane = new ScrollPane();
        AnchorPane anchorPane = new AnchorPane();
        pane.setContent(anchorPane);
        getChildren().add(pane);
        pane.setPrefHeight(700);
        pane.setPrefWidth(1080);
        pane.setStyle("-fx-focus-color: transparent;\n" +
                "-fx-faint-focus-color: transparent;");


        pane.setPadding(Insets.EMPTY);
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        ViewBookCell viewBorrowed = new ViewBookCell(bookBorrowed);
        viewBorrowed.setSize( 900, 500);
        viewBorrowed.setLayoutY(100);


        ViewBookCell viewOrdered = new ViewBookCell(bookOrdered);
        viewOrdered.setSize( 900, 500);
        viewOrdered.setLayoutY(700);


        anchorPane.getChildren().addAll(textBorrow, textOrder);
        textOrder.setY(650);
        textOrder.setX(400);
        textOrder.setFont(Font.font("DejaVu Sans Mono", 50));
        textOrder.setFill(Color.RED);

        textBorrow.setY(80);
        textBorrow.setX(400);
        textBorrow.setFont(Font.font("DejaVu Sans Mono", 50));
        textBorrow.setFill(Color.RED);

        anchorPane.getChildren().add(viewBorrowed);
        anchorPane.getChildren().add(viewOrdered);

    }

    public void load() {
        loadBorrowed(bookBorrowed);
        loadOrdered(bookOrdered);
    }


    private void loadBorrowed(BookCell cells) {

        MyTask<List<Book>> myTask = new MyTask<List<Book>>() {
            @Override
            public void whatNext() {
                cells.addALl(this.getValue());
            }

            @Override
            protected List<Book> call() throws Exception {
                List<BookData> list = Client.getInstance().getBorrowedBookData();
                List<Book> books = new ArrayList<>();
                for (BookData data : list) {
                    books.add(new Book(data));
                }
                return books;
            }
        };
        myTask.execute();
    }


    private void loadOrdered(BookCell cells) {

        MyTask<List<Book>> myTask = new MyTask<List<Book>>() {
            @Override
            public void whatNext() {
                cells.addALl(this.getValue());
            }

            @Override
            protected List<Book> call() throws Exception {
                List<BookData> list = Client.getInstance().getOrderedBookData();
                List<Book> books = new ArrayList<>();
                for (BookData data : list) {
                    books.add(new Book(data));
                }
                return books;
            }
        };
        myTask.execute();
    }


    public static Borrowed getInstance() {
        return r;
    }
}
