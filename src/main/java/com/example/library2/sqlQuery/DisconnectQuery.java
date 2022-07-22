package com.example.library2.sqlQuery;

import com.example.library2.dataSend.SocketData;
import com.example.library2.sever.MainSever;
import com.example.library2.utilities.MessageType;

import java.io.IOException;
import java.net.Socket;

public class DisconnectQuery {
    public static MessageType disconnect(SocketData data) throws IOException {
        for (Socket socket : MainSever.getInstance().getConnecting().keySet()) {
            if (socket.isConnected() && socket.getInetAddress().toString().equals(data.ip) && socket.getPort() == data.port) {
                socket.close();
                MainSever.getInstance().disconnect(socket);
                return MessageType.SUCCESS;
            }
        }
        return MessageType.UNEXPECTED_ERROR;
    }
}
