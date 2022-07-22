package com.example.library2.dataSend;

import java.io.Serializable;
import java.sql.Date;

public class OrderData implements Serializable {
    public String bookOrder = "";
    public int bookID = 0;
    public String customerOrder = "";
    public int cusID = 0;
    public Date timeOrder = null;


    public OrderData(String bookOrder, int bookID, String customerOrder, int cusID, Date timeOrder) {
        this.bookOrder = bookOrder;
        this.bookID = bookID;
        this.customerOrder = customerOrder;
        this.cusID = cusID;
        this.timeOrder = timeOrder;
    }

    public String getBookOrder() {
        return bookOrder;
    }

    public int getBookID() {
        return bookID;
    }

    public String getCustomerOrder() {
        return customerOrder;
    }

    public int getCusID() {
        return cusID;
    }

    public Date getTimeOrder() {
        return timeOrder;
    }
}
