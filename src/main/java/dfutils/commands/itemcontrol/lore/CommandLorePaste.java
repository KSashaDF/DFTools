package dfutils.commands.itemcontrol.lore;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static dfutils.utils.MessageUtils.actionMessage;
import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

class CommandLorePaste {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    static void executePasteLore(ICommandSender sender, String[] commandArgs) {

        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;

        ///Checks if the player has copied any lore yet.
        if (CommandLoreCopy.loreHistory.size() == 0) {
            errorMessage("You have not copied any lore yet.");
            return;
        }

        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        NBTTagCompound displayTag = itemStack.getOrCreateSubCompound("display");
        int historyIndex = 1;

        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }

        //Parses the history index number argument.
        if (commandArgs.length == 2) {
            try {
                historyIndex = CommandBase.parseInt(commandArgs[1]);
            } catch (NumberInvalidException exception) {
                errorMessage("Argument must be a valid number.");
                return;
            }
        }

        //Checks if the history index is within the bounds of the lore history.
        if (historyIndex < 1 || historyIndex > CommandLoreCopy.loreHistory.size()) {
            errorMessage("Invalid history index!");
            return;
        }

        //Sets the item lore.
        displayTag.setTag("Lore", CommandLoreCopy.loreHistory.get((historyIndex - CommandLoreCopy.loreHistory.size()) * -1));
        actionMessage("Set item lore.");

        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
    }

    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length == 1 || commandArgs.length == 2) {
            return true;

        } else {
            infoMessage("Usage:\n" + new CommandLoreBase().getUsage(sender));
            return false;
        }
    }
}
