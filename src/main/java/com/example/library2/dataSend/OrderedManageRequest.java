package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class OrderedManageRequest extends Request {

    public Account account;

    public OrderedManageRequest() {
        super(RequestType.MANAGE_DATA_ORDER);
        account = Account.getAcc();
    }
}
