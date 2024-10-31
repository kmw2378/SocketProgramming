import java.net.MalformedURLException;
import java.net.URL;

public class URLParser {
    private final String host;
    private final int port;
    private final String path;

    public URLParser(String urlString) throws MalformedURLException {
        URL url = new URL(urlString);
        this.host = url.getHost();
        this.port = (url.getPort() == -1) ? url.getDefaultPort() : url.getPort();
        this.path = url.getPath().isEmpty() ? "/" : url.getPath();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }
}