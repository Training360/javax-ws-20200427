package gson;

import java.util.Arrays;
import java.util.List;

public class Catalog {

    private List<Book> books;

    public Catalog() {
    }

    public Catalog(Book... books) {
        this.books = Arrays.asList(books);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
