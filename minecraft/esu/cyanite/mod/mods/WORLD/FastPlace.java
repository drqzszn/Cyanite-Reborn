package esu.cyanite.mod.mods.WORLD;

import com.darkmagician6.eventapi.EventTarget;

import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;

public class FastPlace extends Mod
{
    
    public FastPlace() {
        super("FastPlace", "Fast Place", Category.WORLD);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        this.mc.rightClickDelayTimer = Math.min(this.mc.rightClickDelayTimer, 1);
    }
}
