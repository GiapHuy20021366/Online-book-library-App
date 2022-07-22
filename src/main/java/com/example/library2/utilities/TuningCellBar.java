package com.example.library2.utilities;

import com.example.library2.format.BookCell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class TuningCellBar extends AnchorPane {
    private final ImageView size = new ImageView();
    private final BookCell cells;
    private final MenuBar menuSort = new MenuBar();
    private final Menu menuS = new Menu("Sắp xếp                    ");;


    public  TuningCellBar(BookCell bookCell) {
        this.cells = bookCell;

        size.setFitWidth(930);
        size.setFitHeight(50);

        this.getChildren().add(menuSort);

        menuS.setId("MenuSort");
        menuSort.getMenus().add(menuS);
        menuSort.setPrefWidth(150);




        menuSort.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                menuS.show();
            }
        });



        ToggleGroup sortGroup = new ToggleGroup();

        RadioMenuItem sortByNameASC = new RadioMenuItem("Tăng dần theo tên");
        sortByNameASC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuS.setText(sortByNameASC.getText());
                bookCell.sortByNameASC();
            }
        });
        menuS.getItems().add(sortByNameASC);
        sortByNameASC.setToggleGroup(sortGroup);

        RadioMenuItem sortByNameDESC = new RadioMenuItem("Giảm dần theo tên");
        menuS.getItems().add(sortByNameDESC);
        sortByNameDESC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuS.setText(sortByNameDESC.getText());
                bookCell.sortByNameDESC();
            }
        });
        sortByNameDESC.setToggleGroup(sortGroup);

        RadioMenuItem sortByDateDESC = new RadioMenuItem("Mới nhất          ");
        menuS.getItems().add(sortByDateDESC);
        sortByDateDESC.setOnAction(event -> {
            menuS.setText(sortByDateDESC.getText());
            bookCell.sortByDateDESC();
        });
        sortByDateDESC.setToggleGroup(sortGroup);

        RadioMenuItem sortByDateASC = new RadioMenuItem("Cũ nhất          ");
        menuS.getItems().add(sortByDateASC);
        sortByDateASC.setOnAction(event -> {
            menuS.setText(sortByDateASC.getText());
            bookCell.sortByDateASC();
        });
        sortByDateASC.setToggleGroup(sortGroup);


    }


}
