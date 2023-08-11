package esu.cyanite.mod.mods.PLAYER;

import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.mod.ModManager;
import esu.cyanite.utils.time.TimeHelper;
import esu.cyanite.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.darkmagician6.eventapi.EventTarget;

public class Teams extends Mod
{
    public static Value<Boolean> clientfriend;
    TimeHelper timer;
    static boolean clientfriendOld;
    
    public Teams() {
        super("Teams", Category.PLAYER);
        this.timer = new TimeHelper();
    }
    
    @Override
    public void onDisable() {
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (this.timer.isDelayComplete(0x562137116AE1D1A1L ^ 0x562137116AE1DA19L) && this.mc.thePlayer != null) {
            String inGamename = this.mc.thePlayer.getName();
            if (inGamename == null || !inGamename.equalsIgnoreCase(this.mc.thePlayer.getName()) || Teams.clientfriendOld != Teams.clientfriend.getValueState()) {
                Teams.clientfriendOld = Teams.clientfriend.getValueState();
                final String name = this.mc.thePlayer.getName();
                String p_i267_2_;
                if (Teams.clientfriend.getValueState()) {
                    p_i267_2_ = "true";
                }
                else {
                    p_i267_2_ = "false";
                }
            }
            this.timer.reset();
        }
    }
    
    public static boolean isMod(final String s) {
        return false;
    }
    
	public static boolean isOnSameTeam2(Entity entity) {
		if(ModManager.getModByName("Teams").isEnabled()) {
			if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("\247")) {
				if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2
	                    || entity.getDisplayName().getUnformattedText().length() <= 2) {
					return false;
				}
				if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
	                return true;
	            }
			}
		}
		return false;
	}
    
    public static boolean isOnSameTeam(final EntityPlayer entityPlayer) {
        if (isMod(entityPlayer.getName())) {
            return true;
        }
        if (Teams.clientfriend.getValueState() && isClientFriend(entityPlayer)) {
            return true;
        }
        if (ModManager.getModByName("Teams").isEnabled() 
        	&& Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("�")) {
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2 
        	    || entityPlayer.getDisplayName().getUnformattedText().length() <= 2) {
                return false;
            }
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entityPlayer.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isClientFriend(final EntityPlayer entityPlayer) {
        return false;
    }
    
    
    static {
        Teams.clientfriend = new Value<Boolean>("Teams_ClientFriends", true);
    }
}
