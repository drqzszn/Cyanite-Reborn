package esu.cyanite.mod.mods.COMBAT;

import com.darkmagician6.eventapi.EventTarget;
import esu.cyanite.events.EventPreMotion;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.utils.time.TimeHelper;
import esu.cyanite.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

import java.util.Random;

public class AutoClicker extends Mod
{
    public Value<Boolean> randomcps;
    public Value<Double> cps;
    public Value<Boolean> unblock;
    Random random;
    TimeHelper timer;
    
    public AutoClicker() {
        super("AutoClicker", "Auto Clicker", Category.COMBAT);
        this.randomcps = new Value<Boolean>("AutoClicker_RandomCPS", true);
        this.cps = new Value<Double>("AutoClicker_CPS", 9.0, 1.0, 20.0, 1.0);
        this.unblock = new Value<Boolean>("AutoClicker_UnBlock", true);
        this.random = new Random();
        this.timer = new TimeHelper();
    }
    
    @EventTarget
    public void onMotion(EventPreMotion e) {
            final Random random = new Random();
            if (this.mc.gameSettings.keyBindAttack.pressed) {
                final TimeHelper timer = this.timer;
                final long n = (long)(1000.0 / this.cps.getValueState());
                int n2;
                if (this.randomcps.getValueState()) {
                    n2 = random.nextInt(100) - 60;
                }
                else {
                    n2 = 0;
                }
                if (timer.delay((double)(n + n2))) {
                    if (this.unblock.getValueState()) {
                        if (this.mc.thePlayer.isBlocking()) {
                            this.mc.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.UP));
                            this.mc.thePlayer.swingItem();
                            if (this.mc.objectMouseOver.entityHit instanceof Entity) {
                                this.mc.playerController.attackEntity(this.mc.thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
                            }
                        }
                        else {
                            this.mc.thePlayer.swingItem();
                            if (this.mc.objectMouseOver.entityHit instanceof Entity) {
                                this.mc.playerController.attackEntity(this.mc.thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
                            }
                        }
                    }
                    else {
                        this.mc.thePlayer.swingItem();
                        if (this.mc.objectMouseOver.entityHit instanceof Entity) {
                            this.mc.playerController.attackEntity(this.mc.thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
                        }
                    }
                    this.timer.reset();
                }
            }
    }
}
