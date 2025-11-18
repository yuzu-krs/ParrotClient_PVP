package me.gamrboy4life.paradox.module.pvp;

import org.lwjgl.input.Keyboard;

import me.gamrboy4life.paradox.module.Category;
import me.gamrboy4life.paradox.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

public class Scaffold extends Module {

    public Scaffold() {
        super("Scaffold", Keyboard.KEY_G, Category.PVP);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        // OFF時はシフト解除
        mc.gameSettings.keyBindSneak.pressed = false;
        super.onDisable();
    }

    @Override
    public void onUpdate() {
        if (!isToggled()) return;

        EntityPlayerSP p = player();
        if (p == null || world() == null) return;

        // 足元
        BlockPos under = new BlockPos(p.posX, p.posY - 1, p.posZ);
        Block block = world().getBlock(under);

        boolean isAir =
                (block instanceof BlockAir) ||
                (block instanceof BlockLiquid);

        if (isAir) {
            // 足元が空 → 自動半シフト
            mc.gameSettings.keyBindSneak.pressed = true;
        } else {
            // 足元にブロック → 通常歩き
            mc.gameSettings.keyBindSneak.pressed = false;
        }
    }
}
