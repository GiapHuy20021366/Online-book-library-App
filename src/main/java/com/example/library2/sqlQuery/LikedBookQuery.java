package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;
import com.example.library2.utilities.Account;
import com.example.library2.utilities.MessageType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.library2.sqlQuery.LikeQuery.getIDAccount;


public class LikedBookQuery {
    public static List<BookData> getLikedBooks(Account account) throws SQLException {
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
                    "    SELECT likes.Book_ID\n" +
                    "    FROM likes\n" +
                    "    WHERE likes.Acc_ID = " + AccId + "\n" +
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
            statement.close();
            con.close();
        }
        return null;
    }

    public static int getCountLike(int bookID) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT COUNT(*)\n" +
                    "FROM likes\n" +
                    "WHERE likes.Book_ID = " + bookID + "\n" +
                    "GROUP BY likes.Book_ID";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return 0;
    }
}
