package com.example.library2.utilities;

import com.example.library2.client.Client;
import com.example.library2.menu.AccountUtility;

import java.io.Serializable;

public class Account implements Serializable {
    private static final Account acc = new Account();
    private String name = "";
    private String pass = "";
    private boolean login = false;

    private Account() {
//        setUpTest();
    }

    public MessageType setAcc(String name, String pass) {
        MessageType type = Client.getInstance().checkAcc(name, pass);
        if (type.equals(MessageType.SUCCESS)) {
            this.name = name;
            this.pass = pass;
            this.login = true;
        }
        switch (Client.getInstance().getPos()) {
            case EMPLOYEE -> {
                AccountUtility.getController().setVisibleManage(true);
                AccountUtility.getController().setVisibleSeverManage(false);
            }
            case MANAGEMENT -> {
                AccountUtility.getController().setVisibleManage(true);
                AccountUtility.getController().setVisibleSeverManage(true);
            }

            default -> AccountUtility.getController().setVisibleManage(false);
        }
        return type;
    }

    public MessageType logOut() {
        MessageType type = Client.getInstance().logOut(name);
        switch (type) {
            case SUCCESS -> {
                this.name = "";
                this.pass = "";
                this.login = false;
                AccountUtility.getController().setVisibleManage(false);
                SubMenu.getInstance().hideManagement();
                SubMenu.getInstance().hideSeverManagement();
            }
            case SEVER_RUN_OUT -> {
                //
            }
        }
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean checkPass(String pass) {
        return this.pass.equals(pass);
    }

    public boolean isLogin() {
        return login;
    }

    public static Account getInstance() {
        return acc;
    }

    public static Account getAcc() {
        Account account = new Account();
        account.name = acc.name;
        account.pass = acc.pass;
        return account;
    }

    private void setUpTest() {
        this.name = "Huy@321106";
        this.pass = "Huy@321106";
        this.login = true;
    }
}
