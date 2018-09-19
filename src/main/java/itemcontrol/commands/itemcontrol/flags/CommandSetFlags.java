package itemcontrol.commands.itemcontrol.flags;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static diamondcore.utils.MessageUtils.actionMessage;
import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandSetFlags extends CommandBase implements IClientCommand {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "setflags";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§b/setflags <number> \n" +
                "\n" +
                "§cNote: §7For those wondering what this command \n" +
                "§7does, here is an explanation. Each item has a HideFlags \n" +
                "§7NBT tag, and this NBT tag can be set anywhere from 0 \n" +
                "§7to 63. 0 will make all aspects of the item shown (aspects being \n" +
                "§7things like enchants, attributes, etc.) and 63 will make \n" +
                "§7everything hidden, however, some number in between 0 and 63 \n" +
                "§7will hide different aspects, here is a key on how to use \n" +
                "§7this functionality: \n" +
                "\n" +
                "§7(note that you need to add the numbers for all the things \n" +
                "§7you want to hide together, such as 3 will hide enchants \n" +
                "§7and attributes) \n" +
                "\n" +
                "§5> §d1 §8- §7Enchantments \n" +
                "§5> §d2 §8- §7Attributes \n" +
                "§5> §d4 §8- §7The Unbreakable Tag \n" +
                "§5> §d8 §8- §7CanDestroy Tags \n" +
                "§5> §d16 §8- §7CanPlaceOn Tags \n" +
                "§5> §d32 §8- §7Various other tags. \n" +
                "";
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
        
        //Checks if item has NBT tag, if not, adds NBT tag.
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        
        try {
            //Sets item flags.
            itemStack.getTagCompound().setTag("HideFlags", new NBTTagInt(parseInt(commandArgs[0], 0, 63)));
        } catch (NumberInvalidException exception) {
            errorMessage("Invalid number argument.");
            return;
        }
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
        actionMessage("Set HideFlags tag for this item.");
    }
}
