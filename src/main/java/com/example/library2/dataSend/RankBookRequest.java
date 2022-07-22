package com.example.library2.dataSend;

public class RankBookRequest extends Request{
    public RankBookRequest(RequestType type) {
        super(type);
    }

    public RankBookRequest() {
        super(RequestType.RANK_BOOK);
    }
}
