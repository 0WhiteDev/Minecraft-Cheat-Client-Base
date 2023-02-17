package client.whitedev.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatHelper {
    public static String fix(String string) {
        return string.replace('&', '§').replace(">>", "»");
    }
    public void sendMessage(String message, boolean prefix) {
        if(prefix){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.fix("&8&l[&a&lClientBase&8&l] &8>> &7" + message)));
        }else{
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.fix(message)));
        }
    }
}
