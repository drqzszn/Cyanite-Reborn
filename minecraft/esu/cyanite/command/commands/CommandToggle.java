package esu.cyanite.command.commands;

import esu.cyanite.command.Command;
import esu.cyanite.mod.Mod;
import esu.cyanite.mod.ModManager;
import esu.cyanite.utils.PlayerUtil;

public class CommandToggle
extends Command {
    public CommandToggle(String[] commands) {
        super(commands);
        this.setArgs(".toggle <mod>");
    }

    @Override
    public void onCmd(String[] args) {
        if (args.length != 2) {
            PlayerUtil.tellDebugPlayer(this.getArgs());
        } else {
            boolean found = false;
            for (Mod mod : ModManager.getModList()) {
                if (!args[1].equalsIgnoreCase(mod.getName())) continue;
                try {
                    mod.set(!mod.isEnabled());
                }
                catch (Exception var6) {
                    var6.printStackTrace();
                }
                found = true;
                PlayerUtil.tellDebugPlayer(mod.getName() + " was toggled");
                break;
            }
            if (!found) {
                PlayerUtil.tellDebugPlayer("Cannot find Module : " + args[1]);
            }
        }
    }
}

