package com.example.library2.sever;

import com.example.library2.utilities.Account;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainSever {
    private final static MainSever mainSever = new MainSever();
    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);
    private final HashMap<Socket, String> connecting = new HashMap<>();
    private ServerSocket serverSocket;
    private  ExecutorService executor;

    private MainSever() {
    }

    private static final int NUM_OF_THREAD = 4;

    public HashMap<Socket, String> getConnecting() {
        return connecting;
    }

    public final static int SERVER_PORT = 7777;

    public void runSever() throws IOException, CannotRunSeverException {
        if (isRunning.get()) {
            throw new CannotRunSeverException("Sever is Running, please stop sever before run");
        }
        isRunning.set(true);


        executor = Executors.newFixedThreadPool(NUM_OF_THREAD);

        try {
            System.out.println("Binding to port " + SERVER_PORT + ", please wait  ...");
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server started: " + serverSocket);
            System.out.println("Sever is running, number of client can connect a time: " + NUM_OF_THREAD);
            System.out.println("Waiting for a client ...");
            System.out.println("-------------------------------------------------------------------------------");

            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client accepted: " + socket);
                    connecting.put(socket, null);
                    System.out.println("Number of socket connecting now: " + connecting.size());
                    SeverExecute handler = new SeverExecute(socket);
                    executor.execute(handler);

                } catch (IOException e) {
                    System.err.println(" Connection Error: " + e);
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

    public void disconnect(Socket socket) {
        connecting.remove(socket);
        System.out.println("Disconnect[Account:" + connecting.get(socket) + "," + socket);
    }

    public void logOut(Socket socket) {
        System.out.println("LogOut[Account:" + connecting.get(socket) + "," + socket);
        connecting.put(socket, null);
    }

    public void updateAcc(Socket socket, String account) {
        connecting.put(socket, account);
        System.out.println("Login[Account:" + account + "," + socket);
    }

    public static MainSever getInstance() {
        return mainSever;
    }

    public void getStatus() {
        System.out.println("Number of socket connecting now: " + connecting.size());
        System.out.println("-----------------------------------------------------");
    }

    public void shutdown() {
        for (Socket socket : connecting.keySet()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        connecting.clear();

        System.out.println("Sever shutdown by Admin! Goodbye");
        if (serverSocket != null) {
            try {
                executor.shutdown();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAccName(Socket socket) {
        return connecting.get(socket);
    }

    public static void main(String[] args) throws IOException, CannotRunSeverException {
        MainSever.getInstance().runSever();
    }
}
