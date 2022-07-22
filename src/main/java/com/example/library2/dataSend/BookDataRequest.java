package com.example.library2.dataSend;

public class BookDataRequest extends Request{
    public BookDataRequest(RequestType type) {
        super(type);
    }
    public int id;

    public BookDataRequest(int id) {
        super(RequestType.BOOK_DATA_REQUEST);
        this.id = id;
    }
}
