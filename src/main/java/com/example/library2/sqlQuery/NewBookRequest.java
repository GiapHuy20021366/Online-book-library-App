package com.example.library2.sqlQuery;

import com.example.library2.dataSend.Request;
import com.example.library2.dataSend.RequestType;

public class NewBookRequest extends Request {
    public NewBookRequest(RequestType type) {
        super(type);
    }

    public NewBookRequest() {
        super(RequestType.NEW_BOOK_REQUEST);
    }
}
