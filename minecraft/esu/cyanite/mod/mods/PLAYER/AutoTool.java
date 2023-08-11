/*
 * Decompiled with CFR 0_132 Helper by Lightcolour E-mail wyy-666@hotmail.com.
 */
package esu.cyanite.mod.mods.PLAYER;

import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class AutoTool
extends Mod {
    public AutoTool() {
        super("AutoTool", "Auto Tool", Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!this.mc.gameSettings.keyBindAttack.pressed) {
            return;
        }
        if (this.mc.objectMouseOver == null) {
            return;
        }
        BlockPos pos = this.mc.objectMouseOver.getBlockPos();
        if (pos == null) {
            return;
        }
        this.updateTool(pos);
    }

    public void updateTool(BlockPos pos) {
        Block block = this.mc.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0f;
        int bestItemIndex = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[i];
            if (itemStack == null || itemStack.getStrVsBlock(block) <= strength) continue;
            strength = itemStack.getStrVsBlock(block);
            bestItemIndex = i;
        }
        if (bestItemIndex != -1) {
            Minecraft.thePlayer.inventory.currentItem = bestItemIndex;
        }
    }
}

