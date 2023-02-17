package client.whitedev.gui.hook;

import client.whitedev.helper.ChatHelper;
import client.whitedev.helper.PacketHelper;
import client.whitedev.mods.viamcp.ViaMCP;
import client.whitedev.mods.viamcp.protocols.ProtocolCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.text.DecimalFormat;

public class GuiClientInGame extends GuiIngame {

    private final Minecraft mc;
    private final ScaledResolution scaledResolution;
    private final FontRenderer fontRenderer;
    DecimalFormat df = new DecimalFormat("#.##");
    String color = "&f";

    public GuiClientInGame(Minecraft mc) {
        super(mc);
        this.mc = mc;
        this.scaledResolution = new ScaledResolution(mc);
        this.fontRenderer = mc.fontRendererObj;
    }

    @Override
    public void renderGameOverlay(float partialTicks) {
        super.renderGameOverlay(partialTicks);

        String brand;
        if (mc.thePlayer.getClientBrand() != null) {
            brand = mc.thePlayer.getClientBrand().contains("<- ") ? mc.thePlayer.getClientBrand().split(" ")[0] + " -> " + mc.thePlayer.getClientBrand().split("<- ")[1] : mc.thePlayer.getClientBrand().split(" ")[0];
        } else {
            brand = "unkown";
        }

        int lag = (int) PacketHelper.getServerLagTime();

        if (lag == 0) {
            color = "&f";
        } else if (lag > 15000) {
            color = "&c";
        } else if (lag > 10000) {
            color = "&6";
        } else if (lag > 5000) {
            color = "&e";
        } else if (lag > 1000) {
            color = "&2";
        } else {
            color = "&a";
        }

        mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/logo.png"));
        drawModalRectWithCustomSizedTexture(4, 4, 0.0F, 0.0F, 75, 75, (float) 75, (float) 75);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&a&lIP &8-> &f" + mc.getCurrentServerData().serverIP), 6, 90, -1);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&a&lEngine &8-> &f" + brand), 6, 100, -1);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&a&lVersion &8-> &f" + ProtocolCollection.getProtocolById(ViaMCP.getInstance().getVersion()).getName()), 6, 110, -1);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&a&lOnline &8-> &f" + mc.getNetHandler().getPlayerInfoMap().size() + "/" + mc.getNetHandler().currentServerMaxPlayers), 6, 120, -1);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&a&lFPS &8-> &f" + Minecraft.debugFPS), 6, 130, -1);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&a&lTPS &8-> &f" + this.df.format(PacketHelper.tps)), 6, 140, -1);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&a&lPing &8-> &f" + mc.getCurrentServerData().pingToServer + " ms"), 6, 150, -1);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&a&lLag &8-> " + color + PacketHelper.getServerLagTime() + " ms"), 6, 160, -1);
    }
}