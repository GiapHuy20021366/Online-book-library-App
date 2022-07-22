package com.example.library2.menu;

import com.example.library2.client.Client;
import com.example.library2.dataSend.SocketData;
import com.example.library2.transitionTool.MyTask;
import com.example.library2.utilities.MessageType;
import com.example.library2.utilities.SubMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminManagement implements Initializable {
    @FXML
    Tab sever;

    @FXML
    TableView<SocketData> connects;

    @FXML
    Text countConnect;

    @FXML
    Text countLogin;

    @FXML
    Button closeSever;

    @FXML
    AnchorPane paneTabSever;

    ContextMenu menuOnTableConnect = new ContextMenu();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        countConnect.setFont(Font.font("DejaVu Sans Mono", 20));
        countConnect.setFill(Color.GREEN);

        countLogin.setFont(Font.font("DejaVu Sans Mono", 20));
        countLogin.setFill(Color.GREEN);

        setUpMenuConnect();

        setUpTableConnects();

//        setTab();


        closeSever.setStyle("-fx-background-color: \n" +
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

    public void closeSever(MouseEvent event) {
        Client.getInstance().closeSever();
        connects.getItems().clear();
        countLogin.setText("Login: 0");
        countConnect.setText("Online: 0");
    }

    private void setUpMenuConnect() {
        MenuItem space = new MenuItem("           ");

        MenuItem reload = new MenuItem("Reload");
        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadConnect();
            }
        });
        Image openIcon = new Image(new File(".\\Icon\\reload.png").toURI().toString());
        ImageView openView = new ImageView(openIcon);
        openView.setFitWidth(15);
        openView.setFitHeight(15);
        reload.setGraphic(openView);

        MenuItem disConnect = new MenuItem("Disconnect");
        disConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SocketData data = connects.getSelectionModel().getSelectedItem();
                if (data != null) {
                    MessageType type = Client.getInstance().disconnectSocketByAdmin(data);
                    if (type.equals(MessageType.SUCCESS)) {
                        loadConnect();
                    }
                }
            }
        });

        Image dis = new Image(new File(".\\Icon\\disconnect.png").toURI().toString());
        ImageView disV = new ImageView(dis);
        disV.setFitWidth(15);
        disV.setFitHeight(15);
        disConnect.setGraphic(disV);

        MenuItem close = new MenuItem("Close Admin");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SubMenu.getInstance().hideSeverManagement();
            }
        });
        Image closeI = new Image(new File(".\\Icon\\close.png").toURI().toString());
        ImageView closeView = new ImageView(closeI);
        closeView.setFitWidth(15);
        closeView.setFitHeight(15);
        close.setGraphic(closeView);

        menuOnTableConnect.getItems().addAll(space, reload, disConnect, close);

        paneTabSever.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                menuOnTableConnect.show(paneTabSever, event.getScreenX(), event.getScreenY());
                SocketData data = connects.getSelectionModel().getSelectedItem();
                disConnect.setVisible(data != null);
            }
        });
    }

    private void setTab() {
        ContextMenu menu = new ContextMenu();

        MenuItem space = new MenuItem("        ");

        MenuItem close = new MenuItem("Close Admin");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SubMenu.getInstance().hideSeverManagement();
            }
        });
        Image closeI = new Image(new File(".\\Icon\\close.png").toURI().toString());
        ImageView closeView = new ImageView(closeI);
        closeView.setFitWidth(15);
        closeView.setFitHeight(15);
        close.setGraphic(closeView);


        menu.getItems().addAll(space, close);

        paneTabSever.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                menu.show(paneTabSever, event.getScreenX(), event.getScreenY());
            }
        });

    }

    private void setUpTableConnects() {

        TableColumn<SocketData, String> c1 = new TableColumn<>("Account");
        c1.setCellValueFactory(new PropertyValueFactory<>("name"));
        c1.setPrefWidth(240);

        TableColumn<SocketData, Integer> c2 = new TableColumn<>("Ip Address");
        c2.setCellValueFactory(new PropertyValueFactory<>("ip"));
        c2.setPrefWidth(240);

        TableColumn<SocketData, Integer> c3 = new TableColumn<>("Port");
        c3.setCellValueFactory(new PropertyValueFactory<>("port"));
        c3.setPrefWidth(240);


        connects.getColumns().addAll(c1, c2, c3);
        connects.setPrefWidth(720);



        loadConnect();

    }

    public void loadConnect() {
        MyTask<List<SocketData>> myTask = new MyTask<List<SocketData>>() {
            @Override
            public void whatNext() {
                connects.getItems().clear();
                connects.getItems().addAll(getValue());
                countConnect.setText("Online: " + getValue().size());
                int count = 0;
                for (SocketData socketData : getValue()) {
                    if (socketData.getName() != null) {
                        count++;
                    }
                }
                countLogin.setText("Login: " + count);
            }

            @Override
            protected List<SocketData> call() throws Exception {
                List<SocketData> list = Client.getInstance().getConnects();
                return list;
            }
        };
        myTask.execute();
    }


    private static AnchorPane anchorPane = null;
    private static AdminManagement controller;

    private static AnchorPane getManage() {
        AnchorPane pane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new File(".\\src\\main\\java\\com\\example\\library2\\menu\\AdminManagement.fxml").toURI().toURL());
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

    public static AdminManagement getController() {
        return controller;
    }
}
