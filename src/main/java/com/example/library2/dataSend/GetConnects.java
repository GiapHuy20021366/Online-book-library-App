package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class GetConnects extends Request{
    public Account account;

    public GetConnects() {
        super(RequestType.GET_CONNECTS);
        account = Account.getAcc();
    }
}
