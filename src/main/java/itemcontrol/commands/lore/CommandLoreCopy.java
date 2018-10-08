package itemcontrol.commands.lore;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

import static diamondcore.utils.MessageUtils.actionMessage;
import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

class CommandLoreCopy {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    static List<NBTTagList> loreHistory = new ArrayList<>();

    static void executeCopyLore(ICommandSender sender, String[] commandArgs) {

        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;

        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        NBTTagCompound displayTag = itemStack.getSubCompound("display");

        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }

        //Checks if item has any lore.
        if (displayTag == null || !displayTag.hasKey("Lore") || displayTag.getTagList("Lore", 8).tagCount() == 0) {
            loreHistory.add(new NBTTagList());
            actionMessage("Copied item lore.");
        } else {
            loreHistory.add(displayTag.getTagList("Lore", 8));
            actionMessage("Copied item lore.");
        }
    }

    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length == 1) {
            return true;

        } else {
            infoMessage("Usage:\n" + new CommandLoreBase().getUsage(sender));
            return false;
        }
    }
}
