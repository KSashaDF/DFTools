package dfutils.commands.itemcontrol.enchant;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.utils.MessageUtils.actionMessage;
import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandDisenchant extends CommandBase implements IClientCommand {

    private final Minecraft minecraft = Minecraft.getMinecraft();

    public String getName() {
        return "disenchant";
    }

    public String getUsage(ICommandSender sender) {
        return "§e/disenchant <enchantment>";
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

        //Checks if the command has the correct number of arguments.
        if (commandArgs.length != 1) {
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

        Enchantment enchantment;

        //Tries to find to find the specified enchantment first by using an enchantment ID,
        //if user did not specify an enchantment ID try to find the enchantment using its name.
        try {
            enchantment = Enchantment.getEnchantmentByID(CommandBase.parseInt(commandArgs[0]));
        } catch (NumberInvalidException exception) {
            enchantment = Enchantment.getEnchantmentByLocation(commandArgs[0]);
        }

        //If the enchantment is null, the specified enchantment is not valid.
        if (enchantment == null) {
            errorMessage("Invalid enchantment!");
            return;
        }

        NBTTagList enchantList = itemStack.getTagCompound().getTagList("ench", 10);

        //Searches for enchants of the same type and if one is found, removes it.
        for (int i = 0; i < enchantList.tagCount(); i++) {
            if (enchantList.getCompoundTagAt(i).getShort("id") == (short) Enchantment.getEnchantmentID(enchantment)) {
                enchantList.removeTag(i);

                actionMessage("Removed enchantment!");

                //Sends updated item to the server.
                minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);

                return;
            }
        }

        errorMessage("Unable to find specified enchantment on item!");
    }
}
