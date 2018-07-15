package dfutils.utils;

import net.minecraft.util.math.BlockPos;

public class MathUtils {
    public static double distance(BlockPos pos1, BlockPos pos2) {
        return Math.hypot(Math.hypot(pos1.getX() - pos2.getX(), pos1.getZ() - pos2.getZ()), pos1.getY() - pos2.getY());
    }

    public static boolean withinRange(int number, int min, int max) {
        if (min > max) {
            int tempMin = min;
            min = max;
            max = tempMin;
        }

        return number >= min && number <= max;
    }
}
