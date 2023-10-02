import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

import de.florianmichael.viamcp.ViaMCP;
import net.minecraft.client.main.Main;

public class Start
{
	
	// Src By liaic OptiFine_1.8.9_HD_U_I7.
	// Go to optifine/Config.java to see optifine version.
	
    public static void main(String[] args)
    {
        try {
            ViaMCP.create();

            // In case you want a version slider like in the Minecraft options, you can use this code here, please choose one of those:

            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
            ViaMCP.INSTANCE.initAsyncSlider();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Main.main(concat(new String[] {"--version", "mcp", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second)
    {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
