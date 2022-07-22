package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class CheckOrderRequest extends Request{
    public Account account;
    public int bookID;

    public CheckOrderRequest(int bookID) {
        super(RequestType.CHECK_ORDER);
        this.bookID = bookID;
        this.account = Account.getAcc();
    }

}
