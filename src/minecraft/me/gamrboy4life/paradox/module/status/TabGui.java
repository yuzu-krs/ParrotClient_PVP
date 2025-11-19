package me.gamrboy4life.paradox.module.status;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.gamrboy4life.paradox.Paradox;
import me.gamrboy4life.paradox.module.Category;
import me.gamrboy4life.paradox.module.Module;
import me.gamrboy4life.paradox.utils.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class TabGui extends Module {

    // ===== モダン配色 =====
    private static final int BG_TOP = 0xCC141414;       // 深い黒 (上)
    private static final int BG_BOTTOM = 0xCC1E1E1E;    // やや明るい黒 (下)
    private static final int BORDER_COLOR = 0xFF2F81F7; // アクセントブルー
    private static final int HIGHLIGHT = 0x802F81F7;    // 半透明青

    private static final int TEXT_ACTIVE = 0xFFFFFFFF;       // 選択中 OFF
    private static final int TEXT_ACTIVE_CAT = 0xFFEFEFEF;   // カテゴリ選択色
    private static final int TEXT_ON = 0xFF2F81F7;           // モジュールON
    private static final int TEXT_OFF = 0xFFBBBBBB;          // モジュールOFF

    // ===== 状態管理 =====
    public int currentTab;
    public boolean expanded;
    private long animationStartTime;
    private float animationProgress;

    public TabGui() {
        super("TabGui", 0, Category.STATUS);
        toggled = true;
        animationStartTime = System.currentTimeMillis();
    }

    
    public void draw() {
        if (this.isToggled()) {
            updateAnimation();
            drawModernTabGui();
        }
    }

    private void updateAnimation() {
        long currentTime = System.currentTimeMillis();
        animationProgress = Math.min(1.0f, (currentTime - animationStartTime) / 300.0f);
    }

    private void drawModernTabGui() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        drawCategoryPanel();
        if (expanded) drawModulePanel();

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    // =====================================================================
    // CATEGORY PANEL
    // =====================================================================

    private void drawCategoryPanel() {
        int panelX = 4;
        int panelY = 4;
        int panelWidth = 70;
        int panelHeight = Category.values().length * 12 + 6;

        drawGradientRect(panelX, panelY, panelX + panelWidth, panelY + panelHeight,
                BG_TOP, BG_BOTTOM);

        drawBorder(panelX, panelY, panelX + panelWidth, panelY + panelHeight);

        if (currentTab >= 0 && currentTab < Category.values().length) {
            int highlightY = panelY + 3 + currentTab * 12;
            drawHighlight(panelX + 2, highlightY, panelX + panelWidth - 2, highlightY + 10);
        }

        int count = 0;
        for (Category c : Category.values()) {
            int textY = panelY + 4 + count * 12;
            int textColor = (count == currentTab) ? TEXT_ACTIVE_CAT : TEXT_OFF;

            Wrapper.fr.drawStringWithShadow(c.name, panelX + 6, textY, textColor);
            count++;
        }
    }

    // =====================================================================
    // MODULE PANEL
    // =====================================================================

    private void drawModulePanel() {
        Category category = Category.values()[currentTab];
        List<Module> modules = Paradox.instance.moduleManager.getModulesbyCategory(category);

        if (modules.isEmpty()) return;

        int maxLen = 0;
        for (Module m : modules) {
            maxLen = Math.max(maxLen, Wrapper.fr.getStringWidth(m.name));
        }

        int panelX = 78;
        int panelY = 4;
        int panelWidth = maxLen + 20;
        int panelHeight = modules.size() * 12 + 6;

        drawGradientRect(panelX, panelY, panelX + panelWidth, panelY + panelHeight,
                BG_TOP, BG_BOTTOM);

        drawBorder(panelX, panelY, panelX + panelWidth, panelY + panelHeight);

        if (category.moduleIndex >= 0 && category.moduleIndex < modules.size()) {
            int highlightY = panelY + 3 + category.moduleIndex * 12;
            drawHighlight(panelX + 2, highlightY, panelX + panelWidth - 2, highlightY + 10);
        }

        int count = 0;
        for (Module m : modules) {
            int textY = panelY + 4 + count * 12;

            // ====== ★ ここが今回の修正の核心 ★ ======
            int textColor;
            if (count == category.moduleIndex) {
                textColor = m.isToggled() ? TEXT_ON : TEXT_ACTIVE;
            } else {
                textColor = m.isToggled() ? TEXT_ON : TEXT_OFF;
            }
            // ===========================================

            Wrapper.fr.drawStringWithShadow(m.name, panelX + 6, textY, textColor);
            count++;
        }
    }

    // =====================================================================
    // DRAW HELPERS
    // =====================================================================

    private void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor4f((startColor >> 16 & 255) / 255F, (startColor >> 8 & 255) / 255F, (startColor & 255) / 255F, (startColor >> 24 & 255) / 255F);
        GL11.glVertex2f(right, top);
        GL11.glVertex2f(left, top);
        GL11.glColor4f((endColor >> 16 & 255) / 255F, (endColor >> 8 & 255) / 255F, (endColor & 255) / 255F, (endColor >> 24 & 255) / 255F);
        GL11.glVertex2f(left, bottom);
        GL11.glVertex2f(right, bottom);
        GL11.glEnd();

        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    private void drawBorder(int left, int top, int right, int bottom) {
        Gui.drawRect(left - 1, top - 1, right + 1, top, BORDER_COLOR);
        Gui.drawRect(left - 1, bottom - 1, right + 1, bottom, BORDER_COLOR);
        Gui.drawRect(left - 1, top, left, bottom, BORDER_COLOR);
        Gui.drawRect(right, top, right + 1, bottom, BORDER_COLOR);
    }

    private void drawHighlight(int left, int top, int right, int bottom) {
        drawGradientRect(left, top, right, bottom, HIGHLIGHT, HIGHLIGHT);
    }

    // =====================================================================
    // KEY INPUT
    // =====================================================================

    public void keyPressed(int k) {
        Category category = Category.values()[currentTab];
        List<Module> modules = Paradox.instance.moduleManager.getModulesbyCategory(category);

        animationStartTime = System.currentTimeMillis();

        switch (k) {
            case Keyboard.KEY_UP:
                if (expanded) {
                    category.moduleIndex = (category.moduleIndex <= 0)
                            ? modules.size() - 1
                            : category.moduleIndex - 1;
                } else {
                    currentTab = (currentTab <= 0)
                            ? Category.values().length - 1
                            : currentTab - 1;
                }
                break;

            case Keyboard.KEY_DOWN:
                if (expanded) {
                    category.moduleIndex = (category.moduleIndex >= modules.size() - 1)
                            ? 0
                            : category.moduleIndex + 1;
                } else {
                    currentTab = (currentTab >= Category.values().length - 1)
                            ? 0
                            : currentTab + 1;
                }
                break;

            case Keyboard.KEY_RIGHT:
                if (expanded && !modules.isEmpty()) {
                    Module m = modules.get(category.moduleIndex);
                    if (!m.name.equals("TabGui"))
                        m.toggle();
                } else {
                    if (!modules.isEmpty()) {
                        expanded = true;
                        category.moduleIndex = 0;
                    }
                }
                break;

            case Keyboard.KEY_LEFT:
                expanded = false;
                break;
        }
    }
}