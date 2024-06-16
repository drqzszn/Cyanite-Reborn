package esu.cyanite.mod.mods.COMBAT;

import com.darkmagician6.eventapi.EventTarget;
import com.sun.javafx.tk.FontLoader;
import esu.cyanite.Client;
import esu.cyanite.events.EventMove;
import esu.cyanite.events.EventPreMotion;
import esu.cyanite.events.EventRender2D;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.mod.ModManager;
import esu.cyanite.utils.RotationUtils;
import esu.cyanite.utils.render.RenderUtil;
import esu.cyanite.utils.time.TimerUtil;
import esu.cyanite.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class KillAura extends Mod {

    public static final List<EntityLivingBase> targets = new ArrayList<>();
    public static boolean attacking;
    public static boolean blocking;
    public static boolean wasBlocking;
    private final TimerUtil debugDelay = new TimerUtil();
    private final TimerUtil attackTimer = new TimerUtil();
    private final TimerUtil switchTimer = new TimerUtil();

    public static Value<String> mode = new Value("KillAura", "Mode", 0);
    public static Value<Double> switchDelay = new Value<Double>("Switch Delay", 50.0d, 1.0d, 500.0d, 1.0d);;

    public static Value<Double> maxTargetAmount = new Value<Double>("Max Target Amount", 3.0d, 2.0d, 50.0d, 1.0d);;

    public static Value<Double> minCPS = new Value<Double>("Min CPS", 10.0d, 1.0d, 20.0d, 1.0d);;
    public static Value<Double> maxCPS = new Value<Double>("Max CPS", 10.0d, 1.0d, 20.0d, 1.0d);;

    public static Value<Double> reach = new Value<Double>("Reach", 10.0d, 3.0d, 20.0d, 0.1d);;

    public Value<Boolean> autoblock = new Value<Boolean>("Autoblock", false);;

    public static Value<String> autoblockMode;

    public Value<Boolean> rotations = new Value<Boolean>("Rotations", true);
    public static Value<String> rotationMode;
    public static Value<String> sortMode;

    /*private final MultipleBoolSetting addons = new MultipleBoolSetting("Addons",
            new BooleanSetting("Keep Sprint", true),
            new BooleanSetting("Through Walls", true),
            new BooleanSetting("Allow Scaffold", false),
            new BooleanSetting("Movement Fix", false),
            new BooleanSetting("Ray Cast", false));

    private final MultipleBoolSetting auraESP = new MultipleBoolSetting("Target ESP",
            new BooleanSetting("Circle", true),
            new BooleanSetting("Tracer", false),
            new BooleanSetting("Box", false),
            new BooleanSetting("Custom Color", false));
    private final Animation auraESPAnim = new DecelerateAnimation(300, 1);*/

    public EntityLivingBase lastEntity;
    public static EntityLivingBase target;
    private float yaw = 0;
    private int cps;
    private EntityLivingBase auraESPTarget;

    public KillAura() {
        super("KillAura", Category.COMBAT);
        mode.addValue("Single");
        mode.addValue("Multi");

        autoblockMode = new Value("KillAura", "Autoblock Mode", 0);
        autoblockMode.addValue("Watchdog");
        autoblockMode.addValue("Fake");
        autoblockMode.addValue("WatchdogTest");
        autoblockMode.addValue("Verus");
        autoblockMode.addValue("packet");
        autoblockMode.addValue("Keybind");
        autoblockMode.addValue("RightClick");
        autoblockMode.addValue("Vanilla");
        autoblockMode.addValue("AAC");
        autoblockMode.addValue("OldNCP");
        autoblockMode.addValue("DCJ");

        rotationMode = new Value("KillAura", "Rotation Mode", 0);
        rotationMode.addValue("Vanilla");
        rotationMode.addValue("Smooth");
        rotationMode.addValue("Less");

        sortMode = new Value("KillAura", "Sort Mode", 0);
        sortMode.addValue("Range");
        sortMode.addValue("Hurt Time");
        sortMode.addValue("Health");
        sortMode.addValue("Armor");

/*        autoblockMode.addParent(autoblock, a -> autoblock.isEnabled());
        rotationMode.addParent(rotations, r -> rotations.isEnabled());
        switchDelay.addParent(mode, m -> mode.is("Switch"));
        maxTargetAmount.addParent(mode, m -> mode.is("Multi"));
        customColor.addParent(auraESP, r -> r.isEnabled("Custom Color"));

        this.addSettings(mode, maxTargetAmount, switchDelay, minCPS, maxCPS, reach, autoblock, autoblockMode,
                rotations, rotationMode, sortMode, addons, auraESP, customColor);*/
    }

    public void sendPacketNoEvent(Packet packet) {
        sendPacket(packet, true);
    }

    public void sendPacket(Packet<?> packet, boolean silent) {
        if (mc.thePlayer != null) {
            mc.getNetHandler().getNetworkManager().sendPacket(packet);
        }
    }

    public void sendPacket(Packet packet) {
        sendPacket(packet, false);
    }

    @Override
    public void onDisable() {
        target = null;
        targets.clear();
        blocking = false;
        attacking = false;
        if (wasBlocking) {
            //PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        wasBlocking = false;
        super.onDisable();
    }

    @EventTarget
    public void onRender(EventRender2D event) {

        if (lastEntity == null)
            return;

        boolean checkWinning = lastEntity.getHealth() < mc.thePlayer.getHealth();
        String f = checkWinning ? "Win due to your health > target health" : "death, target health > your health";
        Client.instance.fontMgr.tahoma20.drawString(f,
                RenderUtil.width() / 2 -
                        Client.instance.fontMgr.tahoma20.getStringWidth(f) / 2, RenderUtil.height() / 2 - 20,
                !checkWinning ? new Color(255, 0, 0).getRGB() : new Color(0, 255, 0).getRGB());

        if (debugDelay.reach(500)) {
            //message ("Health : " + mc.thePlayer.getHealth() + "  target health : " + lastEntity.getHealth());
            debugDelay.reset();
        }
    }

    @EventTarget
    public void onPre(EventPreMotion event) {
        for(Object o : mc.theWorld.loadedEntityList){
            if(o instanceof EntityLivingBase){
                EntityLivingBase e = (EntityLivingBase) o;
                if(e != mc.thePlayer && e.hurtTime == 0 && e.isEntityAlive()){
                    if(mc.thePlayer.getDistanceSqToEntity(e) < 500){
                        //mc.thePlayer.setPosition(e.posX,e.posY,e.posZ);
                        mc.thePlayer.posX = e.posX;
                        mc.thePlayer.posY = e.posY;
                        mc.thePlayer.posZ = e.posZ;
                        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
                        mc.thePlayer.swingItem();
                    }
                }
            }
        }

        //this.setSuffix(mode.getMode());


        if (minCPS.getValueState() > maxCPS.getValueState()) {
            minCPS.setValueState(minCPS.getValueState() - 1);
        }

        // Gets all entities in specified range, sorts them using your specified sort mode, and adds them to target list
        sortTargets();

        if (/*event.isPre()*/true) {
            attacking = !targets.isEmpty() && (ModManager.getModByName("Scaffold").isEnabled());
            blocking = autoblock.getValueState() && attacking /*&& InventoryUtils.isHoldingSword()*/;
            if (attacking) {
                target = targets.get(0);

                if (rotations.getValueState()) {
                    float[] rotations = {0, 0};
                    switch (rotationMode.getValueState()) {
                        case "Vanilla":
                        case "Hypixel":
                            rotations = RotationUtils.getRotationsNeeded(target);
                            break;
                        case "Smooth":
                            rotations = RotationUtils.getSmoothRotations(target);
                            break;
                    }
                    yaw = event.getYaw();
                    event.yaw = rotations[0];
                    event.pitch = rotations[1];
                    Client.setRotation(event.getYaw(), event.getPitch());
                }

                if (RotationUtils.isMouseOver(event.getYaw(), event.getPitch(), target, reach.getValueState().floatValue()))
                    return;

                if (attackTimer.reach(cps)) {
                    final int maxValue = (int) ((minCPS.getValueMax() - maxCPS.getValueState()) * 20);
                    final int minValue = (int) ((minCPS.getValueMax() - minCPS.getValueState()) * 20);
                    cps = minValue;
                }

            } else {
                target = null;
                switchTimer.reset();
            }
        }

        if (blocking) {
            switch (autoblockMode.getValueState()) {
                case "Watchdog":
                    if (event.isPre() && wasBlocking && mc.thePlayer.ticksExisted % 4 == 0) {
                        //PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        wasBlocking = false;
                        //dev.tenacity.utils.player.ChatUtil.print("release use item");
                    }

                    break;
                case "WatchdogTest": {
                    if (event.isPre() && wasBlocking && mc.thePlayer.ticksExisted % 4 == 0) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                        wasBlocking = false;
                    }
                }
                case "Keybind":
                    if (blocking) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    }
                    break;
                case "RightClick": {
                    if (blocking) {
                        mc.thePlayer.getHeldItem().useItemRightClick(mc.theWorld,
                                mc.thePlayer);
                    }
                    break;
                }
                case "packet":
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                    break;
                case "Vanilla":
                    Minecraft.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                    Minecraft.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
                    break;
                case "DCJ":
                    if (autoblockMode.getValueState().equals("DCJ")) {
                        final EntityPlayerSP thePlayer = mc.thePlayer;
                        final ItemStack item = thePlayer.inventory.getCurrentItem();
                        thePlayer.setItemInUse(item, item.getMaxItemUseDuration());
                        sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1),
                                255, item, 0, 0, 0));
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    }
                    break;
                case "OldNCP":
                    if (autoblockMode.getValueState().equals("OldNCP")) {
                        sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                    }
                    break;
                case "AAC":
                    if (autoblockMode.getValueState().equals("AAC")) {
                        if (mc.thePlayer.ticksExisted % 2 == 0) {
                            mc.playerController.interactWithEntitySendPacket(mc.thePlayer, auraESPTarget);
                            sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                        }
                    }
                    break;
                case "Verus":
                    if (event.isPre()) {
                        wasBlocking = true;
                    }
                    break;
                case "Fake":
                    break;
            }
        } else if (wasBlocking && autoblockMode.getValueState().equals("Watchdog") && event.isPre()) {
            wasBlocking = false;
        }
    }

    private void sortTargets() {
        targets.clear();
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (mc.thePlayer.getDistanceToEntity(entity) <= reach.getValueState() && isValid(entity) && mc.thePlayer != entityLivingBase ) {
                    targets.add(entityLivingBase);
                }
            }
        }
        switch (sortMode.getValueState()) {
            case "Range":
                targets.sort(Comparator.comparingDouble(mc.thePlayer::getDistanceToEntity));
                break;
            case "Hurt Time":
                targets.sort(Comparator.comparingDouble(Minecraft.thePlayer::getDistanceToEntity));
                break;
            case "Health":
                targets.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
                break;
            case "Armor":
                targets.sort(Comparator.comparingInt(EntityLivingBase::getTotalArmorValue));
                break;
        }
    }

    public boolean isValid(Entity entity) {
        return false;
    }

    @EventTarget
    public void onPlayerMoveUpdateEvent(EventPreMotion event) {
        if(false){
            event.setYaw(yaw);
        }

    }
}
