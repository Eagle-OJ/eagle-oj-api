package org.inlighting.oj.judge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private static final int TIMEOUT = 5000;


    public static void main(String[] args) {
        System.out.println(get("https://api.judge0.com/system_info"));
        System.out.println(post("https://api.judge0.com/submissions/?base64_encoded=false&wait=false"));
    }

    public static String get(final String URL) {
        return get(URL, null);
    }

    public static String get(final String URL, final String PARAM) {
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = null;
            if (PARAM==null)
                url = new URL(URL);
            else
                url = new URL(URL+"?"+PARAM);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.connect();
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            LOGGER.warn("Send get request occurred in: "+URL+"?"+PARAM+" "+e.getMessage());
        } finally {
            close(br);
        }
        return result.toString();
    }

    public static String post(final String URL) {
        return post(URL, null);
    }

    public static String post(final String URL, final String PARAM) {
        BufferedReader br = null;
        PrintWriter pw = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = null;
            if (PARAM==null)
                url = new URL(URL);
            else
                url = new URL(URL+"?"+PARAM);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            pw = new PrintWriter(urlConnection.getOutputStream());
            pw.print(PARAM);
            pw.flush();
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            LOGGER.warn("Send post request occurred in: "+URL+"?"+PARAM+" "+e.getMessage());
        } finally {
            close(br);
            close(pw);
        }
        return result.toString();
    }

    private static void close(Closeable closeable) {
        if (closeable!=null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOGGER.warn("Close error");
            }
        }
    }
}
