package com.example.library2.dataSend;

public class DisconnectSocketRequest extends Request {
    public SocketData socketData;

    public DisconnectSocketRequest(SocketData socketData) {
        super(RequestType.DISCONNECT_SOCKET);
        this.socketData = socketData;
    }
}
