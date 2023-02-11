package pl.trayz.cheats.utils;

import lombok.SneakyThrows;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Author: Trayz
 **/


public class WebsiteUtil {

    @SneakyThrows
    public static String getConfigFromWebsite(final String link) {
        URL crunchifyUrl = new URL(link);
        HttpURLConnection crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
        Map<String, List<String>> crunchifyHeader = crunchifyHttp.getHeaderFields();

        for (String header : crunchifyHeader.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                crunchifyUrl = new URL(crunchifyHeader.get("Location").get(0));
                crunchifyHttp = (HttpURLConnection) crunchifyUrl.openConnection();
                crunchifyHeader = crunchifyHttp.getHeaderFields();
            }
        }
        InputStream crunchifyStream = crunchifyHttp.getInputStream();
        String crunchifyResponse = crunchifyGetStringFromStream(crunchifyStream);
        return crunchifyResponse;
    }



    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents";
        }
    }
}
