package com.example.library2.sqlQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlTool {
    public static Connection getConnect(String database) throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3307/" + database, "root", "");
    }
}

