package esu.cyanite.mod.mods.COMBAT;

import com.darkmagician6.eventapi.EventTarget;
import esu.cyanite.events.EventPacket;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class WTap extends Mod
{
    
    public WTap() {
        super("WTap", Category.COMBAT);
    }
    
    @EventTarget
    public void onPacket(EventPacket eventPacket) {
        if (eventPacket.getType() == EventPacket.EventPacketType.RECEIVE && this.mc.theWorld != null && this.mc.thePlayer != null && eventPacket.packet instanceof C02PacketUseEntity) {
            final C02PacketUseEntity c02PacketUseEntity = (C02PacketUseEntity)eventPacket.packet;
            if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK && c02PacketUseEntity.getEntityFromWorld(this.mc.theWorld) != this.mc.thePlayer && this.mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
                if (this.mc.thePlayer.isSprinting()) {
                    this.mc.thePlayer.setSprinting(false);
                    this.mc.thePlayer.setSprinting(true);
                }
                this.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
            }
        }
    }
}
