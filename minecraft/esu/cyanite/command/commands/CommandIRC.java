package esu.cyanite.command.commands;

import esu.aonehax.TCPClient;
import esu.cyanite.command.Command;
import esu.cyanite.mod.ModManager;
import esu.cyanite.utils.PlayerUtil;
import esu.cyanite.utils.QQUtils;
import net.minecraft.client.Minecraft;

public class CommandIRC extends Command {
    public CommandIRC(String[] commands) {
        super(commands);
        this.setArgs(".irc <message>");
    }
    @Override
    public void onCmd(String[] args) {
        if (args.length == 2) {
            if (ModManager.getModByName("IRC").isEnabled()) {

                TCPClient.sendMessage("103.239.245.36", 25862, "[Cyanite]" + Minecraft.thePlayer.getName() + ": " + args[1]);
            }
            else {
                PlayerUtil.tellDebugPlayer("Please Enable IRC");
            }
        }
    }
}
