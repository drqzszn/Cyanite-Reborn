package esu.cyanite.mod.mods.RENDER;

import esu.cyanite.Client;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.ui.CGUI;
import esu.cyanite.ui.ClickGUI.UIClick;
import esu.cyanite.ui.ClickGUI.UIMenuMods;
import esu.cyanite.value.Value;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;

public class ClickGui extends Mod {

    public Value<String> mode;

    public ClickGui() {
        super("ClickGui", "Click Gui", Category.RENDER);
        this.setKey(54);
        this.mode = new Value<String>("ClickGui", "Mode", 0);
        this.mode.addValue("FPSMaster");
        this.mode.addValue("Debug");
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

        if(this.mode.isCurrentMode("Debug")){
            UIMenuMods.fuck = false;
            this.mc.displayGuiScreen(Client.instance.crink);
            this.set(false);
        }else{
            UIMenuMods.fuck = true;
            mc.displayGuiScreen(new CGUI());
        }

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

