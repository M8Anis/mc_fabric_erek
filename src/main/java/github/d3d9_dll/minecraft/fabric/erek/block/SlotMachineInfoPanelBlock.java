/*
Copyright © 2022 https://github.com/d3d9-dll

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

package github.d3d9_dll.minecraft.fabric.erek.block;

import github.d3d9_dll.minecraft.fabric.erek.item.SlotMachineInfoPanelBlockItem;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;

public class SlotMachineInfoPanelBlock extends github.d3d9_dll.minecraft.fabric.erek.block.Block {

    public static final Identifier IDENTIFIER = SlotMachineInfoPanelBlockItem.IDENTIFIER;

    private static final VoxelShape SHAPE_NORTH =
            Block.createCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 12.0D, 16.0D);

    public SlotMachineInfoPanelBlock(Block.Settings settings) {
        super(settings, SHAPE_NORTH);
    }

}
