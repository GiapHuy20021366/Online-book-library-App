package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetRankBook {
    public static List<BookData> getRank() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT books.Name, books.BookId, books.Releasedate, books.Description, books.SourceImg, COUNT(*) As total\n" +
                    "FROM books INNER JOIN likes ON books.BookId = likes.Book_ID\n" +
                    "GROUP BY books.BookId\n" +
                    "ORDER BY total DESC";
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
                int like = resultSet.getInt(6);
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
