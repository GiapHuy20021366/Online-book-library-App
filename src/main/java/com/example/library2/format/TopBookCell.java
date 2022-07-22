package com.example.library2.format;

import com.example.library2.client.Client;
import com.example.library2.dataSend.BookData;
import com.example.library2.entity.Book;
import com.example.library2.transitionTool.MyTask;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.*;

public class TopBookCell extends AnchorPane {
    private final List<BookFrame> bookFrames = new ArrayList<>();
    private final HashMap<Integer, AnchorPane> cells = new HashMap<>();
    private final Text title = new Text("Nổi bật");
    private final ImageView size = new ImageView();
    private final ScrollPane scrollPane = new ScrollPane();
    private final AnchorPane topBooks = new AnchorPane();

    public TopBookCell() {
        size.setFitWidth(230);
        size.setFitHeight(1000);

        title.setFont(Font.font("DejaVu Sans Mono", 50));
        title.setFill(Color.RED);
        title.setLayoutX(115 - title.getBoundsInLocal().getWidth() / 2);
        title.setLayoutY(10);
        getChildren().add(title);
        getChildren().add(size);

        getChildren().add(scrollPane);
        scrollPane.setContent(topBooks);
        scrollPane.setPrefWidth(230);
        scrollPane.setLayoutY(50);
        scrollPane.setPrefHeight(700);

        scrollPane.setPadding(Insets.EMPTY);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        load();
    }

    public void addBook(Book book) {
        BookFrame bookFrame = new BookFrame(book);
        createCell(bookFrames.size());
        cells.get(bookFrames.size()).getChildren().add(bookFrame);
        bookFrames.add(bookFrame);
    }

    private void addBookFrame(BookFrame bookFrame) {
        createCell(bookFrames.size());
        cells.get(bookFrames.size()).getChildren().add(bookFrame);
        bookFrames.add(bookFrame);
    }

    private void createCell(int pos) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setLayoutX(30);
        anchorPane.setLayoutY((pos + 1) * 10 + pos * 310);
        cells.put(pos, anchorPane);
        topBooks.getChildren().add(anchorPane);
    }

    public void load() {
        MyTask<List<BookFrame>> task = new MyTask<>() {
            @Override
            public void whatNext() {
                for (BookFrame bookFrame : this.getValue()) {
                    addBookFrame(bookFrame);
                }
            }

            @Override
            protected List<BookFrame> call() throws Exception {
                List<BookFrame> bookFrames = new ArrayList<>();
                List<BookData> list = Client.getInstance().getListBookData(100, 110);
                System.out.println(list.size());

                for (BookData data : list) {
                    bookFrames.add(new BookFrame(new Book(data)));
                }
                return bookFrames;
            }
        };
        task.execute();

    }
}
