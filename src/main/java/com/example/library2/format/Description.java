package com.example.library2.format;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;


public class Description extends Pane {
    private final WebView des = new WebView();
    private final ScrollPane scrollPane = new ScrollPane();

    public Description() {
        scrollPane.setContent(des);
        this.getChildren().add(scrollPane);
        des.setPrefWidth(710);

        des.addEventFilter(KeyEvent.ANY, (event) -> {
            if (event.getCode() == KeyCode.DOWN
                    || event.getCode() == KeyCode.UP
                    || event.getCode() == KeyCode.SPACE
                    || event.getCode() == KeyCode.LEFT
                    || event.getCode() == KeyCode.RIGHT) {
                event.consume();
                javafx.event.Event.fireEvent(this, event);
            }
        });

        scrollPane.setPrefHeight(400);
        scrollPane.setPadding(Insets.EMPTY);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    public void setContent(String s) {
        des.getEngine().loadContent(s, "text/html");
    }

}

