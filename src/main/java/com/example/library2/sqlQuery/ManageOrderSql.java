package com.example.library2.sqlQuery;

import com.example.library2.dataSend.BookData;
import com.example.library2.dataSend.OrderData;
import com.example.library2.utilities.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.library2.sqlQuery.BorrowQuery.close;
import static com.example.library2.sqlQuery.LikeQuery.getIDAccount;
import static com.example.library2.sqlQuery.LikedBookQuery.getCountLike;

public class ManageOrderSql {
    public static List<OrderData> getAllPreOrder(Account account) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {

            if (PosCheckSql.getPos(account) == null) {
                return new ArrayList<>();
            }
            con = SqlTool.getConnect("library");

            String sql = "SELECT books.Name, books.BookId, customer.Name, customer.AccId, preorder.Time_Borrow\n" +
                    "FROM books INNER JOIN preorder ON preorder.Book_ID = books.BookId\n" +
                    "INNER JOIN account ON account.ID = preorder.Acc_ID\n" +
                    "INNER JOIN customer ON customer.AccId = account.ID";
            statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            List<OrderData> bookData = new ArrayList<>();
            while (resultSet.next()) {
                String nameB = resultSet.getString(1);
                int id = resultSet.getInt(2);
                String nameCus = resultSet.getString(3);
                int cusID = resultSet.getInt(4);
                Date order = resultSet.getDate(5);
                bookData.add(new OrderData(nameB, id, nameCus, cusID, order));
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
