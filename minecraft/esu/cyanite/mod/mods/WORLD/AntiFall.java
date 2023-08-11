/*
 * Decompiled with CFR 0_132 Helper by Lightcolour E-mail wyy-666@hotmail.com.
 */
package esu.cyanite.mod.mods.WORLD;

import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.mod.ModManager;
import esu.cyanite.utils.time.TimeHelper;
import esu.cyanite.value.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class AntiFall
extends Mod {
    BlockPos lastGroundPos;
    TimeHelper timer = new TimeHelper();
    TimeHelper timer2 = new TimeHelper();
    public Value<Double> fall = new Value<Double>("AntiFall_Distance", 8.0, 1.0, 10.0, 1.0);

    public AntiFall() {
        super("AntiFall", "Anti Fall", Category.WORLD);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (Minecraft.thePlayer.fallDistance >= this.fall.getValueState().floatValue() && !ModManager.getModByName("Fly").isEnabled() && this.mc.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ)).getBlock() == Blocks.air && !this.isBlockUnder() && this.timer.isDelayComplete(900L)) {
            if (this.timer.isDelayComplete(500L)) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 12.0, Minecraft.thePlayer.posZ, false));
            }
            this.timer.reset();
        }
    }

    private boolean isBlockUnder() {
        for (int i = (int)Minecraft.thePlayer.posY; i > 0; --i) {
            BlockPos pos = new BlockPos(Minecraft.thePlayer.posX, (double)i, Minecraft.thePlayer.posZ);
            if (this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }
}

