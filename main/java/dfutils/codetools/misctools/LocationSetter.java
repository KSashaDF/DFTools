package dfutils.codetools.misctools;

import dfutils.codetools.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LocationSetter {
    
    private Minecraft minecraft = Minecraft.getMinecraft();
    
    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        
        if (minecraft.player.isCreative()) {
            ItemStack itemStack = minecraft.player.getHeldItemMainhand();
            
            if (itemStack.getDisplayName().equals("§aLocation")) {
                try {
                    
                    if (itemStack.getTagCompound().getTag("HideFlags").equals(new NBTTagInt(63))) {
                        
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
                    }
                    
                } catch (NullPointerException exception) {}
            }
        }
    }
}
