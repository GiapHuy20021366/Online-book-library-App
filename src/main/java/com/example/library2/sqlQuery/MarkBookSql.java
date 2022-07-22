package com.example.library2.sqlQuery;

import com.example.library2.dataSend.MarkBorrowRequest;
import com.example.library2.dataSend.MarkReturn;
import com.example.library2.dataSend.OrderData;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.MessageType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.library2.sqlQuery.BorrowQuery.close;
import static com.example.library2.sqlQuery.LikeQuery.getIDAccount;

public class MarkBookSql {
    public static MessageType markAsBorrow(MarkBorrowRequest mark) {
        OrderData data = getOrderBy(mark.bookID, mark.accId);
        if (data == null) {
            return MessageType.UNEXPECTED_ERROR;
        }
        MessageType type = deleteFromOrder(mark);
        if (!type.equals(MessageType.SUCCESS)) {
            return type;
        }

        return insertToBorrow(data, mark.account);

    }

    public static MessageType markAsReturn(MarkReturn mark) {
        return deleteFromBorrow(mark);
    }

    public static MessageType deleteFromBorrow(MarkReturn mark) {
        Connection con = null;
        PreparedStatement statement = null;
        Account account = mark.account;
        try {
            con = SqlTool.getConnect("library");
            int AccId = getIDAccount(account);
            if (AccId == -1) {
                return MessageType.FAIL_PASS;
            }
            if (AccId == -2) {
                return MessageType.ACCOUNT_NOT_EXIST;
            }

            if (PosCheckSql.getPos(account) == null) {
                return MessageType.NOT_ENOUGH_POS;
            }

            String sql = "DELETE FROM borrows\n" +
                    "WHERE borrows.Book_ID = " + mark.bookId + " AND borrows.Acc_ID = " + mark.accId;
            statement = con.prepareStatement(sql);
            statement.execute();


        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            close(statement, con);
        }
        return MessageType.SUCCESS;
    }
    public static MessageType insertToBorrow(OrderData data, Account account) {
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

            String sql = "INSERT INTO borrows(Book_ID, Acc_ID, Time_Borrow, Emp_Acc_Id, Time_Order) VALUES(?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setInt(1, data.bookID);
            statement.setInt(2, data.cusID);
            statement.setDate(3, new Date(System.currentTimeMillis()));
            statement.setInt(4, AccId);
            statement.setDate(5, data.timeOrder);
            statement.execute();


        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            close(statement, con);
        }
        return MessageType.SUCCESS;
    }

    public static OrderData getOrderBy(int bookId, int accId) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT books.Name, books.BookId, customer.Name, customer.AccId, preorder.Time_Borrow\n" +
                    "FROM books INNER JOIN preorder ON preorder.Book_ID = books.BookId\n" +
                    "INNER JOIN account ON account.ID = preorder.Acc_ID\n" +
                    "INNER JOIN customer ON customer.AccId = account.ID\n" +
                    "WHERE preorder.Book_ID =  " + bookId + " AND preorder.Acc_ID = " + accId;
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String nameB = resultSet.getString(1);
                int id = resultSet.getInt(2);
                String nameCus = resultSet.getString(3);
                int cusID = resultSet.getInt(4);
                Date order = resultSet.getDate(5);
                return new OrderData(nameB, id, nameCus, cusID, order);
            }


        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            close(statement, con);
        }
        return null;
    }

    public static MessageType deleteFromOrder(MarkBorrowRequest mark) {
        Connection con = null;
        PreparedStatement statement = null;
        Account account = mark.account;
        try {
            con = SqlTool.getConnect("library");
            int AccId = getIDAccount(account);
            if (AccId == -1) {
                return MessageType.FAIL_PASS;
            }
            if (AccId == -2) {
                return MessageType.ACCOUNT_NOT_EXIST;
            }

            if (PosCheckSql.getPos(account) == null) {
                return MessageType.NOT_ENOUGH_POS;
            }

            String sql = "DELETE FROM preorder\n" +
                    "WHERE preorder.Book_ID = " + mark.bookID + " AND preorder.Acc_ID = " + mark.accId;
            statement = con.prepareStatement(sql);
            statement.execute();


        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            close(statement, con);
        }
        return MessageType.SUCCESS;
    }
}
