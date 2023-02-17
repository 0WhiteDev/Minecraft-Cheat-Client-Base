package client.whitedev.gui.alt;

import client.whitedev.helper.ChatHelper;
import client.whitedev.mods.altmanager.AltLoginThread;
import client.whitedev.mods.altmanager.PasswordField;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public final class GuiAltLogin extends GuiScreen {
    private final GuiScreen previousScreen;
    private PasswordField password;
    private AltLoginThread thread;
    private GuiTextField username;
    private MicrosoftAuthResult mca;
    private String microsoftlogin = ChatHelper.fix("&7Microsoft Auth: Waiting for action...");

    public GuiAltLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
                this.thread.start();
                break;
            case 1:
                microsoftlogin = ChatHelper.fix("&7Microsoft Auth: &eLogging in...");
                try {
                    mca = new MicrosoftAuthenticator().loginWithCredentials(username.getText(), password.getText());
                    Minecraft.getMinecraft().session = new Session(mca.getProfile().getName(), mca.getProfile().getId() ,mca.getAccessToken(), Session.Type.LEGACY.name());
                    microsoftlogin = ChatHelper.fix("&7Microsoft Auth: &aLogged in. (" + mca.getProfile().getName() + ")");
                } catch (Exception e) {
                    microsoftlogin = ChatHelper.fix("&7Microsoft Auth: &cLogin failed!");
                }
                break;
            case 2:
                this.mc.displayGuiScreen(this.previousScreen);
        }

    }

    public void drawScreen(int x2, int y2, float z2) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        drawCenteredString(this.mc.fontRendererObj, "Alt Login", width / 2, 20, -1);
        drawCenteredString(this.mc.fontRendererObj, this.thread == null ? EnumChatFormatting.GRAY + "Idle..." : this.thread.getStatus(), width / 2, 29, -1);
        drawCenteredString(this.mc.fontRendererObj, microsoftlogin, width / 2, 38, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }

        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", width / 2 - 96, 106, -7829368);
        }

        super.drawScreen(x2, y2, z2);
    }

    public void initGui() {
        int var3 = height / 4 + 24;
        this.buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 72 + 12 + 24, "Microsoft Login"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, var3 + 72 + 12 + 48, "Back"));
        this.username = new GuiTextField(var3, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        if (character == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            } else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }

        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }

        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }

    protected void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
}
