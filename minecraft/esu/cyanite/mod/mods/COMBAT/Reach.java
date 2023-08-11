package esu.cyanite.mod.mods.COMBAT;

import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.value.Value;

public class Reach extends Mod
{
    public Reach() {
        super("Reach","Reach", Category.COMBAT);
    }
    
    public static Value<Double> reach = new Value<Double>("Reach_Range", 4.5, 3.0, 8.0, 0.1);

}
