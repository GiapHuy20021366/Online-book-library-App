package com.example.library2.dataSend;

public class LogOutRequest extends Request {
    public String name;

    public LogOutRequest(String name) {
        super(RequestType.LOG_OUT);
        this.name = name;
    }
}
