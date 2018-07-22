package dfutils.codetools.misctools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class QuickItemRename {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    public static void quickItemRenameLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {

        if (minecraft.player.isCreative() && minecraft.player.isSneaking()) {
            ItemStack itemStack = minecraft.player.getHeldItemMainhand();

            //Test if the item has the HideFlags tag.
            if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("HideFlags") && itemStack.getTagCompound().getInteger("HideFlags") == 63) {
                int itemId = Item.getIdFromItem(itemStack.getItem());

                //Test if the item is either a book, slimeball, or magma cream.
                if (itemId == 340 || itemId == 341 || itemId == 378) {

                    //Gets the item's name and replaces all the color code symbols with &'s.
                    String itemName = itemStack.getDisplayName();
                    itemName = itemName.replace('§', '&');

                    //If the item is a text item and has the default name, set the chat box text to null.
                    if (itemId == 340 && itemName.equals("&bText"))
                        itemName = "";

                    //If the item is a number item and has the default name, set the chat box text to null.
                    if (itemId == 341 && itemName.equals("&cNumber"))
                        itemName = "0";

                    //DEPRECATED
                    //If the item is a variable item and has the default name, set the chat box text to null.
                    if (itemId == 378 && (itemName.equals("&cVariable") || itemName.equals("§cDynamic Variable")))
                        itemName = "";

                    //If the item is a variable item and has the default name, set the chat box text to null.
                    if (itemId == 378 && itemName.equals("&cDynamic Variable"))
                        itemName = "";

                    //If the item is a number slimeball, get rid of the color code at the start.
                    if (itemId == 341 && itemName.contains("&c")) {
                        itemName = itemName.replaceFirst("&c", "");
                    }

                    minecraft.player.playSound(SoundEvents.BLOCK_ANVIL_USE, 0.75F, 1.5F);
                    minecraft.displayGuiScreen(new GuiChat(itemName));
                }
            }
        }
    }
}
