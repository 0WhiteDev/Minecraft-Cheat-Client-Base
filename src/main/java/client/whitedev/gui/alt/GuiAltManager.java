package client.whitedev.gui.alt;

import client.whitedev.helper.ChatHelper;
import client.whitedev.helper.RandomHelper;
import client.whitedev.helper.RectangleHelper;
import client.whitedev.mods.altmanager.Alt;
import client.whitedev.mods.altmanager.AltLoginThread;
import client.whitedev.mods.altmanager.AltManager;
import client.whitedev.mods.thealtening.auth.TheAlteningAuthentication;
import client.whitedev.mods.thealtening.auth.service.AlteningServiceType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiAltManager extends GuiScreen {
    public Alt selectedAlt = null;
    private String data;
    private String email;
    private String pass_;
    private AltLoginThread thread;
    private GuiButton login;
    private GuiButton remove;
    private GuiButton rename;
    private AltLoginThread loginThread;
    private int offset;
    private String status;
    private TheAlteningAuthentication serviceSwitch = TheAlteningAuthentication.mojang();
    public static Boolean help = false;
    public static boolean loaded = false;

    public static boolean isHelp() {
        return help;
    }

    public void setHelp(boolean enabled) {
        help = enabled;
    }

    public GuiAltManager() {
        this.status = EnumChatFormatting.GRAY + "No alts selected";
    }

    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                if (this.loginThread == null) {
                    this.mc.displayGuiScreen(null);
                } else if (!this.loginThread.getStatus().equals(EnumChatFormatting.YELLOW + "Attempting to log in") && !this.loginThread.getStatus().equals(EnumChatFormatting.RED + "Do not hit back!" + EnumChatFormatting.YELLOW + " Logging in...")) {
                    this.mc.displayGuiScreen(null);
                } else {
                    this.loginThread.setStatus(EnumChatFormatting.RED + "Failed to login! Please try again!" + EnumChatFormatting.YELLOW + " Logging in...");
                }
                break;
            case 1:
                String user = this.selectedAlt.getUsername();
                String pass = this.selectedAlt.getPassword();
                this.loginThread = new AltLoginThread(user, pass);
                this.loginThread.start();
                break;
            case 2:
                if (this.loginThread != null) {
                    this.loginThread = null;
                }

                AltManager.registry.remove(this.selectedAlt);
                this.status = "Removed";
                this.selectedAlt = null;
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            case 4:
                this.mc.displayGuiScreen(new GuiAltLogin(this));
            case 5:
            default:
                break;
            case 6:
                this.mc.displayGuiScreen(new GuiRenameAlt(this));
            case 7: {
                serviceSwitch.updateService(AlteningServiceType.MOJANG);
                break;
            }
            case 8: {
                serviceSwitch.updateService(AlteningServiceType.THEALTENING);
                break;
            }
            case 9: {
                this.setHelp(!isHelp());
                break;
            }
            case 10: {
                this.loginThread = new AltLoginThread(RandomHelper.getRandomHelper().getRandomString(9, false), "");
                this.loginThread.start();
                break;
            }
            case 11: {
                new GuiAddAlt(new GuiAltManager()).loadAlts();
                break;
            }
            case 12: {
                try {
                    data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    Matcher m = Pattern.compile(":").matcher(data);
                    while (m.find()) {
                        String processed = data.replace(":", " ");
                        int index = -1;
                        for (int i = 0; i < processed.length(); i++) {
                            if (Character.isWhitespace(processed.charAt(i))) {
                                index = i + 1;
                                break;
                            }
                        }
                        email = processed.substring(0, index - 1);
                        pass_ = processed.substring(index);
                        this.thread = new AltLoginThread(email, pass_);
                        this.thread.start();
                    }
                } catch (UnsupportedFlavorException e) {e.printStackTrace();}
                break;
            }
        }

    }

    public void drawScreen(int par1, int par2, float par3) {
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            } else if (wheel > 0) {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }

        this.drawDefaultBackground();
        this.drawString(this.fontRendererObj, "Name: " + this.mc.session.getUsername(), 10, 10, -7829368);
        if (isHelp()) {
            this.drawString(this.fontRendererObj, "If you want use TheAltening:", 10, 20, -7829368);
            this.drawString(this.fontRendererObj, "1. Click thealtening button", 10, 30, -7829368);
            this.drawString(this.fontRendererObj, "2. Click add alt and copy email from TheAltening!", 10, 40, -7829368);
            this.drawString(this.fontRendererObj, "3. Type random password (min 8 words) like -> 123456789!", 10, 50, -7829368);
            this.drawString(this.fontRendererObj, "If you want use Normal Account:", 10, 70, -7829368);
            this.drawString(this.fontRendererObj, "1. Click mojang button", 10, 80, -7829368);
            this.drawString(this.fontRendererObj, "2. Click add alt and enter your account password and email!", 10, 90, -7829368);
        }
        drawString(this.fontRendererObj, "§7Altening Service Type: §f" + serviceSwitch.getService(), 3, height - 10, -1);
        FontRenderer fontRendererObj = this.fontRendererObj;
        StringBuilder sb2 = new StringBuilder("Alt Manager - ");
        drawCenteredString(fontRendererObj, sb2.append(AltManager.registry.size()).append(" alts").toString(), width / 2, 10, -1);
        drawCenteredString(this.fontRendererObj, this.loginThread == null ? this.status : this.loginThread.getStatus(), width / 2, 20, -1);
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0F, 33.0F, (float) width, (float) (height - 50));
        GL11.glEnable(3089);
        int y2 = 38;
        Iterator var7 = AltManager.registry.iterator();

        while (true) {
            Alt alt2;
            do {
                if (!var7.hasNext()) {
                    GL11.glDisable(3089);
                    GL11.glPopMatrix();
                    super.drawScreen(par1, par2, par3);
                    if (this.selectedAlt == null) {
                        this.login.enabled = false;
                        this.remove.enabled = false;
                        this.rename.enabled = false;
                    } else {
                        this.login.enabled = true;
                        this.remove.enabled = true;
                        this.rename.enabled = true;
                    }

                    if (Keyboard.isKeyDown(200)) {
                        this.offset -= 26;
                        if (this.offset < 0) {
                            this.offset = 0;
                        }
                    } else if (Keyboard.isKeyDown(208)) {
                        this.offset += 26;
                        if (this.offset < 0) {
                            this.offset = 0;
                        }
                    }

                    return;
                }

                alt2 = (Alt) var7.next();
            } while (!this.isAltInArea(y2));

            String name = alt2.getMask().equals("") ? alt2.getUsername() : alt2.getMask();
            String pass = alt2.getPassword().equals("") ? ChatHelper.fix("&oCracked") : ChatHelper.fix("&oPassword Hidden");
            if (alt2 == this.selectedAlt) {
                if (this.isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown(0)) {
                    RectangleHelper.drawBorderedRect(52.0F, (float) (y2 - this.offset - 4), (float) (width - 52), (float) (y2 - this.offset + 20), 1.0F, -16777216, -2142943931);
                } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset)) {
                    RectangleHelper.drawBorderedRect(52.0F, (float) (y2 - this.offset - 4), (float) (width - 52), (float) (y2 - this.offset + 20), 1.0F, -16777216, -2142088622);
                } else {
                    RectangleHelper.drawBorderedRect(52.0F, (float) (y2 - this.offset - 4), (float) (width - 52), (float) (y2 - this.offset + 20), 1.0F, -16777216, -2144259791);
                }
            } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown(0)) {
                RectangleHelper.drawBorderedRect(52.0F, (float) (y2 - this.offset - 4), (float) (width - 52), (float) (y2 - this.offset + 20), 1.0F, -16777216, -2146101995);
            } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset)) {
                RectangleHelper.drawBorderedRect(52.0F, (float) (y2 - this.offset - 4), (float) (width - 52), (float) (y2 - this.offset + 20), 1.0F, -16777216, -2145180893);
            }

            drawCenteredString(this.fontRendererObj, name, width / 2, y2 - this.offset, -1);
            drawCenteredString(this.fontRendererObj, pass, width / 2, y2 - this.offset + 10, 5592405);
            y2 += 26;
        }
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(9999, 5, 999999, 90, 20, ""));
        this.buttonList.add(new GuiButton(0, width / 2 + 4 + 50, height - 24, 100, 20, "Back"));
        this.login = new GuiButton(1, width / 2 - 154, height - 48, 100, 20, "Login");
        this.buttonList.add(this.login);
        this.remove = new GuiButton(2, width / 2 - 154, height - 24, 100, 20, "Remove");
        this.buttonList.add(this.remove);
        this.buttonList.add(new GuiButton(3, width / 2 + 4 + 50, height - 48, 100, 20, "Add"));
        this.buttonList.add(new GuiButton(4, width / 2 - 50, height - 48, 100, 20, "Direct Login"));
        this.rename = new GuiButton(6, width / 2 - 50, height - 24, 100, 20, "Edit");
        this.buttonList.add(new GuiButton(7, width / 2 - 270, height - 24, 100, 20, "Mojang"));
        this.buttonList.add(new GuiButton(8, width / 2 - 270, height - 48, 100, 20, "TheAltening"));
        this.buttonList.add(new GuiButton(9, width / 2 + 166, height - 48, 100, 20, "Help"));
        this.buttonList.add(new GuiButton(10, width / 2 + 166, height - 24, 100, 20, "Random Nickname"));
        this.buttonList.add(new GuiButton(11, width / 2 + 166, height - 72, 100, 20, "Load Alts"));
        this.buttonList.add(new GuiButton(12, width / 2 - 270, height - 72, 100, 20, "Clipboard Login"));

        this.buttonList.add(this.rename);
        this.login.enabled = false;
        this.remove.enabled = false;
        this.rename.enabled = false;
    }

    private boolean isAltInArea(int y2) {
        return y2 - this.offset <= height - 50;
    }

    private boolean isMouseOverAlt(int x2, int y2, int y1) {
        return x2 >= 52 && y2 >= y1 - 4 && x2 <= width - 52 && y2 <= y1 + 20 && y2 >= 33 && x2 <= width && y2 <= height - 50;
    }

    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        if (this.offset < 0) {
            this.offset = 0;
        }

        int y2 = 38 - this.offset;

        for (Iterator var5 = AltManager.registry.iterator(); var5.hasNext(); y2 += 26) {
            Alt alt2 = (Alt) var5.next();
            if (this.isMouseOverAlt(par1, par2, y2)) {
                if (alt2 == this.selectedAlt) {
                    this.actionPerformed(this.buttonList.get(1));
                    return;
                }

                this.selectedAlt = alt2;
            }
        }

        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
        ScaledResolution scale = new ScaledResolution(this.mc);
        int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x2 * (float) factor), (int) (((float) scale.getScaledHeight() - y22) * (float) factor), (int) ((x22 - x2) * (float) factor), (int) ((y22 - y2) * (float) factor));
    }
}
