package client.whitedev.gui.mainmenu;

import client.whitedev.gui.alt.GuiAltManager;
import client.whitedev.helper.ChatHelper;
import client.whitedev.mods.viamcp.gui.GuiProtocolSelector;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;


public class GuiClientMainMenu extends GuiScreen {

    public void drawScreen(int mouseX, int mouseY, float p) {
        mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/mainmenu.png"));
        drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, (float) width, (float) height);
        mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/logo.png"));
        drawModalRectWithCustomSizedTexture(width / 2 - 50, height / 4 - 30, 0.0F, 0.0F, 100, 100, (float) 100, (float) 100);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&aClient Base &f1.8.9 - Created by &a0WhiteDev"), 4, height - 20, 1);
        mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix("&aGitHub: &f&nhttps://github.com/0WhiteDev"), 4, height - 10, 1);

        super.drawScreen(mouseX, mouseY, p);
    }

    public void initGui() {
        this.createButtons();
    }

    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiSelectWorld(new GuiClientMainMenu()));
                break;
            case 1:
                mc.displayGuiScreen(new GuiMultiplayer(new GuiClientMainMenu()));
                break;
            case 2:
                mc.displayGuiScreen(new GuiAltManager());
                break;
            case 3:
                mc.displayGuiScreen(new GuiProtocolSelector(this));
                break;
            case 4:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 5:
                mc.shutdown();
                break;
        }
    }

    public void createButtons() {
        buttonList.add(new GuiButton(0, width / 2 - 130, height / 2 - 40, 100, 20, "SinglePlayer"));
        buttonList.add(new GuiButton(1, width / 2 + 30, height / 2 - 40, 100, 20, "MultiPlayer"));
        buttonList.add(new GuiButton(2, width / 2 - 150, height / 2 - 10, 100, 20, "AltManager"));
        buttonList.add(new GuiButton(3, width / 2 + 50, height / 2 - 10, 100, 20, "ViaVersion"));
        buttonList.add(new GuiButton(4, width / 2, height / 2 + 40, 100, 20, "Settings"));
        buttonList.add(new GuiButton(5, width / 2 - 100, height / 2 + 40, 100, 20, "Quit"));

        // This button is only for fix last button screen color bug
        buttonList.add(new GuiButton(999, 9999, 9999, 0, 0, ""));
    }
}
