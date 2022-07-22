package com.example.library2.sever;

import com.example.library2.dataSend.Data;
import com.example.library2.dataSend.Request;

import java.io.*;
import java.net.Socket;

public class SeverExecute extends Thread {
    private final Socket socket;

    public SeverExecute(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println("Processing: " + socket);
        System.out.println("------------------------------------------");
        try {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            ObjectInputStream inputStream = new ObjectInputStream(is);
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            while (socket.isConnected()) {
                Object obj = inputStream.readObject();
                if (obj instanceof Request request) {
                    HandRequest.executeRequest(request, outputStream, socket);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Request Processing Error: " + socket + " disconnected");
        } finally {
            MainSever.getInstance().disconnect(socket);
        }
        System.out.println("Complete processing: " + socket);
        MainSever.getInstance().getStatus();
    }
}
