package client.whitedev.crashers;

import client.whitedev.commands.CommandManager;
import client.whitedev.helper.ChatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CrashManager {
    private List<Method> methods = new ArrayList<>();
    private static CrashManager crashManager;

    public void addMethod(Method... methods) {
        this.methods.addAll(Arrays.asList(methods));
    }

    public static CrashManager getManager(){
        if (crashManager == null) {
            crashManager = new CrashManager();
        }
        return crashManager;
    }

    public boolean handleMethod(String msg) {
        if (!msg.startsWith(CommandManager.COMMAND_PREFIX)) {
            return false;
        }

        String[] args = msg.substring(1).split(" ");
        if(args.length == 3) {
            Optional<Method> methodOptional = methods.stream().filter((method) -> method.check(args[1])).findFirst();

            if (methodOptional.isPresent()) {
                Method method = methodOptional.get();
                method.onMethod(args);
                return true;
            } else {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.fix("&8&l[&a&lClientBase&8&l] &8>> &7Available methods: &f" + allMethods())));
            }
            return false;
        }
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.fix("&8&l[&a&lClientBase&8&l] &8>> &7Correct use of the command: &f" + CommandManager.COMMAND_PREFIX + "crash <method> <packets>")));
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.fix("&8&l[&a&lClientBase&8&l] &8>> &7Available methods: &f" + allMethods())));
        return false;
    }

    public String allMethods(){
        List<String> methodNames = new ArrayList<>();
        for (Method method : methods) {
            methodNames.add(method.getName());
        }
        return String.join(", ", methodNames);
    }
}
