package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.MessageType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeQuery {
    public static MessageType like(int bookID, Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");
            int AccId = getIDAccount(account);
            if (AccId == -1) {
                return MessageType.FAIL_PASS;
            }
            if (AccId == -2) {
                return MessageType.ACCOUNT_NOT_EXIST;
            }


            String sql = "INSERT INTO likes VALUES(?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setInt(1, bookID);
            statement.setInt(2, AccId);
            statement.setDate(3, new Date(System.currentTimeMillis()));
            statement.execute();


        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return MessageType.SUCCESS;
    }

    public static MessageType dislike(int bookID, Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");
            int AccId = getIDAccount(account);
            if (AccId == -1) {
                return MessageType.FAIL_PASS;
            }
            if (AccId == -2) {
                return MessageType.ACCOUNT_NOT_EXIST;
            }

            String sql = "DELETE FROM likes\n" +
                    "WHERE likes.Book_ID = " + bookID + " AND likes.Acc_ID = " + AccId;
            statement = con.prepareStatement(sql);
            statement.execute();


        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return MessageType.SUCCESS;
    }

    public static int getIDAccount(Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * \n" +
                    "FROM account\n" +
                    "WHERE account.Name = '" + account.getName() + "'";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                if (!account.checkPass(resultSet.getString(2))) {
                    return -1;
                }
                return resultSet.getInt(3);
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return -2;
    }

    public static MessageType isLiked(int bookID, Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * \n" +
                    "FROM likes\n" +
                    "WHERE likes.Book_ID = " + bookID + " AND likes.Acc_ID = " + getIDAccount(account);

            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return MessageType.HAD_LIKE;
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return MessageType.UNEXPECTED_ERROR;
    }
}
