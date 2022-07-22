package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BorrowData;
import com.example.library2.dataSend.OrderData;
import com.example.library2.utilities.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.library2.sqlQuery.BorrowQuery.close;

public class ManageBorrowSql {
    public static List<BorrowData> getAllBorrowed(Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {

            if (PosCheckSql.getPos(account) == null) {
                return new ArrayList<>();
            }
            con = SqlTool.getConnect("library");

            String sql = "SELECT books.Name, books.BookId, customer.Name, customer.AccId, employee.Name, employee.AccId, borrows.Time_Order, borrows.Time_Borrow\n" +
                    "FROM borrows INNER JOIN books ON borrows.Book_ID = books.BookId\n" +
                    "INNER JOIN customer ON borrows.Acc_ID = customer.AccId\n" +
                    "INNER JOIN employee ON employee.AccId = borrows.Emp_Acc_Id;";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            List<BorrowData> bookData = new ArrayList<>();
            while (resultSet.next()) {
                String nameB = resultSet.getString(1);
                int idB = resultSet.getInt(2);
                String nameCus = resultSet.getString(3);
                int cusId = resultSet.getInt(4);
                String empName = resultSet.getString(5);
                int empId = resultSet.getInt(6);
                Date order = resultSet.getDate(7);
                Date borrow = resultSet.getDate(8);
                bookData.add(new BorrowData(nameB, idB, nameCus, cusId, order, borrow, empName, empId));
            }
            return bookData;
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        } finally {
            close(statement, con);
        }
        return null;
    }
}
