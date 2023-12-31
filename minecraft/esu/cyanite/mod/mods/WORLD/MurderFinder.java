package esu.cyanite.mod.mods.WORLD;

import com.darkmagician6.eventapi.EventTarget;

import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.ui.Notification;
import esu.cyanite.utils.HBTimer;
import esu.cyanite.utils.Type;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;

public class MurderFinder extends Mod
{
    HBTimer time;
    
    public MurderFinder() {
        super("MurderFinder", "Murder Finder", Category.WORLD);
        this.time = new HBTimer();
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        for (final Entity next : this.mc.theWorld.loadedEntityList) {
            if (!(next instanceof EntityPlayer) && next instanceof EntityPlayerSP) {
                final EntityPlayer entity = (EntityPlayer) next;
                int i = 0;
                while (i < 1) {
                    if (entity != null 
                	    && entity.getName() != this.mc.thePlayer.getName() 
                	    && (((EntityPlayer) entity).getHeldItem().getItem() == Items.iron_shovel 
                	    || entity.getHeldItem().getItem() == Items.stick 
                	    || entity.getHeldItem().getItem() == Items.wooden_axe 
                	    || entity.getHeldItem().getItem() instanceof ItemSword 
                	    || entity.getHeldItem().getItem() == Items.stone_shovel 
                	    || entity.getHeldItem().getItem() == Items.blaze_rod 
                	    || entity.getHeldItem().getItem() == Items.diamond_shovel 
                	    || entity.getHeldItem().getItem() == Items.feather 
                	    || entity.getHeldItem().getItem() == Items.carrot_on_a_stick 
                	    || entity.getHeldItem().getItem() == Items.bone 
                	    || entity.getHeldItem().getItem() == Items.golden_carrot 
                	    || entity.getHeldItem().getItem() == Items.diamond_hoe 
                	    || entity.getHeldItem().getItem() == Items.shears)) {
                	
                        Notification.tellPlayer("This is Murdurer: " + entity.getName().toUpperCase(), Type.WARN);
                    }
                    ++i;
                }
            }
        }
    }
}
