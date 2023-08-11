package esu.cyanite.command.commands;

import esu.cyanite.command.Command;
import esu.cyanite.utils.PlayerUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommandQQ extends Command {
    public CommandQQ(String[] commands) {
        super(commands);
        this.setArgs(".esu <qq>");
    }
    @Override
    public void onCmd(String[] args) {
        if (args.length == 2) {
            String qqNumber = args[1];

            try {
                URL url = new URL("https://zy.xywlapi.cc/qqapi?qq=" + qqNumber);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 解析 JSON 数据
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

                // 提取 phone 值
                String phone = jsonObject.get("phone").getAsString();
                String place = jsonObject.get("phonediqu").getAsString();

                PlayerUtil.tellDebugPlayer("QQ:" + qqNumber + " " + "Phone:" + phone + " " + "phonediqu:" + place);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
