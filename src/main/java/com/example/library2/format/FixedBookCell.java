package com.example.library2.format;

import com.example.library2.entity.Book;

import java.util.Collection;

public class FixedBookCell extends BookCell{
    @Override
    protected void makeStart() {
    }

    @Override
    public void addALl(Collection<Book> collection) {
        clear();
        for (Book book : collection) {
            addBook(book);
        }
    }

    @Override
    public void next(int count) {
    }
}
