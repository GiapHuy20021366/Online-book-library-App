package com.example.library2.bookinf;

import com.example.library2.client.Client;
import com.example.library2.entity.Author;
import com.example.library2.entity.Book;
import com.example.library2.format.BookFrame;
import com.example.library2.format.DateParse;
import com.example.library2.format.Description;
import com.example.library2.utilities.SubMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;



public class BookIntroduce implements Initializable {
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

    @FXML
    AnchorPane leftMenu;

    @FXML
    Text date;

    Book book;

    Scene prevScene;

    public void setPrevScene(Scene scene) {
        this.prevScene = scene;
    }

    public BookIntroduce() throws SQLException {
    }

    public void setBook(Book book) {
        this.book = book;
        bookTitle.setText(book.getName().replace("\n", " "));
        rate.setText("Rate: " + book.getLike());
        bookDescription.setContent(book.getDescription());
        bookImg.setImage(book.getImage());
        authorDescription.setContent("Giới thiệu tác giả:<p>" + book.getFullDesAuthors());
        switch (Client.getInstance().hadLikeBook(book.getId())) {
            case HAD_LIKE -> {
                like.setText("Đã thích");
            }
            case SEVER_RUN_OUT -> {
                System.out.println("Sever run out");
            }
        }
        switch (Client.getInstance().checkOrder(book.getId())) {
            case HAD_BORROWED -> {
                borrow.setText("Đã mượn");
            }
            case BORROWED_BY_OTHER -> {
                borrow.setText("Đã được mượn");
            }
            case HAD_ORDERED -> {
                borrow.setText("Đã đặt");
            }
            case ORDERED_BY_ANOTHER -> {
                borrow.setText("Đã được đặt");
            }
            case SEVER_RUN_OUT -> {
                System.out.println("Sever run out");
            }
        }
        showAllAuthors(book);



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftMenu.getChildren().add(SubMenu.getInstance());

        paneForBookDisplay.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        paneForBookDisplay.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

//        bookImg.setImage(book.getImage());

//        bookTitle.setText(book.getName().replace("\n", " "));
        bookTitle.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 18));
        bookTitle.setWrappingWidth(530);
        bookTitle.setFill(Color.RED);

//        rate.setText(book.getRate());

//        bookDescription.setContent(book.getDescription());
        bookDescription.setLayoutY(280);
        BookAndAuthor.getChildren().add(bookDescription);

//        authorDescription.setContent("Giới thiệu tác giả:<p>" + book.getFullDesAuthors());
        authorDescription.setLayoutY(bookDescription.getLayoutY() + 400);
        BookAndAuthor.getChildren().add(authorDescription);

        like.setText("Thích");
        borrow.setText("Đặt trước");

        setBook(BookFrame.curBook);


        date.setText(DateParse.parseDate(book.getDate()));

        paneForBookDisplay.setStyle("-fx-focus-color: transparent;\n" +
                "-fx-faint-focus-color: transparent;");

        like.setStyle("-fx-focus-color: transparent;\n" +
                "-fx-faint-focus-color: transparent;");
        borrow.setStyle("-fx-focus-color: transparent;\n" +
                "-fx-faint-focus-color: transparent;");

        setButton(like);
        setButton(borrow);

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
        FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\bookinf\\BookIntroduce.fxml").toURI().toURL());
        return new Scene(fxmlLoader.load());
    }

    public void showLeftMenu(MouseEvent event) {
        SubMenu.getInstance().showAt(leftMenu);
    }

    public void hideLeftMenu(MouseEvent event) {
        SubMenu.getInstance().hideAt(leftMenu);
    }

    public void clickLike(MouseEvent event) {
        if (like.getText().equals("Đã thích")) {
            switch (Client.getInstance().disLikeBook(book.getId())) {
                case SUCCESS -> {
                    like.setText("Thích");
                }
                case ACCOUNT_NOT_EXIST -> {
                    System.out.println("Error: Your account doesn't exist");
                }
            }
            return;
        }
        switch (Client.getInstance().likeBook(book.getId())) {
            case SUCCESS -> {
                like.setText("Đã thích");
            }
            case ACCOUNT_NOT_EXIST -> {
                System.out.println("Error: Your account doesn't exist");
            }
            case FAIL_PASS -> {
                System.out.println("Error: Your pass failed");
            }
        }
    }

    public void clickOrder(MouseEvent event) {
        if (borrow.getText().equals("Đã được đặt")
                || borrow.getText().equals("Đã mượn")
                || borrow.getText().equals("Đã được mượn")) {
            return;
        }
        if (borrow.getText().equals("Đã đặt")) {
            switch (Client.getInstance().removeOrder(book.getId())) {
                case SUCCESS -> {
                    borrow.setText("Đặt trước");
                }
                case ACCOUNT_NOT_EXIST -> {
                    System.out.println("Error: Your account doesn't exist");
                }
            }
            return;
        }
        switch (Client.getInstance().order(book.getId())) {
            case SUCCESS -> {
                borrow.setText("Đã đặt");
            }
            case ACCOUNT_NOT_EXIST -> {
                System.out.println("Error: Your account doesn't exist");
            }
            case FAIL_PASS -> {
                System.out.println("Error: Your pass failed");
            }
        }
    }

    private void setButton(Button button) {
        button.setStyle("-fx-background-color: \n" +
                "        linear-gradient(#ffd65b, #e68400),\n" +
                "        linear-gradient(#ffef84, #f2ba44),\n" +
                "        linear-gradient(#ffea6a, #efaa22),\n" +
                "        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
                "        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,2,3,0;\n" +
                "    -fx-text-fill: #654b00;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 14px;\n" +
                "    -fx-padding: 10 20 10 20;");
    }

    private void buttonOnEnter(Button button) {
        button.setTextFill(Color.RED);
    }

    private void buttonOnExit(Button button) {
        button.setTextFill(Color.BLACK);
    }
}
