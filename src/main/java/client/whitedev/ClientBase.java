package client.whitedev;

import client.whitedev.commands.CommandManager;
import client.whitedev.commands.impl.AuthorCommand;
import client.whitedev.commands.impl.HelpCommand;
import client.whitedev.crashers.CrashManager;
import client.whitedev.crashers.impl.Test2Method;
import client.whitedev.crashers.impl.TestMethod;
import client.whitedev.mods.Discord;
import client.whitedev.mods.viamcp.ViaMCP;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

public class ClientBase {

    public String version = "1.0";
    private static ClientBase client;
    public Minecraft mc = Minecraft.getMinecraft();

    public void clientStart() throws Exception {
        new Discord();
        CommandManager.getManager().addCommands(
                new AuthorCommand(),
                new HelpCommand()
        );
        CrashManager.getManager().addMethod(
                new TestMethod(),
                new Test2Method()
        );
        Display.setTitle("ClientBase 1.8.9 | " + version);
        Minecraft.getMinecraft().gameSettings.ofFastRender = true;
        try {
            ViaMCP.getInstance().start();
            ViaMCP.getInstance().initAsyncSlider();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ClientBase getClient() {
        if (client == null) {
            client = new ClientBase();
        }
        return client;
    }

    public void terminate() {
        DiscordRPC.discordShutdown();
    }

}
