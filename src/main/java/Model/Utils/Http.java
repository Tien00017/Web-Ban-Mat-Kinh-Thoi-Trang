package Model.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
    // POST request
    public static String post(String urlStr, String body) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            return readResponse(conn);

        } catch (Exception e) {
            throw new RuntimeException("POST request failed", e);
        }
    }

    // GET request
    public static String get(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            return readResponse(conn);

        } catch (Exception e) {
            throw new RuntimeException("GET request failed", e);
        }
    }

    private static String readResponse(HttpURLConnection conn) throws Exception {
        BufferedReader reader;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        reader.close();
        return sb.toString();
    }
}
