package client.whitedev.commands;

import client.whitedev.helper.ChatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public interface Command {
    boolean check(String string);
    void onCommand(String[] args);

    default void sendMessage(String message, boolean prefix) {
        if(prefix){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.fix("&8&l[&a&lClientBase&8&l] &8>> &7" + message)));
        }else{
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.fix(message)));
        }
    }
}
