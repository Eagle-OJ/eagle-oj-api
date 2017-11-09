package util;

import java.io.*;

/**
 * @author Smith
 **/
public class ReadUtil {
    public static String read(String path) throws IOException {
        InputStream is = ReadUtil.class.getResourceAsStream(path);
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = null;
        while ((s = br.readLine()) != null) {
            stringBuilder.append(s);
        }
        br.close();

        return stringBuilder.toString();
    }
}
