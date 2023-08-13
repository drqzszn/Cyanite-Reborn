package esu.cyanite.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class QQUtils {

    // ... 其他代码 ...

    public static void makeHTTPRequest(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 可以根据需要对响应进行处理
            System.out.println("Response Content: " + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void sendGroupMessage(long groupId, String message) {

        String url = null;
        try {
            url = "http://127.0.0.1:9515/send_group_msg?group_id=" + groupId + "&message=" + URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        makeHTTPRequest(url);
    }
    public static void sendPrivateMessage(long userId, String message) {
        String url = "http://127.0.0.1:9515/send_private_msg?user_id=" + userId + "&message=" + message;
        makeHTTPRequest(url);
    }

    // ... 其他代码 ...
}