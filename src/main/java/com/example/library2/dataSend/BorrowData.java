package com.example.library2.dataSend;

import java.io.Serializable;
import java.sql.Date;

public class BorrowData implements Serializable {
    public String bookBorrow = "";
    public int bookID = 0;
    public String customerBorrow = "";
    public int cusID = 0;
    public Date timeOrder = null;
    public Date timeBorrow = null;
    public String employeeAccept = "";
    public int empAccId = 0;

    public BorrowData(String bookBorrow, int bookID, String customerBorrow, int cusID, Date timeOrder, Date timeBorrow, String employeeAccept, int empAccId) {
        this.bookBorrow = bookBorrow;
        this.bookID = bookID;
        this.customerBorrow = customerBorrow;
        this.cusID = cusID;
        this.timeOrder = timeOrder;
        this.timeBorrow = timeBorrow;
        this.employeeAccept = employeeAccept;
        this.empAccId = empAccId;
    }

    public String getBookBorrow() {
        return bookBorrow;
    }

    public int getBookID() {
        return bookID;
    }

    public String getCustomerBorrow() {
        return customerBorrow;
    }

    public int getCusID() {
        return cusID;
    }

    public Date getTimeOrder() {
        return timeOrder;
    }

    public Date getTimeBorrow() {
        return timeBorrow;
    }

    public String getEmployeeAccept() {
        return employeeAccept;
    }

    public int getEmpAccId() {
        return empAccId;
    }
}
