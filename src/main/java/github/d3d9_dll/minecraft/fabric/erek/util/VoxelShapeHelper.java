package github.d3d9_dll.minecraft.fabric.erek.util;

import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class VoxelShapeHelper {

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        @SuppressWarnings("unused") final String COPED_FROM_AND_ADAPTED =
                "https://forums.minecraftforge.net/topic/74979-1144-rotate-voxel-shapes/#comment-523370";

        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};

        int times = (to.ordinal() - from.getDirection().ordinal() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox(
                    (minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))
            );
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

}