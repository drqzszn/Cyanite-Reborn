package esu.cyanite.mod.mods.WORLD;

import esu.cyanite.Client;
import esu.cyanite.events.EventChat;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.ui.Notification;
import esu.cyanite.utils.color.Colors;
import esu.cyanite.utils.render.RenderUtil;
import esu.cyanite.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class AutoHypixel extends Mod
{
    public Value<Boolean> gg = new Value<Boolean>("AutoHypixel_GG", true);
    public Value<Boolean> play = new Value<Boolean>("AutoHypixel_AutoPlay", true);
    public float animationY;
    public float animationY2;


    public AutoHypixel() {
        super("AutoHypixel", Category.WORLD);
    }



    public void onChat(EventChat c) {
        if (c.getMessage().contains("Winner - ") || c.getMessage().contains("\u7b2c\u4e00\u540d - ") || c.getMessage().contains("\u80dc\u5229 -")) {
            if (this.gg.getValueState().booleanValue()) {
                mc.thePlayer.sendChatMessage("/ac GG");
            }
            if (this.play.getValueState().booleanValue()) {
                mc.thePlayer.sendChatMessage("/play solo_insane");
            }

        }
    }
    @Override
    public void onEnable() {
        super.onEnable();

    }
    @Override
    public void onDisable() {
        super.onDisable();

    }
}
