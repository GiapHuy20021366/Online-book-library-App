package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class BorrowedManageRequest extends Request {
    public Account account;

    public BorrowedManageRequest() {
        super(RequestType.MANAGE_DATA_BORROWED);
        this.account = Account.getAcc();
    }
}
