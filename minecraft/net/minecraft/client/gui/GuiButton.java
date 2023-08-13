package net.minecraft.client.gui;

import java.awt.Color;

import esu.aonehax.SuperLib;
import esu.aonehax.AnimationUtils;
import esu.cyanite.Client;
import esu.cyanite.ui.font.UnicodeFontRenderer;
import esu.cyanite.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean visible;
    protected boolean hovered;
    private double animation;

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        this.animation = 0.10000000149011612D;
    }

    protected int getHoverState(boolean mouseOver) {
        int i = 1;
        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }
    float anim1 = 0f;
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fr = mc.fontRendererObj;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
//            GlStateManager.enableBlend();
//            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
//            GlStateManager.blendFunc(770, 771);
            this.animation = RenderUtil.getAnimationState(this.animation, (double)(this.hovered ? 0.2F : 0.1F), 5D);
            if (hovered) {
                //anim1 = (float)AnimationUtils.animate(18, anim1, 0.25f);
                anim1 = RenderUtil.toanim(anim1,18,6,0.1f);
            }else {
                //anim1 = (float)AnimationUtils.animate(0, anim1, 0.25f);
                anim1 = RenderUtil.toanim(anim1,0,6,0.1f);
            }
            if (this.enabled) {
                RenderUtil.drawGradientSideways((float)this.xPosition, (float)this.yPosition+18-anim1, (float)(this.xPosition + this.width), (float)(this.yPosition + this.height), SuperLib.reAlpha(new Color(10, 90, 205).getRGB(), 0.95F),SuperLib.reAlpha(new Color(1, 190, 206).getRGB(),0.95F));
            }else {
                RenderUtil.drawGradientSideways((float)this.xPosition, (float)this.yPosition+18-anim1, (float)(this.xPosition + this.width), (float)(this.yPosition + this.height), SuperLib.reAlpha(new Color(255, 10, 10).getRGB(), 0.95F),SuperLib.reAlpha(new Color(255, 111, 0).getRGB(),0.95F));
            }
            RenderUtil.drawRect((float)this.xPosition, (float)this.yPosition, (float)(this.xPosition + this.width), (float)(this.yPosition + this.height) - 1.5F, SuperLib.reAlpha(new Color(0, 0, 0).getRGB(),0.50f));
            RenderUtil.drawRect((float)this.xPosition, (float)this.yPosition, (float)(this.xPosition + this.width), (float)(this.yPosition + this.height), SuperLib.reAlpha(new Color(255, 255, 255).getRGB(), 0.1F));
//            if (this.enabled) {
//                RenderUtil.drawRect((float)this.xPosition, (float)this.yPosition, (float)(this.xPosition + this.width), (float)(this.yPosition + this.height), SuperLib.reAlpha(new Color(225, 225, 225).getRGB(), (float)this.animation));
//            } else {
//                RenderUtil.drawRect((float)this.xPosition, (float)this.yPosition, (float)(this.xPosition + this.width), (float)(this.yPosition + this.height), SuperLib.reAlpha(new Color(255, 255, 255).getRGB(), 0.1F));
//            }

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;
            if (!this.enabled) {
                j = 10526880;
            } else if (this.hovered) {
                j = 16777120;
            }

            fr.drawStringWithShadow(this.displayString, (float)this.xPosition + ((float)this.width - fr.getStringWidth(SuperLib.removeColorCode(this.displayString)) + 2.0F) / 2.0F, (float)(this.yPosition + (this.height - 8) / 2), -1);
        }

    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
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
