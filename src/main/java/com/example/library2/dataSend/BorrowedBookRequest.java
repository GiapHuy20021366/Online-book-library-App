package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class BorrowedBookRequest extends Request{
    public Account account;

    public BorrowedBookRequest() {
        super(RequestType.BORROWED_BOOK_REQUEST);
        this.account = Account.getAcc();
    }
}
