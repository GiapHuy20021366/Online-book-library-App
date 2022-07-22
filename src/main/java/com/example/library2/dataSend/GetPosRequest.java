package com.example.library2.dataSend;

import com.example.library2.utilities.Account;

public class GetPosRequest extends Request{
    public Account account;

    public GetPosRequest() {
        super(RequestType.GET_POS);
        this.account = Account.getAcc();
    }
}
