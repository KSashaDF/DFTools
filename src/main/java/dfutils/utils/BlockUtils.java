package dfutils.utils;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

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

        signText[0] = ITextComponent.Serializer.jsonToComponent(signNbt.getString("Text1")).getUnformattedText();
        if (signText[0].equals(""))
            signText[0] = null;

        signText[1] = ITextComponent.Serializer.jsonToComponent(signNbt.getString("Text2")).getUnformattedText();
        if (signText[1].equals(""))
            signText[1] = null;

        signText[2] = ITextComponent.Serializer.jsonToComponent(signNbt.getString("Text3")).getUnformattedText();
        if (signText[2].equals(""))
            signText[2] = null;

        signText[3] = ITextComponent.Serializer.jsonToComponent(signNbt.getString("Text4")).getUnformattedText();
        if (signText[3].equals(""))
            signText[3] = null;
        
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
