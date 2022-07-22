package com.example.library2.format;

import com.example.library2.bookinf.BookIntroduce;
import com.example.library2.direction.GoTo;
import com.example.library2.entity.Book;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.SubMenu;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookFrame extends AnchorPane {
    private final ImageView mainSize = new ImageView();
    private final DoubleProperty ratePadding = new SimpleDoubleProperty(0.05);
    private final ImageView imgBook = new ImageView();
    private final Text title = new Text();
    private final List<Text> aus = new ArrayList<>();
    private final Text rate = new Text("0.5");

    private final AnchorPane picture = new AnchorPane();
    private final AnchorPane inf = new AnchorPane();
    private final AnchorPane authors = new AnchorPane();
    private Book book;

    public BookFrame(Book book) {

        setFrame();
        setBook(book);

    }

    private void setFrame() {
        picture.setLayoutX(5);

        imgBook.setFitWidth(160);
        imgBook.setFitHeight(230);
        imgBook.setOnMousePressed(this::gotoBookIntro);
        imgBook.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        imgBook.setOnMouseEntered(event -> imgBook.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);"));
        imgBook.setOnMouseExited(event -> imgBook.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"));
        picture.getChildren().add(imgBook);
        this.getChildren().add(picture);

        inf.setLayoutX(5);
        inf.setLayoutY(240);

        title.setX(5);
        title.setY(10);
        title.setFont(Font.font("DejaVu Sans Mono", 14));
        title.setFill(Color.RED);
        title.setOnMousePressed(this::gotoBookIntro);
        title.setOnMouseEntered(event -> title.setStyle("-fx-underline: true;"));
        title.setOnMouseExited(event -> title.setStyle("-fx-underline: false;"));
        title.setWrappingWidth(160);
        title.setTextAlignment(TextAlignment.CENTER);
        inf.getChildren().add(title);

        rate.setX(40);
        rate.setY(60);
        rate.setFont(Font.font("DejaVu Sans Mono", 16));
        rate.setFill(Color.DARKOLIVEGREEN);
        inf.getChildren().add(rate);


        this.getChildren().add(inf);


        mainSize.setFitWidth(170);
        mainSize.setFitHeight(310);
        getChildren().add(mainSize);
        mainSize.toBack();
    }

    public void setBook(Book book) {
        this.book = book;

        imgBook.setImage(book.getImage());
        String titleK = ETittle(book.getName());
        if (titleK.length() > 23) {
            title.setText(titleK.substring(0, 20).concat("..."));
        } else {
            title.setText(titleK);
        }

        for (Text text : aus) {
            getChildren().remove(text);
        }

        for (String au : book.getAuthors()) {
            String shortName;
            if (au.length() > 15) {
                shortName = au.substring(0, 12).concat("...");
            } else {
                shortName = au;
            }
            Text t = new Text(shortName);
            t.setFont(Font.font("DejaVu Sans Mono", 10));
            t.setFill(Color.DARKRED);

            t.setOnMousePressed(event -> System.out.println(au));
            t.setOnMouseEntered(event -> t.setStyle("-fx-underline: true;"));
            t.setOnMouseExited(event -> t.setStyle("-fx-underline: false;"));

            if (aus.isEmpty()) {
                t.setX(15);
                t.setY(30);
            } else if (aus.get(aus.size() - 1).getBoundsInLocal().getMaxX() + t.getBoundsInLocal().getWidth() + 12 < 170) {
                t.setX(aus.get(aus.size() - 1).getBoundsInLocal().getMaxX() + 12);
                t.setY(aus.get(aus.size() - 1).getY());
            } else {
                t.setX(20);
                t.setY(aus.get(aus.size() - 1).getY() + 15);
            }
            inf.getChildren().add(t);
            t.toFront();
            aus.add(t);

            if (aus.size() > 1) {
                break;
            }
        }

        rate.setText("Rate: " + book.getLike());

    }

    public static Book curBook;

    private void gotoBookIntro(MouseEvent event) {
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return;
        }
        curBook = book;
        Scene curScene = ((Node) event.getSource()).getScene();
        GoTo.getInstance().put(curScene);
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        try {
            Scene nextScene = BookIntroduce.loadScene();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String ETittle(String s) {
        while (s.contains("  ")) {
            s = s.replace("  ", " ");
        }
        return s;
    }

    public void setMainSize(double w, double h) {
        mainSize.setFitWidth(w);
        mainSize.setFitHeight(h);
    }

    public Book getBook() {
        return book;
    }
}
