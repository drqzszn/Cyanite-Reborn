package esu.cyanite.mod;

import esu.cyanite.mod.mods.COMBAT.*;
import esu.cyanite.mod.mods.MOVEMENT.*;
import esu.cyanite.mod.mods.PLAYER.*;
import esu.cyanite.mod.mods.RENDER.*;
import esu.cyanite.mod.mods.WORLD.*;

import java.util.ArrayList;

public class ModManager {
    public static ArrayList<Mod> modList = new ArrayList();

    public ModManager() {
        modList.add(new NoCommand());
        modList.add(new ClickGui());
        modList.add(new HUD());
        modList.add(new Sprint());
        modList.add(new NoSlowDown());
        modList.add(new Fullbright());
        modList.add(new DamageParticle());
        modList.add(new ItemESP());
        modList.add(new ESP());
        modList.add(new Velocity());
        modList.add(new BlockESP());
        modList.add(new ChestESP());
        modList.add(new AutoHeal());
        modList.add(new SpeedMine());
        modList.add(new AutoArmor());
        modList.add(new InvManager());
        modList.add(new AutoSword());
        modList.add(new RealBobbing());
        modList.add(new Scaffold());
        modList.add(new Speed());
        modList.add(new KillAura());
        modList.add(new AntiBot());
        modList.add(new Fly());
        modList.add(new Xray());
        modList.add(new NoFall());
        modList.add(new InvMove());
        modList.add(new AntiFall());
        modList.add(new Criticals());
        modList.add(new BlockAnimation());
        modList.add(new Disabler());
        modList.add(new AutoTool());
        modList.add(new AimAssist());
        modList.add(new AutoClicker());
        modList.add(new Hitbox());
        modList.add(new Reach());
        modList.add(new WTap());
        //MoveMent
        modList.add(new FakeLag());
        modList.add(new Jesus());
        modList.add(new Step());
        
        modList.add(new ItemPhysic());
        //player
        modList.add(new AntiAim());

        modList.add(new NameTag());
        modList.add(new Trajectory());
        modList.add(new ViewClip());
        
        modList.add(new AntiObsidian());
        modList.add(new AutoHypixel());
        modList.add(new FastPlace());
        modList.add(new MurderFinder());
        modList.add(new RotationAnimation());
    }

    public static ArrayList getEnabledModListHUD() {
        ArrayList<Mod> enabledModList = new ArrayList<Mod>();
        for (Mod m : modList) {
            if (!m.isEnabled()) continue;
            enabledModList.add(m);
        }
        return enabledModList;
    }

    public static Mod getModByName(String mod) {
        for (Mod m : modList) {
            if (!m.getName().equalsIgnoreCase(mod)) continue;
            return m;
        }
        return null;
    }

    public static ArrayList<Mod> getModList() {
        return modList;
    }

    public static Mod getMod(final Class<? extends Mod> clazz) {
        for (final Mod mod : ModManager.modList) {
            if (mod.getClass() == clazz) {
                return mod;
            }
        }
        return null;
    }


    public static ArrayList getEnabled() {
        final ArrayList<Mod> list = new ArrayList<Mod>();
        for (final Mod mod : ModManager.modList) {
            if (mod.isEnabled() && mod.getCategory() != Category.RENDER) {
                list.add(mod);
            }
        }
        return list;
    }

}

