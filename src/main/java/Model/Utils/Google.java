package Model.Utils;

import java.io.InputStream;
import java.util.Properties;

public class Google {
    private static final Properties props = new Properties();

    static {
        try (InputStream is =
                     Google.class.getClassLoader()
                             .getResourceAsStream("gg.properties")) {

            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Không load được gg.properties", e);
        }
    }

    public static String getClientId() {
        return props.getProperty("google.client_id");
    }

    public static String getClientSecret() {
        return props.getProperty("google.client_secret");
    }

    public static String getRedirectUri() {
        return props.getProperty("google.redirect_uri");
    }
}
