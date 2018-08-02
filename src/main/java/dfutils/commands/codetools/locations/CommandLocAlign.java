package dfutils.commands.codetools.locations;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

class CommandLocAlign {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    static void executeAlignLoc(ICommandSender sender, String[] commandArgs) {

        if (!checkFormat(sender, commandArgs)) {
            return;
        }

        ItemStack itemStack = minecraft.player.getHeldItemMainhand();

        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }

        if (!itemStack.getDisplayName().equals("§aLocation")) {
            errorMessage("Invalid item!");
            return;
        }

        if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("HideFlags") || !(itemStack.getTagCompound().getInteger("HideFlags") == 63)) {
            errorMessage("Invalid item!");
            return;
        }

        try {
            //Gets the location lore, removes the decimal, and sets the location lore again.
            NBTTagList itemLore = itemStack.getSubCompound("display").getTagList("Lore", 8);
            double locationX = CommandBase.parseDouble(itemLore.getStringTagAt(0));
            double locationY = CommandBase.parseDouble(itemLore.getStringTagAt(1));
            double locationZ = CommandBase.parseDouble(itemLore.getStringTagAt(2));

            itemLore.set(0, new NBTTagString(((int) locationX) + ".0"));
            itemLore.set(1, new NBTTagString(((int) locationY) + ".0"));
            itemLore.set(2, new NBTTagString(((int) locationZ) + ".0"));

            //Sends the updated location item to the server.
            minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        } catch (NumberInvalidException exception) {
            errorMessage("Invalid location format!");
        }
    }

    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length != 1) {
            infoMessage("Usage: \n" + new CommandLocBase().getUsage(sender));
            return false;
        }

        return true;
    }
}
