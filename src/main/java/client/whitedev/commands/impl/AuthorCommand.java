package client.whitedev.commands.impl;

import client.whitedev.commands.Command;

public class AuthorCommand implements Command {
    private static final String COMMAND_NAME = "author";

    @Override
    public boolean check(String string) {
        return string.equalsIgnoreCase(COMMAND_NAME);
    }

    @Override
    public void onCommand(String[] args) {
        sendMessage("", false);
        sendMessage("&a0WhiteDev &8-> &fMain Developer", true);
        sendMessage("&aGitHub&8: &fhttps://github.com/0WhiteDev", true);
        sendMessage("&aInstagram&8: &fhttps://www.instagram.com/0whitedev/", true);
        sendMessage("&aDiscord&8: &f0WhiteDev#0001", true);
        sendMessage("", false);
    }
}