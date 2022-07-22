package com.example.library2.bookinf;

import com.example.library2.entity.Author;
import com.example.library2.entity.Book;
import com.example.library2.format.BookFrame;
import com.example.library2.format.Description;
import com.example.library2.sqlQuery.GetBookQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class BookInf implements Initializable {
    @FXML
    AnchorPane overall;

    @FXML
    Pane bookDisplay;

    @FXML
    ScrollPane paneForBookDisplay;

    @FXML
    AnchorPane bookHeading;

    @FXML
    ImageView bookImg;

    @FXML
    Text bookTitle;

    @FXML
    Text rate;

    @FXML
    Button like;

    @FXML
    Button borrow;

    private final Description bookDescription = new Description();
    private final Description authorDescription = new Description();

    @FXML
    final Text author = new Text();

    @FXML
    AnchorPane BookAndAuthor;

    @FXML
    Button goBack;

    Book book;

    Scene prevScene;

    public void setPrevScene(Scene scene) {
        this.prevScene = scene;
    }

    public BookInf() throws SQLException {
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBook(BookFrame.curBook);

        paneForBookDisplay.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        paneForBookDisplay.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        bookImg.setImage(book.getImage());

        bookTitle.setText(book.getName().replace("\n", " "));
        bookTitle.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 18));
        bookTitle.setWrappingWidth(530);
        bookTitle.setFill(Color.RED);

        rate.setText(book.getRate());

//        String content;
//        if (book.getSourceBySql()) {
//            content = book.getSourceHtml();
//        } else {
//            content = book.getDescription();
//        }
//        bookDescription.setContent(content);
        bookDescription.setContent(book.getDescription());
        bookDescription.setLayoutY(280);
        BookAndAuthor.getChildren().add(bookDescription);

        authorDescription.setContent("Giới thiệu tác giả:<p>" + book.getFullDesAuthors());
        authorDescription.setLayoutY(bookDescription.getLayoutY() + 400);
        BookAndAuthor.getChildren().add(authorDescription);

        like.setText("Thích");
        borrow.setText("Mượn");
        goBack.setText("Quay lại");

        showAllAuthors(book);
    }

    private final HashMap<Text, Author> authors = new HashMap<>();
    private void showAllAuthors(Book book) {
        HashMap<String, Author> bookAuthors = book.getAllAuthors();
        int i = 0;
        Text prev = null;
        for (String s : bookAuthors.keySet()) {
            Text t = new Text(s);
            t.setFont(Font.font("DejaVu Sans Mono", 14));
            t.setFill(Color.BLACK);
            if (prev == null) {
                t.setX(200);
                t.setY(180);
            } else {
                if (prev.getBoundsInLocal().getMaxX() + t.getBoundsInLocal().getWidth() + 15 < 560) {
                    t.setX(prev.getBoundsInLocal().getMaxX() + 15);
                    t.setY(180);
                } else {
                    t.setX(200);
                    t.setY(prev.getBoundsInLocal().getMaxY() + 15);
                }
            }
            prev = t;
            bookHeading.getChildren().add(t);
            authors.put(t, bookAuthors.get(s));
        }

    }

    public void goBack(MouseEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        if (prevScene != null) {
            stage.setScene(prevScene);
        }
    }

    public static Scene loadScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\bookinf\\bookInf.fxml").toURI().toURL());
        return new Scene(fxmlLoader.load());
    }
}
