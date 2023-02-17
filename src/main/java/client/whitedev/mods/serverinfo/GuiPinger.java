package client.whitedev.mods.serverinfo;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;

public class GuiPinger {
    private final int x;
    private final int y;
    private final int width;
    private final int high;
    private static final ThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("HanfClient Server Pinger #%d").setDaemon(true).build());
    private DynamicTexture field_148305_h;
    private final ResourceLocation field_148306_i;
    private final ServerData serverData;
    private final OldServerPinger oldServerPinger = new OldServerPinger();
    private final Minecraft mc = Minecraft.getMinecraft();
    private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
    private final boolean update;
    private Integer currentAnimation;
    private final int updateLengh;

    public GuiPinger(int x, int y, int width, int high, ServerData serverData, boolean update) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.serverData = serverData;
        this.high = high;
        this.update = update;
        this.field_148306_i = new ResourceLocation("servers/" + serverData.serverIP + "/icon");
        this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.field_148306_i);
        this.updateLengh = 2;
        ping();
    }

    public void draw() {
        int l;
        String s1;
        boolean flag = (serverData.version > 47);
        boolean flag1 = (serverData.version < 47);
        boolean flag2 = !(!flag && !flag1);
        Gui.drawRect(x - 10, y, x + width, y + high, ColorUtils.transpartensColor(Color.BLACK, 100).getRGB());
        mc.fontRendererObj.drawString(serverData.serverName, x + 32 + 3, y + 1 + 10, 16777215);
        List<String> list = mc.fontRendererObj.listFormattedStringToWidth(serverData.serverMOTD, width - 32 - 2);
        for (int i = 0; i < Math.min(list.size(), 2); i++)
            mc.fontRendererObj.drawString(list.get(i), x + 32 + 3, y + 12 + mc.fontRendererObj.FONT_HEIGHT * i + 10, 8421504);
        String s2 = flag2 ? (EnumChatFormatting.DARK_RED + serverData.gameVersion + " " + serverData.populationInfo) : (serverData.gameVersion + " " + serverData.populationInfo);
        int j = mc.fontRendererObj.getStringWidth(s2);
        mc.fontRendererObj.drawString(s2, width + x - 68 - j, y + 1 + 10, 8421504);
        int k = 0;
        if (flag2) {
            l = 5;
            s1 = flag ? ("Client out of date! " + serverData.pingToServer + "ms") : ("Server out of date! " + serverData.pingToServer + "ms");
        } else if (serverData.field_78841_f && serverData.pingToServer != -2L) {
            if (serverData.pingToServer < 0L) {
                l = 5;
            } else if (serverData.pingToServer < 150L) {
                l = 0;
            } else if (serverData.pingToServer < 300L) {
                l = 1;
            } else if (serverData.pingToServer < 600L) {
                l = 2;
            } else if (serverData.pingToServer < 1000L) {
                l = 3;
            } else {
                l = 4;
            }
            if (serverData.pingToServer < 0L) {
                s1 = "no con";
            } else {
                s1 = serverData.pingToServer + "ms";
            }
        } else {
            k = 1;
            l = (int)(Minecraft.getSystemTime() / 100L + 2L & 0x7L);
            if (l > 4)
                l = 8 - l;
            s1 = "Pinging...";
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(width + x - 65, y + 10, (k * 10), (176 + l * 8), 10, 8, 256.0F, 256.0F);
        mc.fontRendererObj.drawString("(" + s1 + ")", width + x - 50, y + 11, Color.gray.getRGB());
        if (serverData.getBase64EncodedIconData() != null) {
            prepareServerIcon();
        }
        if (field_148305_h != null) {
            func_178012_a(x, y, field_148306_i);
        } else {
            func_178012_a(x, y, UNKNOWN_SERVER);
        }
        if (update)
            doAnimation();
    }

    private void doAnimation() {
        if (currentAnimation == null)
            currentAnimation = width;
        if (currentAnimation <= -10)
            resetAnimation();
        currentAnimation = currentAnimation - updateLengh;
        Gui.drawRect(x - 10, y + high - 3, currentAnimation + x, y + high, Color.GREEN.getRGB());
    }

    public void resetAnimation() {
        currentAnimation = width;
        ping();
    }

    public void ping() {
        serverData.field_78841_f = true;
        serverData.pingToServer = -2L;
        serverData.serverMOTD = "Pinging...";
        serverData.populationInfo = "";
        pool.submit(() -> {
            try {
                oldServerPinger.ping(serverData);
            } catch (UnknownHostException var2) {
                serverData.pingToServer = -1L;
                serverData.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
            } catch (Exception var3) {
                serverData.pingToServer = -1L;
                serverData.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
            }
        });
    }

    protected void func_178012_a(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_) {
        mc.getTextureManager().bindTexture(p_178012_3_);
        GlStateManager.enableBlend();
        mc.fontRendererObj.drawString("", p_178012_1_, p_178012_2_ + 9, Color.white.getRGB());
                Gui.drawModalRectWithCustomSizedTexture(p_178012_1_ - 1, p_178012_2_ + 9, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        GlStateManager.disableBlend();
    }

    private void prepareServerIcon() {
        if (serverData.getBase64EncodedIconData() == null) {
            mc.getTextureManager().deleteTexture(field_148306_i);
            field_148305_h = null;
        } else {
            BufferedImage bufferedimage;
            ByteBuf bytebuf = Unpooled.copiedBuffer(serverData.getBase64EncodedIconData(),
                    Charsets.UTF_8);
            ByteBuf bytebuf1 = Base64.decode(bytebuf);
            try {
                bufferedimage = TextureUtil.readBufferedImage(new ByteBufInputStream(bytebuf1));
                Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide");
                Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high");
            } catch (Throwable throwable) {
                System.out.println("Invalid icon for server " + serverData.serverName + " (" + serverData.serverIP + ")");
                serverData.setBase64EncodedIconData(null);
            } finally {
                bytebuf.release();
                bytebuf1.release();
            }
            if (field_148305_h == null) {
                field_148305_h = new DynamicTexture(64, 64);
                mc.getTextureManager().loadTexture(field_148306_i, field_148305_h);
            }
            field_148305_h.updateDynamicTexture();
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY) {
        return (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + high);
    }

    public void setServerIP(String ip) {
        serverData.serverIP = ip;
        serverData.serverName = ip;
    }

}
