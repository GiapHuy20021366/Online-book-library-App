package com.example.library2.sqlQuery;

import com.example.library2.dataSend.RegisNewAccountRequest;
import com.example.library2.dataSend.RequestType;
import com.example.library2.utilities.MessageType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckNewAccount {
    public static MessageType regisNewAcc(RegisNewAccountRequest newAcc) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "INSERT INTO account(Name, Pass) VALUES(?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, newAcc.acc);
            statement.setString(2, newAcc.pass);
            statement.execute();

            insertAccToCustomerTable(newAcc);

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

    private static MessageType insertAccToCustomerTable(RegisNewAccountRequest newAcc) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            int newID = getAccId(newAcc.acc);
            if (newID == - 1) {
                return MessageType.UNEXPECTED_ERROR;
            }
            con = SqlTool.getConnect("library");
            String sqlCus = "INSERT INTO customer(Name, Phone, Email, AccId) VALUES(?, ?, ?, ?)";
            statement = con.prepareStatement(sqlCus);
            statement.setString(1, newAcc.acc);
            statement.setString(2, newAcc.phone);
            statement.setString(3, newAcc.email);
            statement.setInt(4, newID);
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

    public static int getAccId(String name) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT account.ID FROM account WHERE account.Name = '" + name.trim() +"'";
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
        return -1;
    }

    public static MessageType checkEmail(String email) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM customer WHERE customer.Email = '" + email.trim() +"'";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return MessageType.EMAIL_EXIST;
            }
            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return MessageType.SUCCESS;
    }

    public static MessageType checkPhone(String phone) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = SqlTool.getConnect("library");

            String sql = "SELECT * FROM customer WHERE customer.Phone = '" + phone.trim() +"'";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return MessageType.PHONE_EXIST;
            }
            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            statement.close();
            con.close();
        }
        return MessageType.SUCCESS;
    }
}
