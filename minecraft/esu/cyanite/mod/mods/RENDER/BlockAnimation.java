package esu.cyanite.mod.mods.RENDER;

import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.value.Value;
import com.darkmagician6.eventapi.EventTarget;

public class BlockAnimation
extends Mod {
    public static Value<String> mode = new Value("BlockAnimation", "Mode", 0);

    public BlockAnimation() {
        super("BlockAnimation", Category.RENDER);
        mode.addValue("Swing");
        mode.addValue("Swang");
        mode.addValue("Swong");
        mode.addValue("Swank");
        mode.addValue("Swaing");
        mode.addValue("Vanilla");
    }

    @Override
    public void onEnable() {
//        this.set(false);
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (this.isEnabled()) {
//            this.set(false);
        }
    }
}

