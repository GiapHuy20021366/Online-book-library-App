package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class OrderedBookRequest extends Request {
    public Account account;

    public OrderedBookRequest() {
        super(RequestType.ORDERED_BOOK_REQUEST);
        this.account = Account.getAcc();
    }
}
