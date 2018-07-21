package dfutils.commands.codetools.code;

import dfutils.utils.ItemUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import static dfutils.utils.MessageUtils.infoMessage;

class CommandSelectStick {

    static void executeSelectStick(ICommandSender sender, String[] commandArgs) {

        if (!checkFormat(sender, commandArgs)) {
            return;
        }

        //Creates item and sets item name.
        ItemStack itemStack = new ItemStack(Item.getItemById(280));
        itemStack.setStackDisplayName("§6* §eCode Selection Stick §6*");

        //Sets item lore.
        NBTTagList itemLore = new NBTTagList();
        itemStack.getSubCompound("display").setTag("Lore", itemLore);

        itemLore.appendTag(new NBTTagString("§8§m                              "));
        itemLore.appendTag(new NBTTagString("§7Use this to select code."));
        itemLore.appendTag(new NBTTagString(""));
        itemLore.appendTag(new NBTTagString("§3> §7Click once to select"));
        itemLore.appendTag(new NBTTagString("§7the current code block."));
        itemLore.appendTag(new NBTTagString(""));
        itemLore.appendTag(new NBTTagString("§3> §7Click twice to select"));
        itemLore.appendTag(new NBTTagString("§7the local scope."));
        itemLore.appendTag(new NBTTagString(""));
        itemLore.appendTag(new NBTTagString("§3> §7Click three times to"));
        itemLore.appendTag(new NBTTagString("§7select the code line."));
        itemLore.appendTag(new NBTTagString("§8§m                              "));

        //Sends item to server.
        ItemUtils.setItemInHotbar(itemStack, true);
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length != 1) {
            infoMessage("Usage: \n" + new CommandCodeBase().getUsage(sender));
            return false;
        }
        
        return true;
    }
}
