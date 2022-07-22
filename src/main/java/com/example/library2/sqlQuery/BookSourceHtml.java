package com.example.library2.sqlQuery;

import com.example.library2.entity.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSourceHtml {
    public static String getSourceHtml(int bookId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        String source = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM book_formathtml WHERE book_formathtml.Book_Id = " + bookId;
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                source = resultSet.getString(2);
            }
            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return source;
    }
}
