package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;
import com.example.library2.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FindBookSql {
    public static List<String> getBookLike(String s) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            List<String> list = new ArrayList<>();
            con = SqlTool.getConnect("library");

            String sql = "SELECT Name FROM books WHERE books.Name LIKE '" + s +"%' LIMIT 10";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException troubles) {
//            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return null;
    }

    public static List<BookData> getAllBookAsFind(String s) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");
            String sql = "SELECT * FROM books WHERE books.Name LIKE '" + s.replace("'", "''") +"%' LIMIT 20";
            System.out.println(sql);
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
                int like = GetBookQuery.getCountLike(id);
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
}
