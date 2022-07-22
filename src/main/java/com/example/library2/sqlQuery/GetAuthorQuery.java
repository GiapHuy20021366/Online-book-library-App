package com.example.library2.sqlQuery;

import com.example.library2.entity.Author;

import java.sql.*;

public class GetAuthorQuery {
    public static Author getAuthorById(int id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM authors WHERE authors.AuthorId = " + id;
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                String name = resultSet.getString(1);
                String des = resultSet.getString(3);
                return new Author(name, id, des);
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


    public static Author getAuthorByName(String name) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM authors WHERE authors.AuthorName LIKE '%" +name + "%'" ;
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                String nameAu = resultSet.getString(1);
                int id = resultSet.getInt(2);
                String des = resultSet.getString(3);
                return new Author(name, id, des);
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


}
