package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class MarkBorrowRequest extends Request{

    public Account account;
    public int bookID;
    public int accId;

    public MarkBorrowRequest(int bookID, int accId) {
        super(RequestType.MARK_BORROW);
        this.bookID = bookID;
        this.accId = accId;
        this.account = Account.getAcc();
    }
}
