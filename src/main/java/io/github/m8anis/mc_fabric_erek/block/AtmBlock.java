/*
Copyright © 2022-2023 https://github.com/M8Anis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.github.m8anis.mc_fabric_erek.block;

import io.github.m8anis.mc_fabric_erek.item.AtmBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class AtmBlock extends io.github.m8anis.mc_fabric_erek.block.Block {

    public static final Identifier IDENTIFIER = AtmBlockItem.IDENTIFIER;

    private static final VoxelShape SHAPE_NORTH = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(2.0D, 0.0D, 9.0D, 14.0D, 14.0D, 16.0D),
            // Left plate
            Block.createCuboidShape(13.0D, 0.0D, 2.0D, 14.0D, 4.0D, 3.0D),
            Block.createCuboidShape(13.0D, 0.0D, 3.0D, 14.0D, 7.0D, 4.0D),
            Block.createCuboidShape(13.0D, 0.0D, 4.0D, 14.0D, 9.0D, 5.0D),
            Block.createCuboidShape(13.0D, 0.0D, 5.0D, 14.0D, 10.0D, 7.0D),
            Block.createCuboidShape(13.0D, 0.0D, 7.0D, 14.0D, 14.0D, 9.0D),
            // Right plate
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 3.0D, 4.0D, 3.0D),
            Block.createCuboidShape(2.0D, 0.0D, 3.0D, 3.0D, 7.0D, 4.0D),
            Block.createCuboidShape(2.0D, 0.0D, 4.0D, 3.0D, 9.0D, 5.0D),
            Block.createCuboidShape(2.0D, 0.0D, 5.0D, 3.0D, 10.0D, 7.0D),
            Block.createCuboidShape(2.0D, 0.0D, 7.0D, 3.0D, 14.0D, 9.0D),
            // Upper plate
            Block.createCuboidShape(3.0D, 12.0D, 7.0D, 13.0D, 14.0D, 9.0D),
            // Keypad
            Block.createCuboidShape(6.0D, 0.0D, 4.0D, 10.0D, 1.0D, 6.0D)
    ).simplify();

    public AtmBlock(Settings settings) {
        super(settings, SHAPE_NORTH);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkConstruct(BlockPos blockPos, World world) {
        if (world == null || blockPos == null) return false;

        BlockState atm_block = world.getBlockState(blockPos);
        BlockState atm_bottom_case = world.getBlockState(blockPos.add(0, -1, 0));

        return atm_block.getBlock() instanceof AtmBlock &&
                atm_bottom_case.getBlock() instanceof AtmBottomCaseBlock &&
                atm_block.get(FACING).equals(atm_bottom_case.get(FACING));
    }

}
