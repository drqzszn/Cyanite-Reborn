package esu.cyanite.mod.mods.WORLD;

import com.darkmagician6.eventapi.EventTarget;

import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.value.Value;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class AntiObsidian extends Mod
{
    public static Value<String> mode;
    
    public AntiObsidian() {
        super("AntiObsidian", "Anti Obsidian", Category.WORLD);
        AntiObsidian.mode.addValue("GhostHand");
        AntiObsidian.mode.addValue("Break");
    }
    
    @EventTarget
    public void OnUpdate(final EventUpdate eventUpdate) {
        if (AntiObsidian.mode.isCurrentMode("Break") && this.mc.theWorld.getBlockState(new BlockPos(new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posZ))).getBlock() == Block.getBlockById(49)) {
            this.mc.playerController.onPlayerDestroyBlock(new BlockPos(new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ)), EnumFacing.UP);
        }
    }
    
    static {
        AntiObsidian.mode = new Value<String>("AntiObsidian", "Mode", 0);
    }
}
