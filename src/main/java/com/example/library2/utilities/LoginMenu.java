package com.example.library2.utilities;

import com.example.library2.client.Client;
import com.example.library2.menu.AccountUtility;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginMenu extends AnchorPane {
    private final ImageView size = new ImageView();
    private final AnchorPane login = new AnchorPane();
    private final AnchorPane regis = new AnchorPane();
    private final Text loginT = new Text("Đăng nhập");
    private final Text regisT = new Text("Đăng kí");
    private final Text logOutMid = new Text("Đăng xuất");
    private Button logOut;
    private Button log;
    private Button exit = new Button("X");

    private static final LoginMenu loginMenu = new LoginMenu();
    private final IntegerProperty inMode = new SimpleIntegerProperty(1);

    final Paint value0 = Paint.valueOf("rgba(0,0,0,0.8)");
    final Paint value1 = Paint.valueOf("rgba(255,0,0,0.3)");

    final TextField getName = new TextField();
    final PasswordField getPass = new PasswordField();
    final TextField errLog = new TextField();


    private LoginMenu() {
        getChildren().add(size);
        size.setFitWidth(500);
        size.setFitHeight(500);
//        size.setImage(new Image(new File(".\\Icon\\HomeIcon.png").toURI().toString()));
        size.toBack();


        getChildren().add(login);
        getChildren().add(regis);
        getChildren().add(exit);

        exit.setTextFill(Color.WHITE);
        exit.setPrefWidth(30);
        exit.setPrefHeight(30);
        exit.setLayoutY(0);
        exit.setLayoutX(470);
        exit.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        exit.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
        exit.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 20));
        exit.setOnMouseEntered(event -> exit.setTextFill(Color.RED));
        exit.setOnMouseExited(event -> exit.setTextFill(Color.WHITE));
        exit.setOnMouseClicked(event -> {
            SubMenu.getInstance().closeLoginMenu();
            errLog.setVisible(false);
        });



        login.setLayoutY(100);
        regis.setLayoutY(100);

        this.setStyle("-fx-background-color: rgba(0,0,0,0.8)");

        regis.setVisible(false);

        loginT.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 40));
        loginT.setStyle("-fx-underline: true;");
        loginT.setFill(Color.RED);
        loginT.setTextAlignment(TextAlignment.CENTER);
        loginT.setX(size.getFitWidth() / 4 - loginT.getBoundsInLocal().getWidth() / 2);
        loginT.setY(50);
        loginT.setOnMousePressed(event -> {
            if (inMode.get() == 2) {
                loginT.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 40));
                loginT.setStyle("-fx-underline: true;");
                login.setVisible(true);
                regisT.setFont(Font.font("DejaVu Sans Mono", 40));
                regisT.setStyle("-fx-underline: false;");
                regis.setVisible(false);
            }
            inMode.set(1);
        });
        loginT.setOnMouseEntered(event -> loginT.setStyle("-fx-underline: true;"));
        loginT.setOnMouseExited(event -> {
            if (inMode.get() != 1) {
                loginT.setStyle("-fx-underline: false;");
            }
        });


        regisT.setFont(Font.font("DejaVu Sans Mono", 40));
        regisT.setFill(Color.RED);
        regisT.setTextAlignment(TextAlignment.CENTER);
        regisT.setX(size.getFitWidth() * 0.75d - regisT.getBoundsInLocal().getWidth() / 2);
        regisT.setY(50);
        regisT.setOnMousePressed(event -> {
            if (inMode.get() == 1) {
                regisT.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 40));
                regisT.setStyle("-fx-underline: true;");
                regis.setVisible(true);
                loginT.setFont(Font.font("DejaVu Sans Mono", 40));
                loginT.setStyle("-fx-underline: false;");
                login.setVisible(false);
            }
            inMode.set(2);
        });
        regisT.setOnMouseEntered(event -> regisT.setStyle("-fx-underline: true;"));
        regisT.setOnMouseExited(event -> {
            if (inMode.get() != 2) {
                regisT.setStyle("-fx-underline: false;");
            }
        });

        logOutMid.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 40));
        logOutMid.setStyle("-fx-underline: true;");
        logOutMid.setFill(Color.RED);
        logOutMid.setTextAlignment(TextAlignment.CENTER);
        logOutMid.setX(size.getFitWidth() / 2 - loginT.getBoundsInLocal().getWidth() / 2);
        logOutMid.setY(50);
        logOutMid.setVisible(false);

        getChildren().add(loginT);
        getChildren().add(regisT);
        getChildren().add(logOutMid);

        setUpLogin();
        setUpRegis();
    }


    private void setUpLogin() {


        errLog.setLayoutX(100);
        errLog.setLayoutY(12);
        errLog.setPrefWidth(300);
        errLog.setMaxWidth(300);
        errLog.setEditable(false);
        errLog.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        errLog.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                ";-fx-text-fill: red; -fx-font-size: 16px;");
        errLog.toBack();
        errLog.setVisible(false);

        getName.setPromptText("User Name");
        getName.setLayoutY(50);
        getName.setLayoutX(100);
        getName.setPrefWidth(300);
        getName.setPrefHeight(40);
        getName.setMaxWidth(300);

        getName.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        getName.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
        getName.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getName.getText().equals("User Name")) {
                    getName.setText("");
                }
            }
        });
        getName.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getName.getText().trim().equals("") && !getName.isFocused()) {
                    getName.setText("User Name");
                }
            }
        });

        getName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getName.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
                getName.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
                errLog.setVisible(false);
            }
        });


        getPass.setLayoutX(100);
        getPass.setLayoutY(100);
        getPass.setPrefWidth(300);
        getPass.setPrefHeight(40);
        getPass.setMaxWidth(300);
        getPass.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        getPass.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
        getPass.setPromptText("PassWord");
        getPass.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getPass.getText().equals("PassWord")) {
                    getPass.setText("");
                }
            }
        });
        getPass.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getPass.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
                getPass.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
                errLog.setVisible(false);
                if (getName.getText().trim().equals("") && !getName.isFocused()) {
                    getName.setText("User Name");
                }
            }
        });
        getPass.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (getPass.getText().trim().equals("") && !getPass.isFocused()) {
                    getPass.setText("PassWord");
                }
            }
        });


        log = new Button("LOGIN");
        logOut = new Button("LOG OUT");


        log.setTextFill(Color.WHITE);
        log.setPrefWidth(300);
        log.setPrefHeight(10);
        log.setLayoutY(150);
        log.setLayoutX(100);
        log.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        log.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
        log.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 20));
        log.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                log.setTextFill(Color.RED);
            }
        });
        log.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                log.setTextFill(Color.WHITE);
            }
        });

        log.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (Account.getInstance().setAcc(getName.getText().trim(), getPass.getText().trim())) {
                    case ERROR_NAME -> {
                        getName.setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        errLog.setText("Tên đăng nhập không chính xác");
                        errLog.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        errLog.setVisible(true);
                    }
                    case ERROR_PASSWORD -> {
                        getPass.setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        errLog.setText("Mật khẩu không chính xác");
                        errLog.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        errLog.setVisible(true);
                    }
                    case SEVER_RUN_OUT -> {
                        errLog.setText("Không thể kết nối máy chủ");
                        errLog.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        errLog.setVisible(true);
                    }
                    case SUCCESS -> {
                        errLog.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: green; -fx-font-size: 16px;");
                        errLog.setText("Đăng nhập thành công");
                        errLog.setVisible(true);
                        getName.setVisible(false);
                        getPass.setVisible(false);
                        log.setVisible(false);
                        logOut.setVisible(true);
                        regisT.setVisible(false);
                        loginT.setVisible(false);
                        logOutMid.setVisible(true);

                        AccountUtility.menuItems.get("Log").setText("Đăng xuất");
                    }
                }
            }
        });


        logOut.setTextFill(Color.WHITE);
        logOut.setPrefWidth(300);
        logOut.setPrefHeight(10);
        logOut.setLayoutY(150);
        logOut.setLayoutX(100);
        logOut.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        logOut.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
        logOut.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 20));
        logOut.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logOut.setTextFill(Color.RED);
            }
        });
        logOut.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logOut.setTextFill(Color.WHITE);
            }
        });

        logOut.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch (Account.getInstance().logOut()) {
                    case SUCCESS -> {
                        getName.setVisible(true);
                        getPass.setVisible(true);
                        log.setVisible(true);
                        logOut.setVisible(false);
                        errLog.setVisible(false);
                        getPass.setText("");
                        regisT.setVisible(true);
                        loginT.setVisible(true);
                        logOutMid.setVisible(false);
                        AccountUtility.menuItems.get("Log").setText("Đăng nhập");
                    }
                    case ERROR_NAME -> {
                        System.out.println("Error Name");
                    }
                    case SEVER_RUN_OUT -> {
                        errLog.setText("Không thể kết nối máy chủ");
                        errLog.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        errLog.setVisible(true);
                    }
                }

            }
        });
        logOut.setVisible(false);

        login.getChildren().add(getName);
        login.getChildren().add(getPass);
        login.getChildren().add(log);
        login.getChildren().add(logOut);
        login.getChildren().add(errLog);
    }


    private void setUpRegis() {

        TextField err = new TextField("Err");
        err.setLayoutX(100);
        err.setLayoutY(3);
        err.setPrefWidth(300);
        err.setMaxWidth(300);
        err.setEditable(false);
        err.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                ";-fx-text-fill: red; -fx-font-size: 16px;");
        err.toBack();
        err.setVisible(false);

        HashMap<String, TextField> groupText = new HashMap<>();


        TextField accId = new TextField();
        groupText.put("AccId", accId);
        setTextField(accId, 40, "User Name");

        PasswordField pass0 = new PasswordField();
        groupText.put("PassFirst", pass0);
        setTextField(pass0, 85, "PassWord");

        PasswordField pass1 = new PasswordField();
        groupText.put("PassSecond", pass1);
        setTextField(pass1, 130, "Enter PassWord Again");


        TextField phone = new TextField();
        groupText.put("Phone", phone);
        setTextField(phone, 175, "Phone Number");

        TextField email = new TextField();
        groupText.put("Email", email);
        setTextField(email, 220, "Email");


        Button regisButton = new Button("REGIS");
        regisButton.setTextFill(Color.WHITE);
        regisButton.setPrefWidth(300);
        regisButton.setPrefHeight(10);
        regisButton.setLayoutY(265);
        regisButton.setLayoutX(100);
        regisButton.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        regisButton.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
        regisButton.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 20));
        regisButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                regisButton.setTextFill(Color.RED);
            }
        });
        regisButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                regisButton.setTextFill(Color.WHITE);
            }
        });

        regisButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boolean isFull = true;
                for (String s : groupText.keySet()) {
                    TextField f = groupText.get(s);
                    if (f.getText().trim().equals("") && !s.equals("PassFirst") && !s.equals("PassSecond")) {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        f.setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Không thể để trống");
                        err.setVisible(true);
                        isFull = false;
                    }
                }
                if (!isFull) return;

                if (groupText.get("PassFirst").getText().contains(" ")) {
                    err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                            ";-fx-text-fill: red; -fx-font-size: 16px;");
                    groupText.get("PassFirst").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                    err.setText("Mật khẩu xuất hiện dấu cách");
                    err.setVisible(true);
                    return;
                }

                switch (Check.isValid(groupText.get("AccId").getText())) {
                    case Check.LENGTH_ERROR -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("AccId").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải từ 8 đến 20 ký tự");
                        err.setVisible(true);
                        return;
                    }
                    case Check.LOSS_UPPERCASE -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("AccId").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải có ít nhất 1 kí tự hoa");
                        err.setVisible(true);
                        return;
                    }
                    case Check.LOSS_LOWERCASE -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("AccId").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải có ít nhất 1 kí tự thường");
                        err.setVisible(true);
                        return;
                    }
                    case Check.LOSS_NUMBER -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("AccId").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải có ít nhất 1 chữ số");
                        err.setVisible(true);
                        return;
                    }
                    case Check.LOSS_SPECIAL_CHAR -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("AccId").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải có ít nhất 1 kí tự đặc biệt");
                        err.setVisible(true);
                        return;
                    }
                }

                switch (Check.isValid(groupText.get("PassFirst").getText())) {
                    case Check.LENGTH_ERROR -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("PassFirst").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải từ 8 đến 20 ký tự");
                        err.setVisible(true);
                        return;
                    }
                    case Check.LOSS_UPPERCASE -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("PassFirst").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải có ít nhất 1 kí tự hoa");
                        err.setVisible(true);
                        return;
                    }
                    case Check.LOSS_LOWERCASE -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("PassFirst").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải có ít nhất 1 kí tự thường");
                        err.setVisible(true);
                        return;
                    }
                    case Check.LOSS_NUMBER -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("PassFirst").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải có ít nhất 1 chữ số");
                        err.setVisible(true);
                        return;
                    }
                    case Check.LOSS_SPECIAL_CHAR -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        groupText.get("PassFirst").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                        err.setText("Phải có ít nhất 1 kí tự đặc biệt");
                        err.setVisible(true);
                        return;
                    }
                }

                if (!Check.validatePhone(groupText.get("Phone").getText())) {
                    err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                            ";-fx-text-fill: red; -fx-font-size: 16px;");
                    groupText.get("Phone").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                    err.setText("Phone không hợp lệ");
                    err.setVisible(true);
                    return;
                }

                if (!Check.validateEmail(groupText.get("Email").getText())) {
                    err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                            ";-fx-text-fill: red; -fx-font-size: 16px;");
                    groupText.get("Email").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                    err.setText("Email không hợp lệ");
                    err.setVisible(true);
                    return;
                }

                if (!groupText.get("PassFirst").getText().equals(groupText.get("PassSecond").getText())) {
                    err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                            ";-fx-text-fill: red; -fx-font-size: 16px;");
                    groupText.get("PassSecond").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                    err.setText("Mật khẩu không trùng khớp");
                    err.setVisible(true);
                    return;
                }

                switch (Client.getInstance().checkNewAccount(
                        groupText.get("AccId").getText(),
                        groupText.get("PassFirst").getText(),
                        groupText.get("Phone").getText(),
                        groupText.get("Email").getText()
                )) {
                    case UNEXPECTED_ERROR -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        err.setText("Lỗi không xác định");
                        err.setVisible(true);
                    }
                    case SEVER_RUN_OUT -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        err.setText("Không thể kết nối máy chủ");
                        err.setVisible(true);
                    }
                    case ACCOUNT_EXIST -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        err.setText("Tài khoản đã tồn tại");
                        err.setVisible(true);
                        groupText.get("AccId").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                    case PHONE_EXIST -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        err.setText("Số điện thoại đã tồn tại");
                        err.setVisible(true);
                        groupText.get("Phone").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                    case EMAIL_EXIST -> {
                        err.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: red; -fx-font-size: 16px;");
                        err.setText("Email đã tồn tại");
                        err.setVisible(true);
                        groupText.get("Email").setBackground(new Background(new BackgroundFill(value1, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                    case SUCCESS -> {

                        loginT.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 40));
                        loginT.setStyle("-fx-underline: true;");
                        login.setVisible(true);
                        regisT.setFont(Font.font("DejaVu Sans Mono", 40));
                        regisT.setStyle("-fx-underline: false;");
                        regis.setVisible(false);
                        inMode.set(1);

                        errLog.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2) +
                                ";-fx-text-fill: green; -fx-font-size: 16px;");
                        errLog.setText("Tạo tài khoản thành công");
                        errLog.setVisible(true);

                        getName.setText(groupText.get("AccId").getText().trim());
                        getPass.setText(groupText.get("PassFirst").getText().trim());
                        for (String s : groupText.keySet()) {
                            groupText.get(s).setText("");
                        }

                    }
                }


            }
        });


        regis.getChildren().add(regisButton);
        regis.getChildren().add(err);
        for (String s : groupText.keySet()) {
            TextField f = groupText.get(s);
            regis.getChildren().add(f);
            f.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    f.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
                    f.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
                    err.setVisible(false);
                }
            });
        }
    }

    private void setTextField(TextField textField, double y, String promptText) {
        textField.setLayoutX(100);
        textField.setLayoutY(y);
        textField.setPrefWidth(300);
        textField.setPrefHeight(40);
        textField.setMaxWidth(300);
        textField.setBackground(new Background(new BackgroundFill(value0, CornerRadii.EMPTY, Insets.EMPTY)));
        textField.setStyle("-fx-control-inner-background: #" + value0.toString().substring(2));
        textField.setPromptText(promptText);
    }


    public static LoginMenu getInstance() {
        return loginMenu;
    }
}
