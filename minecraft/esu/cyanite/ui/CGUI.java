package esu.cyanite.ui;

import esu.cyanite.Client;
import esu.cyanite.mod.Category;
import esu.cyanite.mod.Mod;
import esu.cyanite.mod.ModManager;
import esu.cyanite.ui.ClickGUI.ClickMenu;
import esu.cyanite.ui.ClickGUI.UIMenuMods;
import esu.cyanite.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class CGUI extends GuiScreen {
    public static float x, y;
    public static float width = 356, height = 220;
    public static ClickMenu menu;
    static Category category = Category.COMBAT;
    static float categoryanimy = 64;
    static float lastcategoryanimy = 64;
    static float[] fuckalpha = new float[5];
    int check = 0; // 1 2 3 4 5
    int keydownX, keydownY;
    float alpha;
    float[] calpha = new float[5];
    float esualpha;
    float temp;
    float temp1;
    float tempfff;
    float sfuck;
    float afuck;
    float smfuck;

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        if (menu.settingMode) {
            return false;
        }
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    @Override
    public void initGui() {
        super.initGui();
        menu = new ClickMenu();
    }

    @Override
    public void onGuiClosed() {
        ModManager.getModByName("ClickGui").set(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!ModManager.getModByName("ClickGui").isEnabled()) {
            this.mc.displayGuiScreen(null);
        }

        float drawx = x;
        float drawy = y;
        float drawx1 = x + width;
        float drawy1 = y + height;

        if (check == 1) {
            alpha = RenderUtil.toanim(alpha, 255, 16, 0.1f);
        } else {
            alpha = RenderUtil.toanim(alpha, 0, 16, 0.1f);
        }

        RenderUtil.drawRoundRect1(x - 1, y - 1, drawx1 + 1, drawy1 + 1, 4, new Color(255, 255, 255, (int) alpha));

        RenderUtil.drawRoundRect1(x, y, drawx1, drawy1, 4, new Color(39, 39, 39));

        //RenderUtil.drawRect((int) drawx, (int) drawy, (int) (drawx + 110), (int) (drawy + 35), new Color(255, 255, 255, 64));
        if (isHovered((int) drawx, (int) drawy, (int) (drawx + 110), (int) (drawy + 35), mouseX, mouseY) && check == 0 && Mouse.isButtonDown(0)) {
            check = 1;
            keydownX = (int) (mouseX - x);
            keydownY = (int) (mouseY - y);
        }

        float cdrawx = drawx + 6;
        float cdrawy = drawy + 64;
        int i = 0;
        for (Category c : Category.values()) {
            if (calpha[i] == 0) {
                calpha[i] = 390;
            }
            if (isHovered(cdrawx, cdrawy, cdrawx + 100, cdrawy + 20, mouseX, mouseY) && check == 0) {
                calpha[i] = RenderUtil.toanim(calpha[i], 690, 20, 1f);
            } else {
                calpha[i] = RenderUtil.toanim(calpha[i], 390, 20, 1f);
            }
            RenderUtil.drawRoundRect1(cdrawx, cdrawy, cdrawx + 100, cdrawy + 20, 4, new Color((int) (calpha[i] / 10f), (int) (calpha[i] / 10f), (int) (calpha[i] / 10f)));

            if (isHovered(cdrawx, cdrawy, cdrawx + 100, cdrawy + 20, mouseX, mouseY) && check == 0 && Mouse.isButtonDown(0)) {
                if (c != category) {
                    esualpha = 2550;
                    temp = 0;
                    temp1 = 0;
                }
                lastcategoryanimy = cdrawy - drawy;
                category = c;
                check = 2;
            }
            i++;
            cdrawy += 24;
        }

        i = 0;
        cdrawy = drawy + 64;
        cdrawx = drawx + 6;
        for (Category c : Category.values()) {
            if (fuckalpha[i] == 0) {
                fuckalpha[i] = 173;
            }
            if (c == category) {
                if (categoryanimy == 0) {
                    categoryanimy = lastcategoryanimy;
                }
                categoryanimy = RenderUtil.toanim2(categoryanimy, lastcategoryanimy, cdrawy - drawy, 10, 0.5f, 1f);
                //RenderUtil.drawRoundRect1((int)cdrawx, (int)(categoryanimy + drawy), (int)(cdrawx + 100), (int)(categoryanimy + 20 + drawy), 4, new Color(98, 109, 255));

                ResourceLocation rr = new ResourceLocation("client/roundrect.png");
                Minecraft.getMinecraft().getTextureManager().bindTexture(rr);
                RenderUtil.drawImage(rr, (int) cdrawx, (int) (categoryanimy + drawy), (int) (100), (int) (20));

                ResourceLocation logo = new ResourceLocation("client/logo.png");
                Minecraft.getMinecraft().getTextureManager().bindTexture(logo);
                RenderUtil.drawImage(logo, (int) (drawx + 10), (int) (drawy + 16), (int) (82), (int) (32));
            }
            i++;
            cdrawy += 24;
        }

        i = 0;
        cdrawy = drawy + 64;
        cdrawx = drawx + 6;
        for (Category c : Category.values()) {
            if (c == category) {
                fuckalpha[i] = RenderUtil.toanim(fuckalpha[i], 255, 16, 0.1f);
            } else {
                fuckalpha[i] = RenderUtil.toanim(fuckalpha[i], 173, 16, 0.1f);
            }

            ResourceLocation l = new ResourceLocation("client/" + String.valueOf(i) + ".png");
            Minecraft.getMinecraft().getTextureManager().bindTexture(l);
            RenderUtil.drawImage(l, (int) cdrawx + 11, (int) (cdrawy + 4), (int) (12), (int) (12), new Color((int) fuckalpha[i], (int) fuckalpha[i], (int) fuckalpha[i]));
            Client.instance.fontMgr.tahoma16.drawString(c.name(), cdrawx + 30, cdrawy + 5, new Color((int) fuckalpha[i], (int) fuckalpha[i], (int) fuckalpha[i]).getRGB());
            i++;
            cdrawy += 24;
        }

        float listx = drawx + 114;
        float listy = drawy + 10;

        if (isHovered(listx - 5, drawy + 5, drawx1, drawy1 - 5, mouseX, mouseY)) {
            int dwheel = Mouse.getDWheel();

            if (dwheel < 0) {
                temp = temp1 - 32;
            }
            if (dwheel > 0) {
                temp = temp1 + 32;
            }
        }

        int fff = 0;
        for (Mod m : ModManager.getModList()) {
            if (m.getCategory() == category) {
                fff += 40;
            }
        }

        fff -= 205;

        if (temp > 0) {
            temp = 0;
        }
        if (temp < -fff) {
            temp = -fff;
        }

        temp1 = RenderUtil.toanim(temp1, temp, 8, 0.1f);
        listy += temp1;

        if (sfuck == 0) {
            sfuck = 128;
        }

        if (isHovered(drawx1 - 4, (temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5, drawx1 - 1, (temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5 + (210f / (fff + 205)) * 210, mouseX, mouseY) || check == 5) {
            sfuck = RenderUtil.toanim(sfuck, 200, 8, 0.1f);
            afuck = RenderUtil.toanim(afuck, 200, 8, 0.1f);
        } else {
            sfuck = RenderUtil.toanim(sfuck, 128, 8, 0.1f);
            afuck = RenderUtil.toanim(afuck, 0, 8, 0.1f);
        }


        float afuckt = (int) afuck;

        if(smfuck == 0){
            smfuck = 1;
        }
        if (check != 5) {
            smfuck = RenderUtil.toanim(smfuck, 1.5f, 8, 0.05f);
        } else {
            smfuck = RenderUtil.toanim(smfuck, 1, 8, 0.05f);
        }
        float lllp = (int) ((sfuck/smfuck - afuck/smfuck) / (255f - afuck/smfuck) * 255f);
        afuckt /= smfuck;

        RenderUtil.drawRoundRect1(drawx1 - 4 + 1, (temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5, drawx1 - 1 - 1, (temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5 + (210f / (fff + 205)) * 210, 1, new Color(255, 255, 255, (int) lllp));
        RenderUtil.drawRoundRect1(drawx1 - 4, (temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5, drawx1 - 1, (temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5 + (210f / (fff + 205)) * 210, 1, new Color(255, 255, 255, (int) afuckt));

        if (isHovered(drawx1 - 4, (temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5, drawx1 - 1, (temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5 + (210f / (fff + 205)) * 210, mouseX, mouseY) && check == 0 && Mouse.isButtonDown(0)) {
            check = 5;
            keydownY = (int) (mouseY - ((temp1 / -fff) * (210 - ((210f / (fff + 205)) * 210)) + drawy + 5));
            tempfff = fff;
        }

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.doGlScissor(listx - 5, drawy + 5, drawx1, drawy1 - 5);

        for (Mod m : ModManager.getModList()) {
            if (m.getCategory() == category) {
                if (m.getAlpha1() == 0) {
                    m.setAlpha1(66);
                }
                if (isHovered(listx, listy, drawx1 - 5, listy + 36, mouseX, mouseY) && isHovered(listx - 5, drawy + 5, drawx1, drawy1 - 5, mouseX, mouseY) && check == 0) {
                    m.setAlpha1(RenderUtil.toanim(m.getAlpha1(), 165, 16, 0.1f));
                } else {
                    m.setAlpha1(RenderUtil.toanim(m.getAlpha1(), 66, 16, 0.1f));
                }
                RenderUtil.drawRoundRect1(listx, listy, drawx1 - 5, listy + 36, 5, new Color((int) m.getAlpha1(), (int) m.getAlpha1(), (int) m.getAlpha1()));

                if (m.getAlpha2() == 0) {
                    m.setAlpha2(40);
                }
                if (m.isEnabled()) {
                    m.setAlpha2(RenderUtil.toanim(m.getAlpha2(), 66, 8, 0.1f));
                } else {
                    m.setAlpha2(RenderUtil.toanim(m.getAlpha2(), 40, 8, 0.1f));
                }
                RenderUtil.drawRoundRect1(listx + 1, listy + 1, drawx1 - 6, listy + 35, 4, new Color((int) m.getAlpha2(), (int) m.getAlpha2(), (int) m.getAlpha2()));

                if (m.getAlpha3() == 0) {
                    m.setAlpha3(255);
                }
                if (m.isEnabled()) {
                    m.setAlpha3(RenderUtil.toanim(m.getAlpha3(), 255, 8, 0.1f));
                } else {
                    m.setAlpha3(RenderUtil.toanim(m.getAlpha3(), 156, 8, 0.1f));
                }
                Client.instance.fontMgr.tahoma16.drawString(m.getName(), listx + 36, listy + 12, new Color((int) m.getAlpha3(), (int) m.getAlpha3(), (int) m.getAlpha3()).getRGB());
                ResourceLocation l = new ResourceLocation("client/interface.png");
                Minecraft.getMinecraft().getTextureManager().bindTexture(l);
                RenderUtil.drawImage(l, (int) listx + 10, (int) (listy + 10), (int) (14), (int) (14), new Color((int) m.getAlpha3(), (int) m.getAlpha3(), (int) m.getAlpha3()));

                if (isHovered(listx - 5, drawy + 5, drawx1, drawy1 - 5, mouseX, mouseY) && check == 0) {
                    if (isHovered(listx, listy, drawx1 - 5, listy + 36, mouseX, mouseY) && check == 0 && Mouse.isButtonDown(0)) {
                        check = 3;
                        m.set(!m.isEnabled());
                    }

                    if (isHovered(listx, listy, drawx1 - 5, listy + 36, mouseX, mouseY) && check == 0 && Mouse.isButtonDown(1)) {
                        check = 4;
                        menu.currentMod = m;
                        menu.settingMode = true;
                        UIMenuMods.fuck = true;
                        //Minecraft.getMinecraft().displayGuiScreen(Client.instance.crink);
                    }
                }
                listy += 40;
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        menu.draw(mouseX, mouseY);

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();

        esualpha = RenderUtil.toanim(esualpha, 0, 16, 0.1f);
        RenderUtil.drawRect(listx - 5, drawy + 5, drawx1, drawy1 - 5, new Color(39, 39, 39, (int) (esualpha / 10f)).getRGB());

        check(mouseX, mouseY);
    }

    private void check(int mouseX, int mouseY) {
        if (!Mouse.isButtonDown(0)) {
            check = 0;
        }

        if (Mouse.isButtonDown(0) && check == 0) {
            check = -1;
        }

        if (check == 1) {
            x = mouseX - keydownX;
            y = mouseY - keydownY;
        }

        if (check == 5) {
            temp = (mouseY - keydownY - y - 5) / (210 - ((210f / (tempfff + 205)) * 210)) * (-tempfff);
            if (temp > 0) {
                temp = 0;
            }
            if (temp < -tempfff) {
                temp = -tempfff;
            }
            //temp1 = temp;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        menu.mouseClick(mouseX, mouseY);
    }
}
