package me.gamrboy4life.paradox.module.pvp;

import me.gamrboy4life.paradox.module.Category;
import me.gamrboy4life.paradox.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemSword;

public class AutoSprint extends Module {

    private final Minecraft mc = Minecraft.getMinecraft();

    public AutoSprint() {
        super("AutoSprint", 0, Category.PVP);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer != null) {
            mc.thePlayer.setSprinting(false);
        }
        super.onDisable();
    }

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;
        if (mc.thePlayer == null) return;

        // 前進していなくても常に維持したいならここ外す
        if (mc.thePlayer.moveForward <= 0) return;

        // ブロックにぶつかっている場合は無理にスプリントしない
        if (mc.thePlayer.isCollidedHorizontally) return;

        // 攻撃中もスプリントを維持したいので「isUsingItem()」チェックは削除
        if (mc.thePlayer.isSneaking()) return;
        
        if (mc.thePlayer.isUsingItem() && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
            return; // 食べ物や弓のときはスプリントをやめる
        }

        // 常にスプリントを維持
        mc.thePlayer.setSprinting(true);
    }
}
