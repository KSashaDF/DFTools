package itemcontrol.commands.itemcontrol.enchant;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static diamondcore.utils.MessageUtils.actionMessage;
import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandClearEnch extends CommandBase implements IClientCommand {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public String getName() {
        return "clearenchants";
    }

    public String getUsage(ICommandSender sender) {
        return "§b/clearenchants";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {

        //Checks if player should be able to execute command.
        if (!minecraft.player.isCreative()) {
            errorMessage("You need to be in build mode or dev mode to do this!");
            return;
        }

        //Checks if the command has any arguments.
        if (commandArgs.length != 0) {
            infoMessage("Usage:\n" + getUsage(sender));
            return;
        }

        ItemStack itemStack = minecraft.player.getHeldItemMainhand();

        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }

        //The following ifs check if there are any enchantments on the item.
        if (!itemStack.hasTagCompound()) {
            errorMessage("Invalid item! This item does not contain any enchantments.");
            return;
        }

        if (!itemStack.getTagCompound().hasKey("ench")) {
            errorMessage("Invalid item! This item does not contain any enchantments.");
            return;
        }

        //Removes the enchantment NBT tag.
        itemStack.getTagCompound().removeTag("ench");

        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);

        actionMessage("Cleared all enchantments.");
    }
}
