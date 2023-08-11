package esu.cyanite.ui.ClickGUI.option;


import esu.cyanite.Client;
import esu.cyanite.ui.ClickGUI.ClickMenu;
import esu.cyanite.ui.ClickGUI.button.Button;
import esu.cyanite.value.Value;

public class UIToggleButton extends Button{
   public Value value;
   public ClickMenu clickmenu;
   public String name;
   public boolean state;

   public UIToggleButton(ClickMenu clickmenu, String name, boolean state, Value value) {
       super(name, state);
      this.clickmenu = clickmenu;
      this.value = value;
      this.name = name;
      this.state = state;
   }

   public void onPress() {
      if (this.parent != null) {
         if (this.parent.equals(Client.instance.crink.menu.currentMod.getName())) {
            Value var10000 = this.value;
            var10000.setValueState(!((Boolean)var10000.getValueState()));
            Client.instance.fileMgr.saveValues();
            super.onPress();
         }
      }
   }
   
   
}
