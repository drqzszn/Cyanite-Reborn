package esu.aonehax;

import esu.cyanite.utils.PlayerUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ESU {

    public static void run() {
        String serverip = "103.239.245.36";
        int serverPort = 25862;
        try {
            // 创建客户端Socket并连接服务器
            Socket clientSocket = new Socket(serverip, serverPort);
            System.out.println("已连接到服务器：" + serverip + ":" + serverPort);

            // 获取输入流和输出流
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // 创建线程来持续监听服务器消息
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String serverMessage = bufferedReader.readLine();
                        if (serverMessage == null) {
                            System.out.println("与服务器的连接已断开。");
                            break;
                        }
                        PlayerUtil.tellDebugPlayer(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // 主线程保持连接，不关闭
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
