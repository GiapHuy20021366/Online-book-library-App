package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class OrderRequest extends Request{
    public Account account;
    public int bookID;

    public OrderRequest(int bookID) {
        super(RequestType.ORDER_BOOK);
        this.bookID = bookID;
        this.account = Account.getAcc();
    }
}
