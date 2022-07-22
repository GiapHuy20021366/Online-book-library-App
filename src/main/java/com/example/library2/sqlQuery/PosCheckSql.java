package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;
import com.example.library2.utilities.Account;

import java.sql.*;
import java.util.ArrayList;

import static com.example.library2.sqlQuery.BorrowQuery.close;
import static com.example.library2.sqlQuery.LikeQuery.getIDAccount;

public class PosCheckSql {
    public static String getPos(Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            int AccId = getIDAccount(account);
            if (AccId == -1) {
                return null;
            }
            if (AccId == -2) {
                return null;
            }

            con = SqlTool.getConnect("library");

            String sql = "SELECT employee.Pos\n" +
                    "FROM employee\n" +
                    "WHERE employee.AccId = " + AccId;
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            close(statement, con);
        }
        return null;
    }
}
