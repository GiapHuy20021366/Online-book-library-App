package com.example.library2.dataSend;

public class GetBookDataFindRequest extends Request{
    public GetBookDataFindRequest(RequestType type) {
        super(type);
    }

    public String s;
    public GetBookDataFindRequest(String s) {
        super(RequestType.GET_ALL_BOOK_DATA_FIND);
        this.s = s;
    }
}
