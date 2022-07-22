package com.example.library2.dataSend;

public class LoginRequest extends Request {
    public String name;
    public String pass;

    public LoginRequest(RequestType type) {
        super(type);
    }

    public LoginRequest(String name, String pass) {
        this(RequestType.LOGIN);
        this.name = name;
        this.pass = pass;
    }
}
