package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;
import com.example.library2.utilities.MessageType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginQuery {
    public static MessageType checkAcc(String name, String pass) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM account WHERE account.Name = '" + name.trim() + "'";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            String realPass = null;
            if (resultSet.next()) {
                realPass = resultSet.getString(2);
            }
            if (realPass == null) {
                return MessageType.ERROR_NAME;
            }
            if (!pass.trim().equals(realPass)) {
                return MessageType.ERROR_PASSWORD;
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return MessageType.SUCCESS;
    }
}
