package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class DisLikeRequest extends Request {
    public DisLikeRequest(RequestType type) {
        super(type);
    }

    public Account account = Account.getInstance();
    public int bookID;

    public DisLikeRequest(int book_ID) {
        super(RequestType.DISLIKE_REQUEST);
        this.bookID = book_ID;
    }

}
