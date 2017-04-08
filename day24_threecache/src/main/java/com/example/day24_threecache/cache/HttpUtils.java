package com.example.day24_threecache.cache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lenovo on 2017/4/7.
 */
public class HttpUtils {
    private static InputStream is;
    private static ByteArrayOutputStream baos;

    public static byte[] getBytesFromUrl(String string) throws IOException {
        URL url = new URL(string);

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();

                baos = new ByteArrayOutputStream();
                int len = 0;
                byte[] buf = new byte[1024 * 10];
                while (((len = is.read(buf)) != -1)) {
                    baos.write(buf, 0, len);
                }
                return baos.toByteArray();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return new byte[0];
    }
}
