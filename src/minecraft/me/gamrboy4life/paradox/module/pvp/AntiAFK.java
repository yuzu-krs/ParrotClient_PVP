package me.gamrboy4life.paradox.module.pvp;

import java.util.Random;

import me.gamrboy4life.paradox.module.Category;
import me.gamrboy4life.paradox.module.Module;
import me.gamrboy4life.paradox.utils.timers.Timer2;

public class AntiAFK extends Module {

    public AntiAFK() {
        super("AntiAFK", 0, Category.PVP);
    }

    private final Timer2 timer = new Timer2();
    private final Random random = new Random();

    @Override
    public void onUpdate() {
        if (!this.isToggled()) return;

        // 40〜60秒ごとに視点を微妙に動かす（人間っぽい）
        if (timer.check(random.nextInt(20000) + 40000)) {
            float yawOffset = (random.nextFloat() * 6f) - 3f;  // -3〜3度
            float pitchOffset = (random.nextFloat() * 2f) - 1f; // -1〜1度

            mc.thePlayer.rotationYaw += yawOffset;
            mc.thePlayer.rotationPitch = Math.max(-89, Math.min(89, mc.thePlayer.rotationPitch + pitchOffset));

            timer.reset();
        }
    }

    @Override
    public void onDisable() {
        // 特に後処理は不要
    }
}
