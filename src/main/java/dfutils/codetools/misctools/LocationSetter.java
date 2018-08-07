package dfutils.codetools.misctools;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class LocationSetter {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public static void locationSetterLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        
        if (minecraft.player.isCreative()) {
            ItemStack itemStack = minecraft.player.getHeldItemMainhand();
            
            if (itemStack.getDisplayName().equals("§aLocation")) {
                try {
                    
                    if (itemStack.getTagCompound().getInteger("HideFlags") == 63) {
                        
                        NBTTagCompound displayTag = itemStack.getSubCompound("display");
                        NBTTagList loreTag = new NBTTagList();
                        
                        loreTag.appendTag(new NBTTagString(minecraft.objectMouseOver.getBlockPos().getX() + ".0"));
                        loreTag.appendTag(new NBTTagString(minecraft.objectMouseOver.getBlockPos().getY() + ".0"));
                        loreTag.appendTag(new NBTTagString(minecraft.objectMouseOver.getBlockPos().getZ() + ".0"));
                        loreTag.appendTag(new NBTTagString("0.0"));
                        loreTag.appendTag(new NBTTagString("0.0"));
                        
                        displayTag.setTag("Lore", loreTag);
    
                        //Sends updated item to the server.
                        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);

                        //Cancels the left click event.
                        event.setCanceled(true);
                    }
                    
                } catch (NullPointerException exception) {
                    //Looks like the item didn't have a certain NBT tag, continue on.
                }
            }
        }
    }
}
