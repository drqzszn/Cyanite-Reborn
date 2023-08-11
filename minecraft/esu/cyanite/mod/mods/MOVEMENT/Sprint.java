/*
 * Decompiled with CFR 0_132 Helper by Lightcolour E-mail wyy-666@hotmail.com.
 */
package esu.cyanite.mod.mods.MOVEMENT;

import esu.cyanite.events.EventPreMotion;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.value.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;

public class Sprint
extends Mod {
    public Value<Boolean> omni = new Value<Boolean>("Sprint_Omni", true);

    public Sprint() {
        super("Sprint", "Sprint", Category.MOVEMENT);
    }

    @EventTarget
    public void onPre(EventPreMotion event) {
        if (!Minecraft.thePlayer.isCollidedHorizontally && !Minecraft.thePlayer.isSneaking() && Minecraft.thePlayer.getFoodStats().getFoodLevel() > 6 && (this.omni.getValueState() != false ? this.isMoving() : Minecraft.thePlayer.moveForward > 0.0f)) {
            Minecraft.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void onDisable() {
        Minecraft.thePlayer.setSprinting(false);
        super.onDisable();
    }

    public boolean isMoving() {
        return Minecraft.thePlayer.moveForward != 0.0f || Minecraft.thePlayer.moveStrafing != 0.0f;
    }
}

