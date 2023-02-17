package client.whitedev.mods;

import client.whitedev.ClientBase;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordRichPresence.Builder;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Discord implements ReadyCallback {

    // Delete this class if you dont wanna use Discord Game Status

    private static final Minecraft mc = Minecraft.getMinecraft();
    private final String applicationId = "APP_ID_HERE";
    DiscordRichPresence richPresence = (new Builder("Loading..."))
            .setBigImage("logo", "ClientBase 1.8.9 | " + ClientBase.getClient().version)
            .setDetails("Loading...")
            .setStartTimestamps(System.currentTimeMillis()).build();
    private boolean enabled = true;

    public Discord() {
        this.init();
        this.startTask();
        DiscordRPC.discordUpdatePresence(this.richPresence);
    }

    public void apply(DiscordUser discordUser) {
        System.out.println("Initialized DiscordRichPresence API.");
    }

    private void init() {
        DiscordEventHandlers handlers = (new net.arikia.dev.drpc.DiscordEventHandlers.Builder()).setReadyEventHandler((user) -> System.out.printf("Connected to %s#%s (%s)%n", user.username, user.discriminator, user.userId)).build();
        DiscordRPC.discordInitialize(applicationId, handlers, true);

    }

    public void startTask() {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            if (!enabled) {
                DiscordEventHandlers handlers = (new net.arikia.dev.drpc.DiscordEventHandlers.Builder()).setReadyEventHandler((user) -> System.out.printf("Connected to %s#%s (%s)%n", user.username, user.discriminator, user.userId)).build();
                DiscordRPC.discordInitialize(applicationId, handlers, true);
                enabled = true;
            }
            this.richPresence.details = mc.thePlayer == null ? "Main Menu" : "Nick: " + mc.getSession().getUsername();
            this.richPresence.state = mc.getCurrentServerData() == null ? "Offline" : "Server: " + mc.getCurrentServerData().serverIP;
            DiscordRPC.discordUpdatePresence(this.richPresence);
        }, 10L, 10L, TimeUnit.SECONDS);
    }
}

