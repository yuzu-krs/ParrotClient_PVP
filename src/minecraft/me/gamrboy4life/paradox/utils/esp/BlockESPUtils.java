package me.gamrboy4life.paradox.utils.esp;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class BlockESPUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawSingleBlockBox(BlockPos pos, double r, double g, double b) {
        final double renderX = mc.getRenderManager().renderPosX;
        final double renderY = mc.getRenderManager().renderPosY;
        final double renderZ = mc.getRenderManager().renderPosZ;

        double minX = pos.getX() - renderX + 1e-3;
        double minY = pos.getY() - renderY + 1e-3;
        double minZ = pos.getZ() - renderZ + 1e-3;
        double maxX = pos.getX() + 1.0 - renderX - 1e-3;
        double maxY = pos.getY() + 1.0 - renderY - 1e-3;
        double maxZ = pos.getZ() + 1.0 - renderZ - 1e-3;

        AxisAlignedBB bb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.disableDepth();

        GL11.glLineWidth(1.0f);
        GL11.glColor3d(r, g, b);

        drawOutlinedBox(bb);

        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }


    // 線で枠を描く（AxisAlignedBB のまま）
    private static void drawOutlinedBox(AxisAlignedBB bb) {
        GL11.glBegin(GL11.GL_LINES);

        // bottom
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

        // top
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

        // verticals
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

        GL11.glEnd();
    }
}


