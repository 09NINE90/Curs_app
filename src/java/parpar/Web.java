package parpar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public class Web {
    private static Document getPage() throws IOException {
        String url = "https://cbr.ru/";
        Document page = (Document) Jsoup.parse(new URL(url), 3000);
        return page;
    }
}
