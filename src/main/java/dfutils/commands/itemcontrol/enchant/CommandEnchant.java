package dfutils.commands.itemcontrol.enchant;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandEnchant extends CommandBase implements IClientCommand {

    private final Minecraft minecraft = Minecraft.getMinecraft();

    public String getName() {
        return "enchant";
    }

    public String getUsage(ICommandSender sender) {
        return "§e/enchant <enchantment> <level>";
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
        if (commandArgs.length != 2) {
            infoMessage("Usage:\n" + getUsage(sender));
            return;
        }

        ItemStack itemStack = minecraft.player.getHeldItemMainhand();

        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }

        //Checks if item has NBT tag, if not, adds NBT tag.
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        //Checks if item has enchant NBT tag, if not, adds enchantment tag.
        if (!itemStack.getTagCompound().hasKey("ench")) {
            itemStack.getTagCompound().setTag("ench", new NBTTagList());
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
            if (enchantList.getCompoundTagAt(i).getShort("id") == (short) Enchantment.getEnchantmentID(enchantment))
                enchantList.removeTag(i);
        }

        //Creates the new enchantment NBT tag.
        NBTTagCompound enchantTag = new NBTTagCompound();
        enchantTag.setShort("id", (short) Enchantment.getEnchantmentID(enchantment));

        try {
            enchantTag.setShort("lvl", (short) CommandBase.parseInt(commandArgs[1]));
        } catch (NumberInvalidException exception) {
            errorMessage("Invalid enchantment level argument! Argument must be a valid number.");
        }

        //Adds enchantment to item.
        enchantList.appendTag(enchantTag);

        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
    }
}
