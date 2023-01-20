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

import io.github.m8anis.mc_fabric_erek.item.SlotMachineBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class SlotMachineBlock extends io.github.m8anis.mc_fabric_erek.block.Block {

    public static final Identifier IDENTIFIER = SlotMachineBlockItem.IDENTIFIER;

    private static final VoxelShape SHAPE_NORTH = VoxelShapes.union(
            // Back case
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 16.0D),
            // Right plate
            Block.createCuboidShape(0.0D, 0.0D, 6.0D, 1.0D, 14.0D, 7.0D),
            Block.createCuboidShape(0.0D, 0.0D, 5.0D, 1.0D, 12.0D, 6.0D),
            Block.createCuboidShape(0.0D, 0.0D, 4.0D, 1.0D, 10.0D, 5.0D),
            Block.createCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 8.0D, 4.0D),
            Block.createCuboidShape(0.0D, 0.0D, 2.0D, 1.0D, 6.0D, 3.0D),
            Block.createCuboidShape(0.0D, 0.0D, 1.0D, 1.0D, 4.0D, 2.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D),
            // Left plate
            Block.createCuboidShape(15.0D, 0.0D, 6.0D, 16.0D, 14.0D, 7.0D),
            Block.createCuboidShape(15.0D, 0.0D, 5.0D, 16.0D, 12.0D, 6.0D),
            Block.createCuboidShape(15.0D, 0.0D, 4.0D, 16.0D, 10.0D, 5.0D),
            Block.createCuboidShape(15.0D, 0.0D, 3.0D, 16.0D, 8.0D, 4.0D),
            Block.createCuboidShape(15.0D, 0.0D, 2.0D, 16.0D, 6.0D, 3.0D),
            Block.createCuboidShape(15.0D, 0.0D, 1.0D, 16.0D, 4.0D, 2.0D),
            Block.createCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 2.0D, 1.0D),
            // Display header (bottom)
            Block.createCuboidShape(1.0D, 13.0D, 6.0D, 15.0D, 14.0D, 7.0D),
            // Display backplate
            Block.createCuboidShape(1.0D, 0.0D, 2.0D, 15.0D, 2.0D, 3.0D),
            Block.createCuboidShape(1.0D, 0.0D, 3.0D, 15.0D, 4.0D, 4.0D),
            Block.createCuboidShape(1.0D, 0.0D, 4.0D, 15.0D, 6.0D, 5.0D),
            Block.createCuboidShape(1.0D, 0.0D, 5.0D, 15.0D, 8.0D, 6.0D),
            Block.createCuboidShape(1.0D, 0.0D, 6.0D, 15.0D, 10.0D, 7.0D),
            Block.createCuboidShape(1.0D, 0.0D, 7.0D, 15.0D, 12.0D, 8.0D)
    ).simplify();

    public SlotMachineBlock(Block.Settings settings) {
        super(settings, SHAPE_NORTH);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkConstruct(BlockPos blockPos, World world) {
        if (world == null || blockPos == null) return false;

        BlockState slotmachine = world.getBlockState(blockPos);
        BlockState slotmachine_info_panel = world.getBlockState(blockPos.add(0, 1, 0));
        BlockState slotmachine_bottom_case = world.getBlockState(blockPos.add(0, -1, 0));

        return (slotmachine.getBlock() instanceof SlotMachineBlock &&
                slotmachine_info_panel.getBlock() instanceof SlotMachineInfoPanelBlock &&
                slotmachine_bottom_case.getBlock() instanceof SlotMachineBottomCaseBlock) &&
                (slotmachine.get(FACING).equals(slotmachine_info_panel.get(FACING)) &&
                        slotmachine.get(FACING).equals(slotmachine_bottom_case.get(FACING)));
    }

}
