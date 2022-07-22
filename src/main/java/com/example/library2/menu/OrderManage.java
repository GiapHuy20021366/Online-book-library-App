package com.example.library2.menu;

import com.example.library2.client.Client;
import com.example.library2.dataSend.BorrowData;
import com.example.library2.dataSend.OrderData;
import com.example.library2.transitionTool.MyTask;
import com.example.library2.utilities.MessageType;
import com.example.library2.utilities.ShowOnBack;
import com.example.library2.utilities.SubMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderManage implements Initializable {

    @FXML
    AnchorPane overall;

    @FXML
    TabPane order;

    @FXML
    Tab tabOrder;

    @FXML
    Tab tabBorrow;

    @FXML
    AnchorPane leftMenu;

    @FXML
    AnchorPane contentMenu;

    @FXML
    TableView<OrderData> tableOrder;

    @FXML
    TableView<BorrowData> tableBorrow;

    boolean hasFull = false;

    private final ContextMenu menu = new ContextMenu();

//    @Override
//    public void showDuplicate() {
//        overall.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (!hasFull) {
//                    SubMenu.getInstance().showAt(leftMenu);
//                }
//                hasFull = true;
//            }
//        });
//        overall.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                hasFull = false;
//            }
//        });
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableOrder.setPrefWidth(1300);


        tableBorrow.setPrefWidth(1300);


//        showDuplicate();

        setUpContextMenu();

        setUpOrderTable();

        setUpBorrowTable();
    }

    private void setUpContextMenu() {

        MenuItem space = new MenuItem("       ");


        MenuItem reload = new MenuItem("Reload");
        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tabOrder.isSelected()) {
                    reloadTableOrder();
                }
                if (tabBorrow.isSelected()) {
                    reloadTableBorrow();
                }
            }
        });
        Image openIcon = new Image(new File(".\\Icon\\reload.png").toURI().toString());
        ImageView openView = new ImageView(openIcon);
        openView.setFitWidth(15);
        openView.setFitHeight(15);
        reload.setGraphic(openView);

        MenuItem markAsBorrow = new MenuItem("Mark as borrowed");
        markAsBorrow.visibleProperty().bind(tabOrder.selectedProperty());
        markAsBorrow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OrderData data = tableOrder.getSelectionModel().getSelectedItem();
                if (data != null) {
                    if (Client.getInstance().markBookAsBorrow(data.bookID, data.cusID).equals(MessageType.SUCCESS)) {
                        reloadTableOrder();
                        reloadTableBorrow();
                    }
                }
            }
        });
        Image ok = new Image(new File(".\\Icon\\ok.png").toURI().toString());
        ImageView okView = new ImageView(ok);
        okView.setFitWidth(15);
        okView.setFitHeight(15);
        markAsBorrow.setGraphic(okView);

        MenuItem markAsReturn  = new MenuItem("Mark as returned");
        markAsReturn.visibleProperty().bind(tabBorrow.selectedProperty());
        markAsReturn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BorrowData data = tableBorrow.getSelectionModel().getSelectedItem();
                if (data != null) {
                    if (Client.getInstance().markBookAsReturn(data.bookID, data.cusID).equals(MessageType.SUCCESS)) {
                        reloadTableOrder();
                        reloadTableBorrow();
                    }
                }
            }
        });
        ImageView okView2 = new ImageView(ok);
        okView2.setFitWidth(15);
        okView2.setFitHeight(15);
        markAsReturn.setGraphic(okView2);

        MenuItem close = new MenuItem("Close Management");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SubMenu.getInstance().hideManagement();
            }
        });
        Image closeI = new Image(new File(".\\Icon\\close.png").toURI().toString());
        ImageView closeView = new ImageView(closeI);
        closeView.setFitWidth(15);
        closeView.setFitHeight(15);
        close.setGraphic(closeView);

        menu.getItems().addAll(space, reload, markAsBorrow, markAsReturn, close);
    }

    private void setUpOrderTable() {
        TableColumn<OrderData, String> c1 = new TableColumn<>("Book Order");
        c1.setCellValueFactory(new PropertyValueFactory<>("bookOrder"));
        c1.setPrefWidth(240);


        TableColumn<OrderData, Integer> c2 = new TableColumn<>("Book Id");
        c2.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        c2.setPrefWidth(240);

        TableColumn<OrderData, String> c3 = new TableColumn<>("Customer Name");
        c3.setCellValueFactory(new PropertyValueFactory<>("customerOrder"));
        c3.setPrefWidth(240);

        TableColumn<OrderData, Integer> c4 = new TableColumn<>("Customer Id");
        c4.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        c4.setPrefWidth(240);

        TableColumn<OrderData, Date> c5 = new TableColumn<>("Order Date");
        c5.setCellValueFactory(new PropertyValueFactory<>("timeOrder"));
        c5.setPrefWidth(240);

        tableOrder.getColumns().addAll(c1, c2, c3, c4, c5);

        reloadTableOrder();

        tableOrder.setRowFactory(tableView -> {
            final TableRow<OrderData> row = new TableRow<>();
            row.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    final OrderData s = row.getItem();
                    if (s != null) {
//                        row.setStyle("-fx-background-color: #E9E9E9;");
                    }
                }
            });
            row.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
//                    row.setStyle("-fx-background-color: white;");
                }
            });
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            return row;
        });

        tableOrder.setStyle("-fx-border-color: white;\n" +
                "    -fx-table-cell-border-color:white;\n" +
                "    -fx-background-color: white;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-size: 17px;");

        tableOrder.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                menu.show(tableOrder, event.getScreenX(), event.getScreenY());
            }
        });

    }


    private void setUpBorrowTable() {
        TableColumn<BorrowData, String> c1 = new TableColumn<>("Book Borrow");
        c1.setCellValueFactory(new PropertyValueFactory<>("bookBorrow"));
        c1.setPrefWidth(150);


        TableColumn<BorrowData, Integer> c2 = new TableColumn<>("Book Id");
        c2.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        c2.setPrefWidth(150);

        TableColumn<BorrowData, String> c3 = new TableColumn<>("Customer Borrow");
        c3.setCellValueFactory(new PropertyValueFactory<>("customerBorrow"));
        c3.setPrefWidth(150);

        TableColumn<BorrowData, Integer> c4 = new TableColumn<>("Customer Id");
        c4.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        c4.setPrefWidth(150);

        TableColumn<BorrowData, String> c5 = new TableColumn<>("Accept Employee");
        c5.setCellValueFactory(new PropertyValueFactory<>("employeeAccept"));
        c5.setPrefWidth(150);

        TableColumn<BorrowData, Integer> c6 = new TableColumn<>("Employee account");
        c6.setCellValueFactory(new PropertyValueFactory<>("empAccId"));
        c6.setPrefWidth(150);

        TableColumn<BorrowData, Date> c7 = new TableColumn<>("Order Date");
        c7.setCellValueFactory(new PropertyValueFactory<>("timeOrder"));
        c7.setPrefWidth(150);

        TableColumn<BorrowData, Date> c8 = new TableColumn<>("Borrow Date");
        c8.setCellValueFactory(new PropertyValueFactory<>("timeBorrow"));
        c8.setPrefWidth(150);

        tableBorrow.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8);

//        reloadTableOrder();

        tableBorrow.setRowFactory(tableView -> {
            final TableRow<BorrowData> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            return row;
        });

        tableBorrow.setStyle("-fx-border-color: white;\n" +
                "    -fx-table-cell-border-color:white;\n" +
                "    -fx-background-color: white;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-size: 17px;");

        tableBorrow.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                menu.show(tableOrder, event.getScreenX(), event.getScreenY());
            }
        });

        reloadTableBorrow();
    }

    public void reloadTableOrder() {
        MyTask<List<OrderData>> myTask = new MyTask<List<OrderData>>() {
            @Override
            public void whatNext() {
                tableOrder.getItems().clear();
                tableOrder.getItems().addAll(this.getValue());
            }

            @Override
            protected List<OrderData> call() throws Exception {
                List<OrderData> list = Client.getInstance().getOrderedManageData();
                return list;
            }
        };
        myTask.execute();
    }

    public void reloadTableBorrow() {
        MyTask<List<BorrowData>> myTask = new MyTask<List<BorrowData>>() {
            @Override
            public void whatNext() {
                tableBorrow.getItems().clear();
                tableBorrow.getItems().addAll(this.getValue());
            }

            @Override
            protected List<BorrowData> call() throws Exception {
                List<BorrowData> list = Client.getInstance().getBorrowedManageData();
                return list;
            }
        };
        myTask.execute();
    }

    private static AnchorPane anchorPane = null;
    private static OrderManage controller;

    private static AnchorPane getManage() {
        AnchorPane pane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\menu\\OrderManage.fxml").toURI().toURL());
            pane = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    public static AnchorPane getInstance() {
        if (anchorPane == null) {
            anchorPane = getManage();
        }
        return anchorPane;
    }

    public static OrderManage getController() {
        return controller;
    }

    public void reLoad(MouseEvent event) {
        if (tabOrder.isSelected()) {
            reloadTableOrder();
        }
        if (tabBorrow.isSelected()) {
            reloadTableBorrow();
        }
    }




}
