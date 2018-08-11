package dfutils.codetools.misctools;

import dfutils.config.ConfigHandler;
import dfutils.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class QuickItemRename {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public static void quickItemRenameLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        
        if (minecraft.player.isCreative() && minecraft.player.isSneaking()) {
            ItemStack itemStack = minecraft.player.getHeldItemMainhand();
            String itemId = ItemUtils.getName(itemStack);
            
            //Test if the item is a text book, number slime ball, or variable item.
            if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("HideFlags") && itemStack.getTagCompound().getInteger("HideFlags") == 63 &&
                    (itemId.equals("minecraft:book") || itemId.equals("minecraft:slime_ball") || itemId.equals("minecraft:magma_cream"))) {
                
                //Gets the item's name and replaces all the color code symbols with &'s.
                String itemName = itemStack.getDisplayName();
                itemName = itemName.replace('§', '&');
                
                //If the item is a text item and has the default name, set the chat box text to null.
                if (itemId.equals("minecraft:book") && itemName.equals("&bText"))
                    itemName = "";
                
                //If the item is a number item and has the default name, set the chat box text to null.
                if (itemId.equals("minecraft:slime_ball") && itemName.equals("&cNumber"))
                    itemName = "";
                
                //If the item is a variable item and has the default name, set the chat box text to null.
                if (itemId.equals("minecraft:magma_cream") && (itemName.equals("&cVariable") || itemName.equals("&cDynamic Variable")))
                    itemName = "";
                
                //If the item is a number slimeball, get rid of the color code at the start.
                if (itemId.equals("minecraft:slime_ball") && itemName.contains("&c")) {
                    itemName = itemName.replaceFirst("&c", "");
                }
                
                minecraft.displayGuiScreen(new GuiChat(itemName));
                minecraft.player.playSound(SoundEvents.BLOCK_ANVIL_USE, 0.5f, 1.5f);
            } else if (ConfigHandler.DO_QUICK_ITEM_RENAME) {
                
                //Gets the item's name and replaces all the color code symbols with &'s.
                String itemName = itemStack.getDisplayName();
                itemName = itemName.replace('§', '&');
                
                if (itemName.startsWith("&f")) {
                    itemName = itemName.replaceFirst("&f", "");
                }
    
                minecraft.displayGuiScreen(new GuiChat("/rename " + itemName));
                minecraft.player.playSound(SoundEvents.BLOCK_ANVIL_USE, 0.5f, 1.5f);
            }
        }
    }
}
