package com.example.library2.format;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ViewBookCell extends Pane {
    private final ScrollPane scrollPane = new ScrollPane();


    public ViewBookCell(BookCell bookCell) {

        scrollPane.setContent(bookCell);
        scrollPane.setPadding(Insets.EMPTY);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        bookCell.next(10);
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1) {
                bookCell.next(10);
            }
        });

        this.getChildren().add(scrollPane);

        setStyle("-fx-focus-color: transparent;\n" +
                "-fx-faint-focus-color: transparent;");

    }

    public void setSize(double wid, double hei) {
        scrollPane.setPrefSize(wid, hei);
    }

    public void setSize(double hei) {
        scrollPane.setPrefHeight(hei);
    }

}
