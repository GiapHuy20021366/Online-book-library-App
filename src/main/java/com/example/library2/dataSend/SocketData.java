package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

import java.io.Serializable;

public class SocketData implements Serializable {
    public String name;
    public String ip;
    public int port;

    public SocketData(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
