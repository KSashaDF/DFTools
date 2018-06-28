package dfutils.codetools.utils;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockUtils {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    public static String getName(BlockPos blockPos) {
        return minecraft.world.getBlockState(blockPos).getBlock().getLocalizedName();
    }
    
    public static EnumFacing getFacing(BlockPos blockPos) {
        return minecraft.world.getBlockState(blockPos).getValue(PropertyDirection.create("facing"));
    }
    
    public static boolean isWithinRegion(BlockPos checkLoc, BlockPos corner1, BlockPos corner2) {
        return checkLoc.getX() >= corner1.getX() &&
                checkLoc.getY() >= corner1.getY() &&
                checkLoc.getZ() >= corner1.getZ() &&
        
                checkLoc.getX() <= corner2.getX() &&
                checkLoc.getY() <= corner2.getY() &&
                checkLoc.getZ() <= corner2.getZ();
    }
}
