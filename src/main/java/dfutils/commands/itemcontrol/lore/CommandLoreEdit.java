package dfutils.commands.itemcontrol.lore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

class CommandLoreEdit {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    static void executeEditLore(ICommandSender sender, String[] commandArgs) {

        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;

        ItemStack itemStack = minecraft.player.getHeldItemMainhand();

        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }

        //Checks if item has NBT tag.
        if (itemStack.getTagCompound() == null) {
            errorMessage("Invalid item! Item does not contain any lore.");
            return;
        }

        //Checks if item has display tag.
        if (!itemStack.getTagCompound().hasKey("display", 10)) {
            errorMessage("Invalid item! Item does not contain any lore.");
            return;
        }

        NBTTagCompound nbtTag = itemStack.getSubCompound("display");

        //Checks if item has Lore tag.
        if (!nbtTag.hasKey("Lore", 9)) {
            errorMessage("Invalid item! Item does not contain any lore.");
            return;
        }

        NBTTagList loreList = nbtTag.getTagList("Lore", 8);
        int lineNumber;

        //Parses line number from command.
        try {
            lineNumber = CommandBase.parseInt(commandArgs[1]);

        } catch (NumberInvalidException exception) {
            errorMessage("Invalid line number.");
            return;
        }

        //Checks if specified lore line is valid.
        if (lineNumber > loreList.tagCount()) {
            errorMessage("Invalid line number! This item only contains " + loreList.tagCount() + " lines of lore.");
            return;
        }

        //Gets the specified item lore line, replaces all the formatting characters with & characters,
        //and calls the chat GUI display method/thread.
        String itemLore = "/lore set " + lineNumber + " " + loreList.getStringTagAt(lineNumber - 1);
        itemLore = itemLore.replaceAll("§", "&");
        new Thread(new OpenChatGui(itemLore)).start();
    }

    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length == 2) {
            try {

                if (CommandBase.parseInt(commandArgs[1]) > 0) {
                    return true;

                } else {
                    errorMessage("Line number must be greater than 0.");
                    return false;
                }

            } catch (NumberInvalidException exception) {
                errorMessage("Invalid line number.");
                return false;
            }

        } else {
            infoMessage("Usage:\n" + new CommandLoreBase().getUsage(sender));
            return false;
        }
    }

    //Why does this exist you ask? Well! This small multi-thready sub-class thing exists because apparently I need
    //to add a small wait to the GUI open code, otherwise the newly opened GUI is immediately closed for some reason.
    static class OpenChatGui implements Runnable {

        String chatText;

        OpenChatGui(String chatboxText) {
            chatText = chatboxText;
        }

        @Override
        public void run() {
            //Waits one tick.
            try {
                Thread.sleep(50);
            } catch (InterruptedException exception) {
                errorMessage("Uh oh! The GUI display wait was interrupted!");
                return;
            }

            minecraft.displayGuiScreen(new GuiChat(chatText));
        }
    }
}
