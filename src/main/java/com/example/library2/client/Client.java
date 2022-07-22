package com.example.library2.client;

import com.example.library2.dataSend.*;
import com.example.library2.sever.MainSever;
import com.example.library2.sqlQuery.NewBookRequest;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.MessageType;
import com.example.library2.utilities.SubMenu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class Client {
    private static Client client = new Client();
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private static volatile boolean isConnected = false;
    private boolean severIsClosed = true;
    private Timeline reConnect;

    public boolean isSeverIsClosed() {
        return severIsClosed;
    }

    private Client() {
    }

    private void reConnect(long time) {
        if (reConnect != null) {
            reConnect.stop();
        }
        reConnect = new Timeline(new KeyFrame(Duration.millis(time), ev -> {
            connectToSever();
        }));
        reConnect.play();
    }

    public void connectToSever() {
        isConnected = false;
        severIsClosed = true;
        try {
            Socket socket = new Socket("localhost", MainSever.SERVER_PORT);
            System.out.println("Connected!");

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            output = new ObjectOutputStream(outputStream);
            input = new ObjectInputStream(inputStream);

            isConnected = true;
            severIsClosed = false;
        } catch (Exception e) {
            reConnect(1200);
        }
    }

    public MessageType checkAcc(String name, String pass) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }

        synchronized (input) {
            try {
                output.writeObject(new LoginRequest(name, pass));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public MessageType getPos() {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }

        synchronized (input) {
            try {
                output.writeObject(new GetPosRequest());
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public List<SocketData> getConnects() {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new GetConnects());
                return (List<SocketData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public MessageType disconnectSocketByAdmin(SocketData socketData) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }

        synchronized (input) {
            try {
                output.writeObject(new DisconnectSocketRequest(socketData));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public void closeSever() {
        if (severIsClosed) {
            return ;
        }
        try {
            output.writeObject(new CloseSever());

        } catch (Exception e) {
            connectToSever();
        }
    }

    public MessageType checkNewAccount(String acc, String pass, String phone, String email) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        synchronized (input) {
            try {
                output.writeObject(new RegisNewAccountRequest(acc, pass, phone, email));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public BookData getBookData(int bookId) {
        synchronized (input) {
            try {
                output.writeObject(new BookDataRequest(bookId));
                return (BookData) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<BookData> getListBookData(int fromId, int toId) {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new ListBookDataRequest(fromId, toId));
                return (List<BookData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<BookData> getRankBookData() {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new RankBookRequest());
                return (List<BookData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public List<BookData> getLikedBookData() {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new LikedBookRequest());
                return (List<BookData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<BookData> getBorrowedBookData() {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new BorrowedBookRequest());
                return (List<BookData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<BookData> getOrderedBookData() {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new OrderedBookRequest());
                return (List<BookData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public List<OrderData> getOrderedManageData() {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new OrderedManageRequest());
                return (List<OrderData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<BorrowData> getBorrowedManageData() {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new BorrowedManageRequest());
                return (List<BorrowData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public MessageType markBookAsBorrow(int bookId, int accId) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        synchronized (input) {
            try {
                output.writeObject(new MarkBorrowRequest(bookId, accId));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public MessageType markBookAsReturn(int bookID, int accId) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        synchronized (input) {
            try {
                output.writeObject(new MarkReturn(bookID, accId));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public List<BookData> getNewBookData() {
        getConnected();
        synchronized (input) {
            try {
                output.writeObject(new NewBookRequest());
                return (List<BookData>) input.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void getConnected() {
        while (!isConnected) {
            Thread.onSpinWait();
        }
    }


    public static Client getInstance() {
        if (!isConnected) {
            client.connectToSever();
        }
        return client;
    }

    public List<String> getBookAsFind(String s) {
        if (severIsClosed) {
            return null;
        }
        synchronized (input) {
            try {
                output.writeObject(new GetBookFindRequest(s));
                return (List<String>) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return null;
            }
        }
    }

    public List<BookData> getBookDataAsFind(String s) {
        if (severIsClosed) {
            return null;
        }
        synchronized (input) {
            try {
                output.writeObject(new GetBookDataFindRequest(s));
                return (List<BookData>) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return null;
            }
        }
    }

    public MessageType likeBook(int book_ID) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return MessageType.UNEXPECTED_ERROR;
        }
        synchronized (input) {
            try {
                output.writeObject(new LikeRequest(book_ID));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public MessageType disLikeBook(int bookID) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return MessageType.UNEXPECTED_ERROR;
        }
        synchronized (input) {
            try {
                output.writeObject(new DisLikeRequest(bookID));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public MessageType hadLikeBook(int book_ID) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return MessageType.UNEXPECTED_ERROR;
        }
        synchronized (input) {
            try {
                output.writeObject(new LikeRequest(RequestType.HAD_LIKE_REQUEST, book_ID));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public MessageType checkOrder(int book_ID) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return MessageType.UNEXPECTED_ERROR;
        }
        synchronized (input) {
            try {
                output.writeObject(new CheckOrderRequest(book_ID));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public MessageType order(int book_ID) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return MessageType.UNEXPECTED_ERROR;
        }
        synchronized (input) {
            try {
                output.writeObject(new OrderRequest(book_ID));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public MessageType logOut(String name) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return MessageType.UNEXPECTED_ERROR;
        }
        synchronized (input) {
            try {
                output.writeObject(new LogOutRequest(name));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }

    public MessageType removeOrder(int book_ID) {
        if (severIsClosed) {
            return MessageType.SEVER_RUN_OUT;
        }
        if (!Account.getInstance().isLogin()) {
            SubMenu.getInstance().openLoginMenu();
            return MessageType.UNEXPECTED_ERROR;
        }
        synchronized (input) {
            try {
                output.writeObject(new RemoveOrderRequest(book_ID));
                return (MessageType) input.readObject();
            } catch (Exception e) {
                connectToSever();
                return MessageType.SEVER_RUN_OUT;
            }
        }
    }




}
