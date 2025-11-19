package me.gamrboy4life.paradox.module.render;

import me.gamrboy4life.paradox.module.Category;
import me.gamrboy4life.paradox.module.Module;
import me.gamrboy4life.paradox.utils.esp.BlockESPUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class EspBeds extends Module {

    public EspBeds() {
        super("ESP Beds", 0, Category.RENDER);
    }

    @Override
    public void onRender() {
        if (!this.isToggled()) return;

        int px = (int) mc.thePlayer.posX;
        int py = (int) mc.thePlayer.posY;
        int pz = (int) mc.thePlayer.posZ;

        int radius = 30;

        for (int x = px - radius; x <= px + radius; x++) {
            for (int y = py - radius; y <= py + radius; y++) {
                for (int z = pz - radius; z <= pz + radius; z++) {

                    BlockPos pos = new BlockPos(x, y, z);
                    IBlockState state = mc.theWorld.getBlockState(pos);
                    Block block = state.getBlock();

                    if (block == Blocks.bed) {
                        BlockBed.EnumPartType part = state.getValue(BlockBed.PART);
                        EnumFacing facing = state.getValue(BlockBed.FACING);

                        BlockPos headPos;
                        BlockPos footPos;
                        if (part == BlockBed.EnumPartType.HEAD) {
                            headPos = pos;
                            footPos = pos.offset(facing.getOpposite());
                        } else {
                            footPos = pos;
                            headPos = pos.offset(facing);
                        }

                        // head と foot を別々に描画
                        BlockESPUtils.drawSingleBlockBox(headPos, 1.0, 0.2, 0.2);
                        BlockESPUtils.drawSingleBlockBox(footPos, 1.0, 0.2, 0.2);
                    }
                }
            }
        }
    }
}
