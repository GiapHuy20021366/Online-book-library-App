package com.example.library2.menu;

import com.example.library2.client.Client;
import com.example.library2.dataSend.BookData;
import com.example.library2.entity.Book;
import com.example.library2.format.BookCell;
import com.example.library2.format.FixedBookCell;
import com.example.library2.format.TopBookCell;
import com.example.library2.format.ViewBookCell;
import com.example.library2.home.Home;
import com.example.library2.transitionTool.MyTask;
import com.example.library2.utilities.ShowOnBack;
import com.example.library2.utilities.SubMenu;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AllBookFind implements Initializable, ShowOnBack {
    @FXML
    AnchorPane overall;

    @FXML
    AnchorPane midBooks;

    @FXML
    AnchorPane leftMenu;

    @FXML
    AnchorPane rightMenu;

    @FXML
    AnchorPane bookFind;

    @FXML
    AnchorPane tableFind;

    @FXML
    Text text;

    public FixedBookCell bookCell;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookCell = new FixedBookCell();
        ViewBookCell view = new ViewBookCell(bookCell);
        view.setSize(700);
        midBooks.getChildren().add(view);
        leftMenu.setLayoutX(0);

        showDuplicate();

        text.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 20));
        text.setFill(Color.RED);
        text.setTextAlignment(TextAlignment.LEFT);
        text.setWrappingWidth(900);
        text.setText("Đang tìm kiếm kết quả cho: " + ((TextField) FindBook.list.get(0)).getText());
        load(bookCell, text);

        BookTableFind.hide();
    }

    public static Scene getCopy() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\menu\\AllBookFind.fxml").toURI().toURL());
            return new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean hasFull = false;

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
                    if (!rightMenu.getChildren().contains(Home.topBookCell)) {
                        rightMenu.getChildren().add(Home.topBookCell);
                        Home.topBookCell.toBack();
                    }
                    SubMenu.getInstance().showAt(leftMenu);
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

    private static void load(BookCell bookCell, Text text) {
        MyTask<List<Book>> myTask = new MyTask<List<Book>>() {
            @Override
            public void whatNext() {
                text.setText("Hiển thị kết quả cho: " + ((TextField) FindBook.list.get(0)).getText());
                for (Book book : this.getValue()) {
                    bookCell.addBook(book);
                }
            }

            @Override
            protected List<Book> call() throws Exception {
                List<BookData> list = Client.getInstance().getBookDataAsFind(((TextField) FindBook.list.get(0)).getText());
                List<Book> listBook = new ArrayList<>();
                for (BookData data : list) {
                    listBook.add(new Book(data));
                }
                return listBook;
            }
        };
        myTask.execute();

    }
}
