package org.inlighting.oj.judge.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private static final int TIMEOUT = 5000;


    static String get(final String URL) {
        return get(URL, null);
    }

    static String get(final String URL, final String PARAM) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            URL url = null;
            if (PARAM==null)
                url = new URL(URL);
            else
                url = new URL(URL+"?"+PARAM);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.setReadTimeout(TIMEOUT);
            urlConnection.connect();
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            LOGGER.warn("Send get request occurred in: "+URL+"?"+PARAM+" "+e.getMessage());
        } finally {
            close(br);
        }
        return null;
    }

    static String post(final String URL) {
        return post(URL, null);
    }

    static String post(final String URL, final String PARAM) {
        PrintWriter pw = null;
        try {
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(TIMEOUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            pw = new PrintWriter(connection.getOutputStream());
            pw.print(PARAM);
            pw.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        } catch (IOException e) {
            LOGGER.warn("Send post request occurred in: "+URL+"?"+PARAM+" "+e.getMessage(), e);
        } finally {
            close(pw);
        }
        return null;
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
