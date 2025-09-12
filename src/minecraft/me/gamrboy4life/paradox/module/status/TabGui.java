package me.gamrboy4life.paradox.module.status;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.gamrboy4life.paradox.Paradox;
import me.gamrboy4life.paradox.module.Category;
import me.gamrboy4life.paradox.module.Module;
import me.gamrboy4life.paradox.utils.ColorUtils;
import me.gamrboy4life.paradox.utils.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class TabGui extends Module{
	
	public int currentTab;
	public boolean expanded;
	private long animationStartTime;
	private float animationProgress;
	private int hoveredTab = -1;
	private int hoveredModule = -1;

	public TabGui() {
		super("TabGui",0,Category.STATUS);
		toggled=true;
		animationStartTime = System.currentTimeMillis();
	}
	
	public void draw() {
		if(this.isToggled()) {
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
		
		// メインカテゴリパネル
		drawCategoryPanel();
		
		if(expanded) {
			drawModulePanel();
		}
		
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	
	private void drawCategoryPanel() {
		int panelX = 2; // 左上角に詰める
		int panelY = 2; // 左上角に詰める
		int panelWidth = 60; // 幅を縮小
		int panelHeight = Category.values().length * 12 + 6; // 高さを縮小
		
		// 背景グラデーション（より明るく）
		drawGradientRect(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 
			0xF0000000, 0xE8000000);
		
		// クールな青ベースのボーダー
		drawCoolBlueBorder(panelX, panelY, panelX + panelWidth, panelY + panelHeight);
		
		// 選択されたタブのハイライト
		if(currentTab >= 0 && currentTab < Category.values().length) {
			int highlightY = panelY + 3 + currentTab * 12;
			drawCoolBlueHighlight(panelX + 2, highlightY, panelX + panelWidth - 2, highlightY + 10);
		}
		
		// カテゴリ名を描画
		int count = 0;
		for(Category c : Category.values()) {
			int textY = panelY + 4 + count * 12;
			int textColor = (count == currentTab) ? 0xFFFFFFFF : getCoolBlueColor(count);
			
			// 影効果
			Wrapper.fr.drawStringWithShadow(c.name, panelX + 4, textY, textColor);
			count++;
		}
	}
	
	private void drawModulePanel() {
		Category category = Category.values()[currentTab];
		List<Module> modules = Paradox.instance.moduleManager.getModulesbyCategory(category);
		
		if(modules.size() == 0) {
			return;
		}
		
		// 最大幅を計算
		int maxLenModule = 0;
		for(Module module : modules) {
			if(Wrapper.fr.getStringWidth(module.name) > maxLenModule) {
				maxLenModule = Wrapper.fr.getStringWidth(module.name);
			}
		}
		
		int panelX = 67; // カテゴリパネルの右側に配置
		int panelY = 2; // 左上角に詰める
		int panelWidth = maxLenModule + 15; // 幅を縮小
		int panelHeight = modules.size() * 12 + 6; // 高さを縮小
		
		// 背景グラデーション（より明るく）
		drawGradientRect(panelX, panelY, panelX + panelWidth, panelY + panelHeight,
			0xF0000000, 0xE8000000);
		
		// クールな青ベースのボーダー
		drawCoolBlueBorder(panelX, panelY, panelX + panelWidth, panelY + panelHeight);
		
		// 選択されたモジュールのハイライト
		if(category.moduleIndex >= 0 && category.moduleIndex < modules.size()) {
			int highlightY = panelY + 3 + category.moduleIndex * 12;
			drawCoolBlueHighlight(panelX + 2, highlightY, panelX + panelWidth - 2, highlightY + 10);
		}
		
		// モジュール名を描画
		int count = 0;
		for(Module m : modules) {
			int textY = panelY + 4 + count * 12;
			int textColor;
			
			// 選択されている場合は白、それ以外はオン/オフ状態に応じて色を変更
			if(count == category.moduleIndex) {
				textColor = 0xFFFFFFFF; // 選択時は白
			} else if(m.isToggled()) {
				textColor = 0xFF4A90E2; // オン時は青
			} else {
				textColor = 0xFF888888; // オフ時は灰色
			}
			
			// 影効果
			Wrapper.fr.drawStringWithShadow(m.name, panelX + 4, textY, textColor);
			count++;
		}
	}
	
	private void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		
		GL11.glBegin(7);
		GL11.glColor4f((float)(startColor >> 16 & 255) / 255.0F, (float)(startColor >> 8 & 255) / 255.0F, (float)(startColor & 255) / 255.0F, (float)(startColor >> 24 & 255) / 255.0F);
		GL11.glVertex2f((float)right, (float)top);
		GL11.glVertex2f((float)left, (float)top);
		GL11.glColor4f((float)(endColor >> 16 & 255) / 255.0F, (float)(endColor >> 8 & 255) / 255.0F, (float)(endColor & 255) / 255.0F, (float)(endColor >> 24 & 255) / 255.0F);
		GL11.glVertex2f((float)left, (float)bottom);
		GL11.glVertex2f((float)right, (float)bottom);
		GL11.glEnd();
		
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}
	
	private void drawCoolBlueBorder(int left, int top, int right, int bottom) {
		int color = 0xFF6BB6FF; // より明るい青
		
		// 上
		Gui.drawRect(left - 1, top - 1, right + 1, top, color);
		// 下
		Gui.drawRect(left - 1, bottom - 1, right + 1, bottom, color);
		// 左
		Gui.drawRect(left - 1, top, left, bottom, color);
		// 右
		Gui.drawRect(right, top, right + 1, bottom, color);
	}
	
	private void drawCoolBlueHighlight(int left, int top, int right, int bottom) {
		int color = 0xA06BB6FF; // より明るい半透明の青
		drawGradientRect(left, top, right, bottom, color, color & 0x60FFFFFF);
	}
	
	private int getCoolBlueColor(int index) {
		// より明るい青系のカラーパレット（コントラスト改善）
		int[] coolBlueColors = {
			0xFF6BB6FF, // 明るい青
			0xFF87CEEB, // スカイブルー
			0xFF5BA3F5, // ライトブルー
			0xFF4A90E2, // メインブルー
			0xFF3B82F6, // ブルー
			0xFF2563EB, // ダークブルー
			0xFF1D4ED8, // ディープブルー
			0xFF1E40AF  // ダークブルー2
		};
		return coolBlueColors[index % coolBlueColors.length];
	}
	
	public void keyPressed(int k) {
		Category category = Category.values()[currentTab];
		List<Module> modules = Paradox.instance.moduleManager.getModulesbyCategory(category);
		
		// アニメーションをリセット
		animationStartTime = System.currentTimeMillis();
		
		switch(k) {
		
		case Keyboard.KEY_UP:
			if(expanded) {
				if(category.moduleIndex <= 0) {
					category.moduleIndex = modules.size() - 1;
				} else {
					category.moduleIndex--;
				}
			} else {
				if(currentTab <= 0) {
					currentTab = Category.values().length - 1;
				} else {
					currentTab--;
				}
			}
			break;
			
		case Keyboard.KEY_DOWN:
			if(expanded) {
				if(category.moduleIndex >= modules.size() - 1) {
					category.moduleIndex = 0;
				} else {
					category.moduleIndex++;
				}
			} else {
				if(currentTab >= Category.values().length - 1) {
					currentTab = 0;
				} else {
					currentTab++;
				}
			}
			break;
			
		case Keyboard.KEY_RIGHT:
			if(expanded && modules.size() != 0) {
				Module module = modules.get(category.moduleIndex);
				if(!module.name.equals("TabGui")) {
					module.toggle();
				}
			} else {
				if(modules.size() != 0) {
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