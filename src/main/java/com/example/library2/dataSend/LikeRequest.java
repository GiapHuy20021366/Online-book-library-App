package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class LikeRequest extends Request {
    public LikeRequest(RequestType type) {
        super(type);
    }

    public Account account;
    public int bookID;

    public LikeRequest(int bookID) {
        super(RequestType.LIKE_REQUEST);
        this.bookID = bookID;
        this.account = Account.getAcc();
    }

    public LikeRequest(RequestType type, int bookID) {
        super(type);
        this.bookID = bookID;
        this.account = Account.getInstance();
        this.account = Account.getAcc();
    }
}
