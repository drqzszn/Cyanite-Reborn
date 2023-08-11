/*
 * Decompiled with CFR 0_132 Helper by Lightcolour E-mail wyy-666@hotmail.com.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package esu.cyanite.mod.mods.PLAYER;

import esu.cyanite.events.EventUpdate;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import com.darkmagician6.eventapi.EventTarget;
import java.util.Objects;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InvMove
extends Mod {
    public InvMove() {
        super("InvMove", "Inventory Move", Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        block3 : {
            KeyBinding[] moveKeys;
            block2 : {
                moveKeys = new KeyBinding[]{this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindJump, this.mc.gameSettings.keyBindSprint};
                if (!(this.mc.currentScreen instanceof GuiScreen) || this.mc.currentScreen instanceof GuiChat) break block2;
                for (KeyBinding key : moveKeys) {
                    key.pressed = Keyboard.isKeyDown((int)key.getKeyCode());
                }
                break block3;
            }
            if (!Objects.isNull(this.mc.currentScreen)) break block3;
            for (KeyBinding bind : moveKeys) {
                if (Keyboard.isKeyDown((int)bind.getKeyCode())) continue;
                KeyBinding.setKeyBindState(bind.getKeyCode(), false);
            }
        }
    }
}

