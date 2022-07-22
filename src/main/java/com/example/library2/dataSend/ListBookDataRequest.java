package com.example.library2.dataSend;

public class ListBookDataRequest extends Request{
    public ListBookDataRequest(RequestType type) {
        super(type);
    }

    public int fromId;
    public int toId;

    public ListBookDataRequest(int fromId, int toId) {
        super(RequestType.LIST_DATA_BOOK_REQUEST);
        this.fromId = fromId;
        this.toId = toId;
    }
}
