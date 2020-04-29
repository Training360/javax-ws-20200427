package orgjson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class OrgJsonApi {

    public Catalog parse(Reader reader) {
        Catalog catalog = new Catalog();
        catalog.setBooks(new ArrayList<>());
        JSONTokener tokener = new JSONTokener(reader);
        JSONObject root = new JSONObject(tokener);
        JSONArray bookArray = (JSONArray) root.get("books");
        for (int i = 0; i < bookArray.length(); i++) {
            JSONObject bookObject = (JSONObject) bookArray.get(i);
            String isbn10 = (String) bookObject.get("isbn10");
            String title = (String) bookObject.get("title");
            Book book = new Book(isbn10, title);
            catalog.getBooks().add(book);
        }
        return catalog;
    }

    public void write(Catalog catalog, Writer writer) {
        JSONObject catalogObject = new JSONObject();
        JSONArray booksObject = new JSONArray();
        catalogObject.put("books", booksObject);
        for (Book book: catalog.getBooks()) {
            JSONObject bookObject = new JSONObject();
            bookObject.put("isbn10", book.getIsbn10());
            bookObject.put("title", book.getTitle());
            booksObject.put(bookObject);
        }

        try {
            writer.write(catalogObject.toString());
        }
        catch (IOException ioe) {
            throw new IllegalStateException("Can not write JSON", ioe);
        }
    }
}
