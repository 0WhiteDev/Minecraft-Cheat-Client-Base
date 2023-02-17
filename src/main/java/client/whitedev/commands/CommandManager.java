package client.whitedev.commands;

import client.whitedev.helper.ChatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class CommandManager {

    public static final String COMMAND_PREFIX = "#";
    private List<Command> commands = new ArrayList<>();
    private static CommandManager commandManager;

    public void addCommands(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public static CommandManager getManager(){
        if (commandManager == null) {
            commandManager = new CommandManager();
        }
        return commandManager;
    }

    public boolean handleCommand(String msg) {
        if (!msg.startsWith(COMMAND_PREFIX)) {
            return false;
        }

        String[] args = msg.substring(1).split(" ");
        Optional<Command> commandOptional = commands.stream().filter((command) -> command.check(args[0])).findFirst();

        if (commandOptional.isPresent()) {
            Command command = commandOptional.get();
            command.onCommand(args);
            return true;
        } else {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.fix("&8&l[&a&lClientBase&8&l] &8>> &7Command not found!")));
            return false;
        }
    }
}
