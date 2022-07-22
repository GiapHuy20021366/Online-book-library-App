package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class RemoveOrderRequest extends Request{
    public Account account;
    public int bookID;

    public RemoveOrderRequest(int bookID) {
        super(RequestType.REMOVE_ORDER);
        this.bookID = bookID;
        this.account = Account.getAcc();
    }
}
