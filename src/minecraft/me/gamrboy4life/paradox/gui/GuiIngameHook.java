package me.gamrboy4life.paradox.gui;

import java.util.HashSet;

import com.google.common.collect.Sets;

import de.Hero.clickgui.ClickGUI;
import me.gamrboy4life.paradox.Paradox;
import me.gamrboy4life.paradox.module.Module;
import me.gamrboy4life.paradox.utils.ColorUtils;
import me.gamrboy4life.paradox.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class GuiIngameHook extends GuiIngame {

    public GuiIngameHook(Minecraft mcIn) {
        super(mcIn);
    }

    public void renderGameOverlay(float p_175180_1_) {
        super.renderGameOverlay(p_175180_1_);
        
        // 右上にオンになっているmodを表示（コンパクト版）
        renderCompactModList();
    }

    private HashSet<String> modBlackList = Sets.newHashSet(ClickGUI.class.getName());

    public boolean isModBlackListed(Module m) {
        return modBlackList.contains(m.getClass().getName());
    }

    private void renderCompactModList() {
        int yCount = 0;
        int index = 0;
        long x = 0;
        
        for (Module m : Paradox.instance.moduleManager.getModules()) {
            m.onRender();

            ScaledResolution sr = new ScaledResolution(mc);
            double offset = yCount * (Wrapper.fr.FONT_HEIGHT * 0.6f + 2); // フォントサイズに合わせて間隔を調整

            if (m.isToggled()) {
                if (!modBlackList.contains(m.getClass().getName())) {
                    int textWidth = (int)(Wrapper.fr.getStringWidth("> " + m.getName()) * 0.6f);
                    int panelX = sr.getScaledWidth() - textWidth - 2;
                    int panelY = (int) offset;

                    // シンプルなテキスト
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(0.6f, 0.6f, 1.0f);
                    
                    // クールなカラーパレット
                    int textColor = getCoolColor(index);
                    
                    // メインテキスト
                    Wrapper.fr.drawStringWithShadow("> " + m.getName(), (panelX) / 0.6f, panelY / 0.6f, textColor);
                    
                    GlStateManager.popMatrix();
                    
                    yCount++;
                    index++;
                    x++;
                }
            }
        }
    }
    
    private int getCoolColor(int index) {
        // クールなカラーパレット（青系、紫系、シアン系）
        int[] coolColors = {
            0xFF00BFFF, // Deep Sky Blue
            0xFF4169E1, // Royal Blue
            0xFF9370DB, // Medium Purple
            0xFF00CED1, // Dark Turquoise
            0xFF1E90FF, // Dodger Blue
            0xFF8A2BE2, // Blue Violet
            0xFF00FA9A, // Medium Spring Green
            0xFF20B2AA, // Light Sea Green
            0xFF6495ED, // Cornflower Blue
            0xFF7B68EE  // Medium Slate Blue
        };
        return coolColors[index % coolColors.length];
    }

}