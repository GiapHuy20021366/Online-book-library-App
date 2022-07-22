package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;
import com.example.library2.dataSend.Data;
import com.example.library2.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetBookQuery {
    public static BookData getDataBookById(int id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM books WHERE books.BookId = " + id;
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                String nameB = resultSet.getString(1);
                Date date = resultSet.getDate(3);
                String des = resultSet.getString(4);
                String desHtml = BookSourceHtml.getSourceHtml(id);
                if (desHtml != null) {
                    des = desHtml;
                }
                String sourceImg = resultSet.getString(5);
                int like = getCountLike(id);
                return new BookData(nameB, id, date, des, sourceImg, like);
            }
            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return null;
    }


    public static List<BookData> getDataBookById(int fromId, int toId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM books WHERE books.BookId BETWEEN " + fromId + " AND " + toId;
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

    public static List<String> getAllAuthorsNameByBookId(int id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<String> res = new ArrayList<>();
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT authors.AuthorName, authors.AuthorId\n" +
                    "FROM authors\n" +
                    "WHERE authors.AuthorId IN (\n" +
                    "    SELECT book_author.AuthorId\n" +
                    "    FROM book_author\n" +
                    "    WHERE book_author.BookId = " + id + "\n" +
                    "    )";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                res.add(resultSet.getString(1));
            }

            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return res;
    }

    public static List<Integer> getAllAuthorsByBookId(int id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<Integer> res = new ArrayList<>();
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM book_author WHERE book_author.BookId = " + id;
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                res.add(resultSet.getInt(2));
            }

            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return res;
    }

    public static int getCountLike(int bookID) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<Integer> res = new ArrayList<>();
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT COUNT(*)\n" +
                    "FROM likes\n" +
                    "WHERE likes.Book_ID = " + bookID;
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return 0;
    }
}
