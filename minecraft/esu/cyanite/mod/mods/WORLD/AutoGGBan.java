package esu.cyanite.mod.mods.WORLD;

import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.value.Value;

public class AutoGGBan extends Mod
{
    public static Value ad;
    
    public AutoGGBan() {
        super("AutoGG", Category.WORLD);
    }
    
    static {
        AutoGGBan.ad = new Value("AutoGG_AD", (Boolean)true);
    }
}
