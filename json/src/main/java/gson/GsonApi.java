package gson;

import com.google.gson.Gson;

import java.io.Reader;

public class GsonApi {

    public Catalog parse(Reader reader) {
        Gson gson = new Gson();
        return gson.fromJson(reader, Catalog.class);
    }

    public String write(Catalog catalog) {
        Gson gson = new Gson();
        return gson.toJson(catalog);
    }

}
