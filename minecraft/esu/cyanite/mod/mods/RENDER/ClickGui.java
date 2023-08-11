package esu.cyanite.mod.mods.RENDER;

import esu.cyanite.Client;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.ui.ClickGUI.UIClick;

public class ClickGui
extends Mod {

    public ClickGui() {
        super("ClickGui", "Click Gui", Category.RENDER);
        this.setKey(54);
    }

    @Override
    public void onEnable() {
	if (this.mc.currentScreen instanceof UIClick) {
            this.set(false);
            return;
        }
        this.mc.displayGuiScreen(Client.instance.crink);
        this.set(false);
        super.onEnable();
    }
}

