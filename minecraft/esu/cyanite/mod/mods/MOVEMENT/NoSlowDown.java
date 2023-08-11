/*
 * Decompiled with CFR 0_132 Helper by Lightcolour E-mail wyy-666@hotmail.com.
 */
package esu.cyanite.mod.mods.MOVEMENT;

import esu.cyanite.events.EventPostMotion;
import esu.cyanite.events.EventPreMotion;
import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
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
        this.mode.addValue("NCP");
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (this.mode.isCurrentMode("Vanilla")) {
            this.setDisplayName("Vanilla");
        } else if (this.mode.isCurrentMode("NCP")) {
            this.setDisplayName("NCP");
        }
    }

    @EventTarget
    public void onPre(EventPreMotion event) {
        if (this.isMoving() && Minecraft.thePlayer.isBlocking() && this.mode.isCurrentMode("NCP")) {
            Minecraft.thePlayer.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }

    @EventTarget
    public void onPost(EventPostMotion event) {
        if (this.isMoving() && Minecraft.thePlayer.isBlocking() && this.mode.isCurrentMode("NCP")) {
            Minecraft.thePlayer.sendQueue.getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.getHeldItem()));
        }
    }

    public boolean isMoving() {
        return Minecraft.thePlayer.moveForward != 0.0f || Minecraft.thePlayer.moveStrafing != 0.0f;
    }
}

