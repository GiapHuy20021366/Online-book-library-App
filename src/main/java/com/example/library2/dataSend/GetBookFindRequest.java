package com.example.library2.dataSend;

public class GetBookFindRequest extends Request{
    public GetBookFindRequest(RequestType type) {
        super(type);
    }
    public String s;

    public GetBookFindRequest(String s) {
        super(RequestType.GET_BOOK_FIND);
        this.s = s;
    }


}
