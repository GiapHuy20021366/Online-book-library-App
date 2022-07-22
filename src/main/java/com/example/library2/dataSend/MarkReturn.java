package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class MarkReturn extends Request{
    public MarkReturn(int bookId, int accId) {
        super(RequestType.MARK_RETURN);
        this.accId = accId;
        this.bookId = bookId;
        this.account = Account.getAcc();
    }

    public Account account;
    public int bookId;
    public int accId;
}
