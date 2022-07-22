package com.example.library2.sqlQuery;

import com.example.library2.dataSend.SocketData;
import com.example.library2.sever.MainSever;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.HadLike;

import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetConnect {
    public static List<SocketData> connects(Account account) throws SQLException {
        String pos = PosCheckSql.getPos(account);
        if (pos!= null && pos.equals("MANAGEMENT")) {
            List<SocketData> data = new ArrayList<>();
            HashMap<Socket, String> map = MainSever.getInstance().getConnecting();
            for (Socket socket : map.keySet()) {
                SocketData socketData = new SocketData(map.get(socket), socket.getInetAddress().toString(), socket.getPort());
                data.add(socketData);
            }
            return data;
        }
        return new ArrayList<>();
    }
}
