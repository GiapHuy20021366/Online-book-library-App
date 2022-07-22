package com.example.library2.format;

import com.example.library2.client.Client;
import com.example.library2.dataSend.BookData;
import com.example.library2.entity.Book;
import com.example.library2.transitionTool.MyTask;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class BookCell extends AnchorPane {
    private final List<BookFrame> bookFrames = new ArrayList<>();
    private final DoubleProperty paddingRow = new SimpleDoubleProperty(10);
    private final DoubleProperty paddingCol = new SimpleDoubleProperty(20);
    private static final IntegerProperty column = new SimpleIntegerProperty(5);
    private final ImageView maintainSize = new ImageView();
    private final IntegerProperty maxId = new SimpleIntegerProperty(0);
    private final IntegerProperty countWait = new SimpleIntegerProperty(0);

    private final Queue<Book> wait = new ArrayDeque<>();
    private final IntegerProperty sizeWait = new SimpleIntegerProperty(1);
    private final HashMap<Integer, AnchorPane> cells = new HashMap<>();

    public BookCell() {

        setSize(8 * paddingRow.get() + 6 * 170, 700);
        getChildren().add(maintainSize);
        makeStart();

        setStyle("-fx-focus-color: transparent;\n" +
                "-fx-faint-focus-color: transparent;");
    }

    public void clear() {
        clearAllCells();
        bookFrames.clear();
        cells.clear();
        sizeWait.set(0);
        wait.clear();
        countWait.set(0);
        maxId.set(0);
    }

    protected void makeStart() {
        sizeWait.addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < 30) {
                if (maxId.get() < 120) {
                    if (maxId.get() < 20) {
                        preDownload(5 - Math.max(newValue.intValue(), 0));
                    } else {
                        preDownload(30 - Math.max(newValue.intValue(), 0));
                    }
                }
            }
        });
    }

    private void preDownload(int count) {
        if (Math.abs(count) > 30 || count < 1) return;
        MyTask<List<Book>> myTask = new MyTask<>() {
            @Override
            public void whatNext() {
                addALl(this.getValue());
            }

            @Override
            protected List<Book> call() {
                List<Book> books = new ArrayList<>();

                List<BookData> list = Client.getInstance().getListBookData(maxId.get(), maxId.get() + count - 1);
                for (BookData data : list) {
                    books.add(new Book(data));
                }
                return books;
            }
        };
        myTask.execute();
    }

    public void addALl(Collection<Book> collection) {
        if (collection.size() > (30 - wait.size())) {
            return;
        }
        wait.addAll(collection);
        sizeWait.set(wait.size());
        this.maxId.set(maxId.get() + collection.size());
        if (countWait.get() > 0) {
            int count = countWait.get();
            countWait.set(0);
            next(count);
        }
    }

    public void sortByName() {
        sortByName(true);
    }

    public void sortByName(boolean increase) {
        bookFrames.sort(new Comparator<BookFrame>() {
            @Override
            public int compare(BookFrame o1, BookFrame o2) {
                int x = o1.getBook().getName().compareTo(o2.getBook().getName());
                return (increase) ? x : -x;
            }
        });

        resetCells();
    }

    public void sortByNameASC() {
        sortByName();
    }

    public void sortByNameDESC() {
        sortByName(false);
    }

    public void sortByDateASC() {
        sortByDate(true);
    }

    public void sortByDateDESC() {
        sortByDate(false);
    }

    private void sortByDate(boolean increase) {
        bookFrames.sort(new Comparator<BookFrame>() {
            @Override
            public int compare(BookFrame o1, BookFrame o2) {
                Date date1 = o1.getBook().getDate();
                Date date2 = o2.getBook().getDate();
                if (date1 == null || date2 == null) {
                    return 0;
                }
                int x = date1.compareTo(date2);
                return increase ? x : -x;
            }
        });
        resetCells();
    }

    private void resetCells() {
        clearAllCells();
        int i = 0;
        for (BookFrame frame : bookFrames) {
            setCells(i++, frame);
        }
    }

    public void setSize(double wid, double hei) {
        maintainSize.setFitWidth(wid);
        maintainSize.setFitHeight(hei);
    }

    private void setCells(int pos, BookFrame frame) {
        cells.get(pos).getChildren().add(frame);
    }

    private void createCell(int pos) {
        if (pos >= cells.size()) {
            int col = pos % column.get() + 1;
            int row = pos / column.get() + 1;
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setLayoutX(col * paddingRow.get() + (col - 1) * 170);
            anchorPane.setLayoutY(row * paddingCol.get() + (row - 1) * 310);
            cells.put(pos, anchorPane);
            getChildren().add(anchorPane);
        }
    }

    public void addBook(Book book) {
        BookFrame bookFrame = new BookFrame(book);
        createCell(bookFrames.size());
        cells.get(bookFrames.size()).getChildren().add(bookFrame);
        bookFrames.add(bookFrame);
    }

    private void makeEmptyCell(int pos) {
        AnchorPane pane = cells.get(pos);
        if (pane != null) {
            pane.getChildren().clear();
        }
    }

    public void clearAllCells() {
        for (int i : cells.keySet()) {
            cells.get(i).getChildren().clear();
        }
    }

    public void next(int count) {
        if (countWait.get() > 0) return;
        int realCanLoad = Math.min(count, wait.size());
        countWait.set(Math.abs(count - realCanLoad));
        if (maxId.get() < 18) {
            countWait.set(2);
        }
        while (realCanLoad-- > 0) {
            addBook(wait.poll());
        }
        sizeWait.set(wait.size());
    }

}
