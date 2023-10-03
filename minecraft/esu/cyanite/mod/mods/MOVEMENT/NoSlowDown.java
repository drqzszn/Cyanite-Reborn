/*
 * Decompiled with CFR 0_132 Helper by Lightcolour E-mail wyy-666@hotmail.com.
 */
package esu.cyanite.mod.mods.MOVEMENT;

import esu.cyanite.events.EventPostMotion;
import esu.cyanite.events.EventPreMotion;
import esu.cyanite.events.EventUpdate;
import esu.cyanite.events.PacketSendEvent;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.utils.MovementUtils;
import esu.cyanite.value.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowDown
extends Mod {
    public Value<String> mode = new Value("NoSlowDown", "Mode", 0);

    public NoSlowDown() {
        super("NoSlowDown", "No Slow Down", Category.MOVEMENT);
        this.mode.addValue("Vanilla");
    }



    public void onPacketSendEvent (PacketSendEvent e){
        if (mc.thePlayer == null) {
            return;
        }

        if (mc.thePlayer.isEating()
                && MovementUtils.isMoving() && e.getPacket() instanceof C08PacketPlayerBlockPlacement) {
        }
    }
}

