package dfutils.codetools.codehandler.utils;

import dfutils.utils.ItemUtils;
import dfutils.utils.playerdata.PlayerStateHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class CodeItemUtils {
    
    public static boolean isLocation(ItemStack itemStack) {
        return ItemUtils.getName(itemStack).equals("minecraft:paper") &&
                itemStack.hasTagCompound() &&
                itemStack.getTagCompound().hasKey("HideFlags") &&
                itemStack.getTagCompound().getInteger("HideFlags") == 63 &&
                itemStack.getTagCompound().hasKey("display") &&
                itemStack.getSubCompound("display").hasKey("Lore");
    }
    
    public static boolean isVariable(ItemStack itemStack) {
        return ItemUtils.getName(itemStack).equals("minecraft:magma_cream") &&
                itemStack.hasTagCompound() &&
                itemStack.getTagCompound().hasKey("HideFlags") &&
                itemStack.getTagCompound().getInteger("HideFlags") == 63;
    }
    
    public static NBTTagCompound writeLocationNbt(ItemStack itemStack) {
        if (isLocation(itemStack)) {
            NBTTagList locationLore = itemStack.getSubCompound("display").getTagList("Lore", 8);
            NBTTagList locationOffsetNbt = new NBTTagList();
            
            try {
                locationOffsetNbt.appendTag(new NBTTagDouble(CommandBase.parseDouble(locationLore.getStringTagAt(0)) - PlayerStateHandler.plotCorner.getX()));
                locationOffsetNbt.appendTag(new NBTTagDouble(CommandBase.parseDouble(locationLore.getStringTagAt(1)) - PlayerStateHandler.plotCorner.getY()));
                locationOffsetNbt.appendTag(new NBTTagDouble(CommandBase.parseDouble(locationLore.getStringTagAt(2)) - PlayerStateHandler.plotCorner.getZ()));
    
                itemStack.getTagCompound().setTag("LocationOffset", locationOffsetNbt);
            } catch (NumberInvalidException exception) {
                //Uh oh! Invalid location format! Just continue on.
            }
        }
        
        return ItemUtils.toNbt(itemStack);
    }
    
    public static ItemStack applyLocationOffset(ItemStack itemStack) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("LocationOffset")) {
            NBTTagList locationOffsetNbt = itemStack.getTagCompound().getTagList("LocationOffset", 6);
            NBTTagList locationLore = itemStack.getOrCreateSubCompound("display").getTagList("Lore", 8);
            
            locationLore.set(0, new NBTTagString((locationOffsetNbt.getDoubleAt(0) + PlayerStateHandler.plotCorner.getX()) + ""));
            locationLore.set(1, new NBTTagString((locationOffsetNbt.getDoubleAt(1) + PlayerStateHandler.plotCorner.getY()) + ""));
            locationLore.set(2, new NBTTagString((locationOffsetNbt.getDoubleAt(2) + PlayerStateHandler.plotCorner.getZ()) + ""));
            
            itemStack.getTagCompound().removeTag("LocationOffset");
        }
        
        return itemStack;
    }
}
