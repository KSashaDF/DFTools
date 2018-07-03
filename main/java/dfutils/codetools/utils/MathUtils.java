package dfutils.codetools.utils;

import net.minecraft.util.math.BlockPos;

public class MathUtils {
    public static double distance(BlockPos pos1, BlockPos pos2) {
        return Math.hypot(Math.hypot(pos1.getX() - pos2.getX(), pos1.getZ() - pos2.getZ()), pos1.getY() - pos2.getY());
    }
}
