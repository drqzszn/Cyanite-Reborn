package esu.cyanite.mod.mods.WORLD;

import esu.cyanite.events.EventPacket;
import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class SpeedMine
extends Mod {
    private boolean bzs = false;
    private float bzx = 0.0f;
    public BlockPos blockPos;
    public EnumFacing facing;

    public SpeedMine() {
        super("SpeedMine", "Speed Mine", Category.WORLD);
    }

    @EventTarget
    public void onDamageBlock(EventPacket event) {
        if (event.packet instanceof C07PacketPlayerDigging && !Minecraft.playerController.extendedReach() && Minecraft.playerController != null) {
            C07PacketPlayerDigging c07PacketPlayerDigging = (C07PacketPlayerDigging)event.packet;
            if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                this.bzs = true;
                this.blockPos = c07PacketPlayerDigging.getPosition();
                this.facing = c07PacketPlayerDigging.getFacing();
                this.bzx = 0.0f;
            } else if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK || c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                this.bzs = false;
                this.blockPos = null;
                this.facing = null;
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (Minecraft.playerController.extendedReach()) {
            Minecraft.playerController.blockHitDelay = 0;
        } else if (this.bzs) {
            Block block = this.mc.theWorld.getBlockState(this.blockPos).getBlock();
            this.bzx += (float)((double)block.getPlayerRelativeBlockHardness(Minecraft.thePlayer, this.mc.theWorld, this.blockPos) * 1.4);
            if (this.bzx >= 1.0f) {
                this.mc.theWorld.setBlockState(this.blockPos, Blocks.air.getDefaultState(), 11);
                Minecraft.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.blockPos, this.facing));
                this.bzx = 0.0f;
                this.bzs = false;
            }
        }
    }
}

