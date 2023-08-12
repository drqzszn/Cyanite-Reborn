package esu.cyanite.mod.mods.RENDER;

import esu.cyanite.Client;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.ui.CGUI;
import esu.cyanite.ui.ClickGUI.UIClick;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;

public class ClickGui extends Mod {

    public ClickGui() {
        super("ClickGui", "Click Gui", Category.RENDER);
        this.setKey(54);
    }

    @Override
    public void onEnable() {

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();

        mc.displayGuiScreen(new CGUI());

     /*
	if (this.mc.currentScreen instanceof UIClick) {
            this.set(false);
            return;
        }
        this.mc.displayGuiScreen(Client.instance.crink);
        this.set(false);
        super.onEnable();
    }
    */
    }
}

