package com.example.library2.dataSend;

import java.io.Serializable;

public class Request implements Serializable {
    public RequestType type;

    public Request(RequestType type) {
        this.type = type;
    }
}
