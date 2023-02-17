package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.Arrays;

public class GuiButton extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean visible;
    public int width;
    public int height;
    protected boolean hovered;

    public GuiButton(int buttonId, int x2, int y2, String buttonText) {
        this(buttonId, x2, y2, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x2, int y2, int widthIn, int heightIn, String buttonText) {
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x2;
        this.yPosition = y2;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    protected int getHoverState(boolean mouseOver) {
        int i2 = 1;
        if (!this.enabled) {
            i2 = 0;
        } else if (mouseOver) {
            i2 = 2;
        }

        return i2;
    }

    public void drawButton(Minecraft mc2, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.mouseDragged(mc2, mouseX, mouseY);
            int textcol = 14737632;
            int posx1 = this.xPosition;
            int posy1 = this.yPosition;
            int posx2 = this.xPosition + this.width;
            int posy2 = this.yPosition + this.height;
            if (this.hovered) {
                posx1 = this.xPosition + 1;
                posy1 = this.yPosition + 1;
                posx2 = this.xPosition + this.width - 1;
                posy2 = this.yPosition + this.height - 1;
                textcol = getColor();
            }

            if (this.hovered) {
                Gui.drawRect(posx1, posy1 - 2, posx2, posy1, getColor());
                Gui.drawRect(posx1, posy2 + 2, posx2, posy2, getColor());
                Gui.drawRect(posx1, posy1 - 2, posx1 - 2, posy2 + 2, getColor());
                Gui.drawRect(posx2, posy1 - 2, posx2 + 2, posy2 + 2, getColor());
            }

            drawCenteredString(mc2.fontRendererObj, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, textcol);
        }

    }

    public static int getColor() {
        return Color.GREEN.getRGB();
    }

    protected void mouseDragged(Minecraft mc2, int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public boolean mousePressed(Minecraft mc2, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
