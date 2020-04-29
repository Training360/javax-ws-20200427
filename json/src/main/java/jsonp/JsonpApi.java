package jsonp;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import java.io.Reader;
import java.util.ArrayList;

public class JsonpApi {

    public Catalog parse(Reader reader) {
        JsonParser parser = Json.createParser(reader);
        Catalog catalog = new Catalog();
        catalog.setBooks(new ArrayList<>());
        Book book = null;
        String prevName = null;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            if (event == JsonParser.Event.KEY_NAME) {
                String name = parser.getString();
                prevName = name;
                if (name.equals("isbn10")) {
                    book = new Book();
                    catalog.getBooks().add(book);
                }
            }
            if (event == JsonParser.Event.VALUE_STRING) {
                if (prevName.equals("isbn10")) {
                    book.setIsbn10(parser.getString());
                }
                if (prevName.equals("title")) {
                    book.setTitle(parser.getString());
                }
            }
        }
        return catalog;
    }

    public String write(Catalog catalog) {
        JsonArrayBuilder booksArrayBuilder = Json.createArrayBuilder();
        for (Book book: catalog.getBooks()) {
            JsonObject bookObject = Json.createObjectBuilder()
                    .add("isbn10", book.getIsbn10())
                    .add("title", book.getTitle())
                    .build();
            booksArrayBuilder.add(bookObject);
        }

        JsonObject rootObject = Json.createObjectBuilder()
                .add("books", booksArrayBuilder).build();
        return rootObject.toString();
    }
}
