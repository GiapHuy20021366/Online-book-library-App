package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class LikedBookRequest extends Request {
    public Account account;

    public LikedBookRequest() {
        super(RequestType.LIKED_BOOK_REQUEST);
        this.account = Account.getAcc();
    }
}
