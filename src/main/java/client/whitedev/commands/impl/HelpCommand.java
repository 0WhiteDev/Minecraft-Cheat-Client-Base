package client.whitedev.commands.impl;

import client.whitedev.commands.Command;

public class HelpCommand implements Command {
    private static final String COMMAND_NAME = "help";

    @Override
    public boolean check(String string) {
        return string.equalsIgnoreCase(COMMAND_NAME);
    }

    @Override
    public void onCommand(String[] args) {
        sendMessage("Author - Show Author of this Client Base and his SocialMedia", true);
        sendMessage("Help - Show Helpful tips and Commands List", true);
        sendMessage("Crash - Send Crash Packets to the Server", true);
    }
}