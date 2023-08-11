package esu.cyanite.command;

import esu.cyanite.command.commands.*;

import java.util.ArrayList;

public class CommandManager {
    private static ArrayList<Command> commands = new ArrayList();

    public CommandManager() {
        commands.add(new CommandToggle(new String[]{"toggle", "t"}));
        commands.add(new CommandBind(new String[]{"bind"}));
        commands.add(new CommandFriend(new String[] { "friend" }));
        commands.add(new CommandConfig(new String[] { "config" }));
        commands.add(new CommandQQ(new String[] { "esu" }));
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public static String removeSpaces(String message) {
        String space = " ";
        String doubleSpace = "  ";
        while (message.contains(doubleSpace)) {
            message = message.replace(doubleSpace, space);
        }
        return message;
    }
}

