package com.example.library2.entity;

import com.example.library2.dataSend.BookData;
import com.example.library2.sqlQuery.BookSourceHtml;
import com.example.library2.sqlQuery.GetAuthorQuery;
import com.example.library2.sqlQuery.GetBookQuery;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class Book implements Serializable {
    private final StringProperty imgSource = new SimpleStringProperty("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAflBMVEX///8AAADt7e3u7u7r6+v4+Pjz8/Py8vIvLy8yMjKamprY2Njm5ubGxsaoqKhhYWE6Ojq7u7vU1NSJiYl1dXWysrJwcHBqamoVFRVDQ0MnJyeqqqrf39/Pz8+bm5vIyMhdXV19fX1MTEySkpJVVVUeHh6CgoIREREbGxs+Pj7qec82AAAJD0lEQVR4nO2d2XriuhKFbYwkgg3EBEgzY8jE+7/gtoEccrqTUEvWkux8qZuum5bqj4VraSpHUWWdJEk6P9SLGhLHL+Ev4U3C0ro/1Du5Z9iuB89vb2cv8TBQjNFan7zyX21OnlLvHneoJlxCc2JIBtP+QzGaL17jd3tdHHeTh02emoratJRQKz2e9kdv8fd299jPM0UkvP4ku868qn21fZjfYPtoj8NVOZoTTixOGzx728M9QPduu6kmxBI5GQofB0WUPljQXex+mCjd5Ixv1GrzbM93smMJ6fIl65LQqO2oJt7ZJmv3hE7UQ27z4/vc9ltnUblSGYnKe874Kuvlqn5UDjWNSvdO+Sq7T+tG5TLjF875KhvpphCOKXyVDWvH50TTvNAAq6Gqg2ua1YIIWNqyZnxRzQEarbl8pY3CZvwlHTCO3zo6HGENCYrYQIXRNJ1o4gcwjqcqjKZ59AVYIgbRNIU/wArRf8bv+wSM41z5Jsz9ApavG6upsb2mWfkGjOPUeNU0/gHjP1aRRjYDtLSnAITxzmPGn4YArDSqL0ITBjCOu/iL8UIIKgX3E3qhzZUXTWMCjdHKplCklpomCTZGK0N/VDYZX3uaUHxuDx4Is5CAcQyuTtloGm9Tps/tha1pksCPMI6zDhRzBA3Q0tPMlTWRHTQUM0wY9EV6Ni6h3oTmK2eKNoRSpZAo8uqoxEZQzKimSUPjVZYwNU3w90xlU0PM+LUi6z2NZkVRvMx2yDGNf20GLJ+ihAPLmI4P06xqpToTdX4VmjQ/7GwRIUJM01jpmadlpi4nvK7tdRJTsqaH19v//19LE5amsUmGs1R92V7HaDWwON0wBWKO5D/B0vBBuk+V+b5lE2XwaH0BYsYID2gouRK99rZgsz0a4R0WyCIRtwyui+CEQn2AhTEHWlbYLk+Xo2kMtpB/rI4aylquPAgxNxRNo6Gf4XMCKI/KQ05UDTUl4ytoobtrwPWRBGj8QCJEAC32+4DtuglAKNc0CbLdtLfZRJE3v9MUTYPk+3GCb4QBy5TAHzAChtFQDjhS2AA9qxv56bF7TsYv5IRjuwsG4vZ7HMKjOIC55fkXccJYcDSNGDBeGpvzOV151l9QNA2Qr0Tt/esZ8fSzpwiaBngP7C0GaGV6Ju3hqAgZH1ClB1tC8URxzyDU8mQxsCSUL8aOAEKxpjHy1e5M0t4nnnyRZELRNPKZhQVc5SXy5eYNRdOIX3R7iwFaecCeyJKS8cVrYhNLQiWfIQ4ohOKllIMdoQH2RFYUTSP+Cw/tzhwje69Ay4CmUeq8tKvUDQ9YQ/k/Tw64UA27yy3zgL3XiRa3TL7LjXjIGsmylYTIzmQmn35y7nLbeMghljslbxnRNFQPyIXVyxpoORI+bLYHHfzXSMsNIYQOWj1BfTSEEDrDQj1PQ/LEc/vKegrqI0jFmL888JzVAOwjfD7U2N45sBjclIwPHrNKW0cI7WjF8QzsowGaBjwKiPYRWtOYLgiYG7SPSPiwOZ7JQMAJfgkxLOHqDwa4sOgjKCF8h7FjTxhEycBnyNY2vQXTNB0FX73ZIKszVy9UPkzgO5ovVtuuwTJ+Br5jrF6jIQnxi+CPlr0F0jQFDDiz7S2IprGoaPOorHuL4Mde27M4DV/Ur23ikbDAAft2L5kwhMaiap1t0Y8QmkbZ1FyyUjKhNI1NOZSxrtmvz3xocbPimIEnjUNmfG1RWHESoSeNwxFqm/vD0/r9etM04JLhyRaZgwi8aRoLwImB12QCahqLkksDRxH4IcSf4E65isAHocarf07dRXAhZCoZ5CTQ2Y4rhxH40DS36s7/bRu3EfDzIahkTjnCXQQeMj5YkGjmOAI+Ibi1lLv+SgJf00DXCu9W4rO/jdE00A3fGSOCCH7smIcUT+hTIiATIqnQ+U/QCyFw034tvjTZJE3TkQOODSWCiKtpgCuTGScCtqZR4rIeLj/Z4THjG3GqyLHiVo0hFA/Sg/gWU8M0jRLuET7Zb7sE1jTSNykvgqtx8qFwVjHgRcAmlF0FmxMjYBPKfoa21zHDa5qObGYInvhtlKaRrZFOeetDdE0jO9usiAOUnfFFL5on5k+QTSgqMjHkE/I0jWiQrpjJnqtpEpmi0UQ4sqaRFZmY29SxaUjGlxWZeJFflWwcoazIxNAboXtFYUQnSwas1RkfmkY0/TXu+/WnaUT30Qj9+sv4klPOr60mlFQCnfsgpGkayUriI6Ffb5pGNDssan6CM6ymkRA+OP4Gt9eML/qoV7/NhKKDekN/hM4VhawU6NSyNh/i0TSN6Blu3ffrT9OInuHYfb/+Mn761qvsrrKvvNeszYSCakuKs3HvTdM0xONpmsZ4Z+MOlLDeL2H7vQshTVGE9/zeewri8fJhM7wGVG/5JaxNeP1Jhk/Ov5rGyjubjyFjSku89eY345+4Ots8H6zVTyQ0Wm8P1xvco2FXeVid8adpErUq/p7Z3w+1/jGaRn/xMYBphH2Hso7HzIdGfbmJeDeuUQiiMRnfdL+7bbHhb+GzCW/dO5wRTwZ/JORpmpuHMXZff/qxDZrGCBZMJ6rNmsY83yY8lWVpa8ZXspuja7vvQjWAUPpRmufWnqfR0tsyfbq6IWka+eXftmoa+XeMl63M+AYolvTcSkJdyAnjVd0aOyE0DXRDfWNaqGmgyqt7GhxR02BlFBgRsDM+9vlu8toNhbCACFdeCN3qCOwb22tCBGRNI/+M4cmqIuQt0zTCecW7DWpW1QuQ8bX4K3snS1tIiL1LGaqKrGkMlA//cJcyOJpG/rXN0kY0OOY6DVKse0iJgJzxZbdJLrZqJSFQZG/PPr53IXSsI5S8eFKeUCLgaprSE0/ye21dpxHdCapszRygtIxfecKqHyMSlwfCaCkiVB4ICZpGPoVKCf160TQnT9/+QnKuqXBMTXMeHrdSRr16+WEz/sX7fp4IfFu7sYTfFf9wXIjVt6b5n5d9dZP0hduvv/M0SZR/9smVSUJXMlePlQ+v3vqvQ0NvfU3szV/G/+iZ/DA6P8pjMUx93AXyTdgx+v22k9bGB9dHQpqmaYh3eaE2I5i2aZqmeL+E7ffOdn2h/jzvP1p3IcyzGjFqAAAAAElFTkSuQmCC");
    private final StringProperty name = new SimpleStringProperty("Book Title");
    private final IntegerProperty id = new SimpleIntegerProperty(99999);
    private final StringProperty description = new SimpleStringProperty("A example of a book");
    private final StringProperty rate = new SimpleStringProperty("Rate: " + 0.5);
    private String sourceHtml;
    private final Image image;
    private final Date date;
    private final int like;

    public int getLike() {
        return like;
    }

    private final HashMap<String, Author> authors = new HashMap<>();

    public Image getImage() {
        return image;
    }

    private Book(String name, int id, Date date, String des, String sourceImg, int like) {
        this.name.set(name);
        this.id.set(id);
        this.date = date;
        this.description.set(des);
        this.imgSource.set(sourceImg);
        this.image = new Image(this.imgSource.get());
        this.like = like;

        getAllAuthors(getId());
    }

    public Book(BookData bookData) {
        this(bookData.name, bookData.id, bookData.date, bookData.des, bookData.sourceImg, bookData.like);
    }

    private void getAllAuthors(int bookId){
        try {
            for (int i : GetBookQuery.getAllAuthorsByBookId(bookId)) {
                Author author = GetAuthorQuery.getAuthorById(i);
                if (author != null) {
                    authors.put(author.getName(), author);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getImgSource() {
        return imgSource.get();
    }

    public StringProperty imgSourceProperty() {
        return imgSource;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getRate() {
        return rate.get();
    }

    public StringProperty rateProperty() {
        return rate;
    }

    public Date getDate() {
        return date;
    }

    public Set<String> getAuthors() {
        return authors.keySet();
    }

    public String getSourceHtml() {
        return sourceHtml;
    }

    public boolean getSourceBySql() {
        try {
            sourceHtml = BookSourceHtml.getSourceHtml(id.get());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashMap<String, Author> getAllAuthors() {
        return authors;
    }

    public String getFullDesAuthors() {
        StringBuilder builder = new StringBuilder();
        for (String s : authors.keySet()) {
            Author author = authors.get(s);
            if (author.getDescription() != null) {
                builder.append(author.getDescription()).append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Book{" +
                "imgSource=" + imgSource +
                ", name=" + name +
                ", id=" + id +
                ", description=" + description +
                ", rate=" + rate +
                ", date=" + date +
                ", authors=" + authors +
                '}';
    }
}
