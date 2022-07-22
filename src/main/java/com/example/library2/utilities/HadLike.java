package com.example.library2.utilities;

import com.example.library2.client.Client;
import com.example.library2.dataSend.BookData;
import com.example.library2.entity.Book;
import com.example.library2.format.BookCell;
import com.example.library2.format.FixedBookCell;
import com.example.library2.format.ViewBookCell;
import com.example.library2.transitionTool.MyTask;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class HadLike extends Pane {
    private static final HadLike r = new HadLike();
    private final FixedBookCell bookCell = new FixedBookCell();

    private HadLike() {
        ViewBookCell view = new ViewBookCell(bookCell);
        view.setSize(700);
        getChildren().add(view);
    }

    public void load() {
//        bookCell.clearAllCells();
        load(bookCell);
    }



    private void load(BookCell cells) {

        MyTask<List<Book>> myTask = new MyTask<List<Book>>() {
            @Override
            public void whatNext() {
                cells.addALl(this.getValue());
            }

            @Override
            protected List<Book> call() throws Exception {
                List<BookData> list = Client.getInstance().getLikedBookData();
                List<Book> books = new ArrayList<>();
                for (BookData data : list) {
                    books.add(new Book(data));
                }
                return books;
            }
        };
        myTask.execute();
    }

    public static HadLike getInstance() {
        return r;
    }
}
