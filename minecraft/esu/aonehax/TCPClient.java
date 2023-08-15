package esu.aonehax;

import esu.cyanite.utils.PlayerUtil;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void sendMessage(String serverIP, int serverPort, String message) {
        try {
            // 创建客户端Socket并连接服务器
            Socket clientSocket = new Socket(serverIP, serverPort);

            // 获取输出流
            OutputStream outputStream = clientSocket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            PrintWriter printWriter = new PrintWriter(writer, true);

            // 发送消息到服务器
            printWriter.println(message);

            // 关闭资源
            printWriter.close();
            writer.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
