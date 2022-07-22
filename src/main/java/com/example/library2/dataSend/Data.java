package com.example.library2.dataSend;

import java.io.Serializable;

public class Data implements Serializable {
    public DataType type;
    public Object o;
    public Data(Object o, DataType type) {
        this.type = type;
        this.o = o;
    }
}
