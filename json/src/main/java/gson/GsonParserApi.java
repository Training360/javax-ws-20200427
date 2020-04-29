package gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Reader;
import java.util.ArrayList;

public class GsonParserApi {

    public Catalog parse(Reader reader) {
        JsonElement catalogElement = JsonParser.parseReader(reader);
        Gson gson = new Gson();
        Catalog catalog = new Catalog();
        catalog.setBooks(new ArrayList<>());
        JsonObject jsonObject = catalogElement.getAsJsonObject();
        JsonElement booksElement = jsonObject.get("books");
        for (JsonElement bookElement: booksElement.getAsJsonArray()) {
            Book book = gson.fromJson(bookElement, Book.class);
            catalog.getBooks().add(book);
        }
        return catalog;
    }
}
