package dfutils.codetools.utils;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
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
    
    public static String[] getSignText(BlockPos signPos) {
        String[] signText = new String[4];
        NBTTagCompound signNbt = minecraft.world.getTileEntity(signPos).getUpdatePacket().getNbtCompound();
        
        try {
            if (JsonToNBT.getTagFromJson(signNbt.getString("Text1")).hasKey("extra"))
                signText[0] = JsonToNBT.getTagFromJson(signNbt.getString("Text1")).getTagList("extra", 10).getCompoundTagAt(0).getString("text");
    
            if (JsonToNBT.getTagFromJson(signNbt.getString("Text2")).hasKey("extra"))
                signText[1] = JsonToNBT.getTagFromJson(signNbt.getString("Text2")).getTagList("extra", 10).getCompoundTagAt(0).getString("text");
    
            if (JsonToNBT.getTagFromJson(signNbt.getString("Text3")).hasKey("extra"))
                signText[2] = JsonToNBT.getTagFromJson(signNbt.getString("Text3")).getTagList("extra", 10).getCompoundTagAt(0).getString("text");
    
            if (JsonToNBT.getTagFromJson(signNbt.getString("Text4")).hasKey("extra"))
                signText[3] = JsonToNBT.getTagFromJson(signNbt.getString("Text4")).getTagList("extra", 10).getCompoundTagAt(0).getString("text");
        } catch (NBTException exception) {}
        
        return signText;
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
