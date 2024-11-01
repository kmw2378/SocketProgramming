import java.net.MalformedURLException;
import java.net.URL;

public class URLParser {
    private final URL url;

    public URLParser(String urlString) throws MalformedURLException {
        this.url = new URL(urlString);
    }

    public String getHost() {
        return url.getHost();
    }

    public int getPort() {
        return (url.getPort() == -1) ? url.getDefaultPort() : url.getPort();
    }

    public String getPath() {
        return url.getPath().isEmpty() ? "/" : url.getPath() + "?" + url.getQuery();
    }
}