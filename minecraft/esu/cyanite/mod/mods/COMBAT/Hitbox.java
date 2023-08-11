package esu.cyanite.mod.mods.COMBAT;

import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.value.Value;

public class Hitbox extends Mod {
    public static Value<Double> size = new Value<Double>("Hitbox_Size", 0.1, 0.1, 1.0, 0.01);
    public Hitbox() {
        super("Hitbox", "Hitbox", Category.COMBAT);
    }
}
