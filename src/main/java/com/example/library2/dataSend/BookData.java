package com.example.library2.dataSend;

import java.io.Serializable;
import java.sql.Date;

public class BookData implements Serializable {
    public String name;
    public int id;
    public Date date;
    public String des;
    public String sourceImg;
    public int like = 0;

    public BookData(String name, int id, Date date, String des, String sourceImg) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.des = des;
        this.sourceImg = sourceImg;
    }

    public BookData(String name, int id, Date date, String des, String sourceImg, int like) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.des = des;
        this.sourceImg = sourceImg;
        this.like = like;
    }
}
