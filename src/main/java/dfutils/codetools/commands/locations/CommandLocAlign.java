package dfutils.codetools.commands.locations;

import dfutils.codetools.commands.code.CommandCodeBase;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

class CommandLocAlign {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    static void executeExportTemplate(ICommandSender sender, String[] commandArgs) {

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
    }

    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length != 1) {
            infoMessage("Usage: \n" + new CommandCodeBase().getUsage(sender));
            return false;
        }

        return true;
    }
}
