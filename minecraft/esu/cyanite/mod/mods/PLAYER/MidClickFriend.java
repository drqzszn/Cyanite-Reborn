package esu.cyanite.mod.mods.PLAYER;

import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.EventType;

import esu.cyanite.Client;
import esu.cyanite.events.EventPreMotion;
import esu.cyanite.friend.FriendsManager;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.utils.Friend;
import esu.cyanite.utils.handler.MouseInputHandler;
import net.minecraft.entity.player.EntityPlayer;

public class MidClickFriend extends Mod
{
    private MouseInputHandler handler;
    
    public MidClickFriend() {
        super("MCF", "Mid Click Friend", Category.PLAYER);
        this.handler = new MouseInputHandler(2);
    }
    
    @EventTarget
    public void onMotion(final EventPreMotion eventMotion) {
        if (eventMotion.getEventType() == EventType.PRE 
        	&& this.mc.objectMouseOver != null 
        	&& this.mc.objectMouseOver.entityHit != null 
        	&& this.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            final String name = this.mc.objectMouseOver.entityHit.getName();
            if (this.handler.canExcecute()) {
                if (FriendsManager.isFriend(name)) {
                    int i = 0;
                    while (i < FriendsManager.getFriends().size()) {
                        if (((Friend)FriendsManager.getFriends().get(i)).getName().equalsIgnoreCase(name)) {
                            FriendsManager.getFriends().remove(i);
                        }
                        ++i;
                    }
                }
                else {
                    FriendsManager.getFriends().add(new Friend(name));
                }
                Client.instance.fileMgr.saveFriends();
            }
        }
    }
}
