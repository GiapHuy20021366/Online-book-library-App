package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.MessageType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.library2.sqlQuery.LikeQuery.getIDAccount;
import static com.example.library2.sqlQuery.LikedBookQuery.getCountLike;

public class BorrowQuery {

    public static MessageType isBorrowed(int bookID, Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            int id = getIDAccount(account);
            String sql = "SELECT * \n" +
                    "FROM borrows\n" +
                    "WHERE borrows.Book_ID = " + bookID;

            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                if (resultSet.getInt(2) != id) {
                    return MessageType.BORROWED_BY_OTHER;
                }
                return MessageType.HAD_BORROWED;
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
            return MessageType.UNEXPECTED_ERROR;
        } finally {
            close(statement, con);
        }
        return MessageType.NOT_BORROW_YET;
    }

    public static MessageType isOrdered(int bookID, Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            MessageType isBorrow = isBorrowed(bookID, account);
            System.out.println(isBorrow);
            if (!isBorrow.equals(MessageType.NOT_BORROW_YET)) {
                return isBorrow;
            }

            con = SqlTool.getConnect("library");

            int id = getIDAccount(account);
            String sql = "SELECT * \n" +
                    "FROM preorder\n" +
                    "WHERE preorder.Book_ID = " + bookID;

            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                if (resultSet.getInt(2) != id) {
                    return MessageType.ORDERED_BY_ANOTHER;
                }
                return MessageType.HAD_ORDERED;
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
            return MessageType.UNEXPECTED_ERROR;
        } finally {
            close(statement, con);
        }
        return MessageType.NOT_ORDER_YET;
    }

    public static MessageType order(int bookID, Account account) throws SQLException {
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

            if (isOrdered(bookID, account) == MessageType.ORDERED_BY_ANOTHER) {
                return MessageType.ORDERED_BY_ANOTHER;
            }

            String sql = "INSERT INTO preorder(Book_ID, Acc_ID, Time_Borrow) VALUES(?, ?, ?)";
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


    public static MessageType removeOrder(int bookID, Account account) throws SQLException {
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

            String sql = "DELETE FROM preorder \n" +
                    "WHERE preorder.Book_ID = " + bookID + " AND preorder.Acc_ID = " + AccId;
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


    public static MessageType removeBorrowed(int bookID, Account account) throws SQLException {
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

            String sql = "DELETE FROM borrows \n" +
                    "WHERE borrows.Book_ID = " + bookID + " AND borrows.Acc_ID = " + AccId;
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

    public static List<BookData> getBorrowedBooks(Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {

            int AccId = getIDAccount(account);
            if (AccId == -1) {
                return new ArrayList<>();
            }
            if (AccId == -2) {
                return new ArrayList<>();
            }
            con = SqlTool.getConnect("library");

            String sql = "SELECT * \n" +
                    "FROM books\n" +
                    "WHERE books.BookId IN (\n" +
                    "    SELECT borrows.Book_ID\n" +
                    "    FROM borrows\n" +
                    "    WHERE borrows.Acc_ID = " + AccId + "\n" +
                    "    )\n" +
                    "LIMIT 20";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            List<BookData> bookData = new ArrayList<>();
            while (resultSet.next()) {
                String nameB = resultSet.getString(1);
                int id = resultSet.getInt(2);
                Date date = resultSet.getDate(3);
                String des = resultSet.getString(4);
                String desHtml = BookSourceHtml.getSourceHtml(id);

                if (desHtml != null) {
                    des = desHtml;
                }
                String sourceImg = resultSet.getString(5);
                int like = getCountLike(id);
                bookData.add(new BookData(nameB, id, date, des, sourceImg, like));
            }
            return bookData;
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            close(statement, con);
        }
        return null;
    }

    public static List<BookData> getOrderedBooks(Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {

            int AccId = getIDAccount(account);
            if (AccId == -1) {
                return new ArrayList<>();
            }
            if (AccId == -2) {
                return new ArrayList<>();
            }
            con = SqlTool.getConnect("library");

            String sql = "SELECT * \n" +
                    "FROM books\n" +
                    "WHERE books.BookId IN (\n" +
                    "    SELECT preorder.Book_ID\n" +
                    "    FROM preorder\n" +
                    "    WHERE preorder.Acc_ID = " + AccId + "\n" +
                    "    )\n" +
                    "LIMIT 20";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            List<BookData> bookData = new ArrayList<>();
            while (resultSet.next()) {
                String nameB = resultSet.getString(1);
                int id = resultSet.getInt(2);
                Date date = resultSet.getDate(3);
                String des = resultSet.getString(4);
                String desHtml = BookSourceHtml.getSourceHtml(id);

                if (desHtml != null) {
                    des = desHtml;
                }
                String sourceImg = resultSet.getString(5);
                int like = getCountLike(id);
                bookData.add(new BookData(nameB, id, date, des, sourceImg, like));
            }
            return bookData;
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            close(statement, con);
        }
        return null;
    }

    public static void close(Statement statement, Connection connection) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }

}
