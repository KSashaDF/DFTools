package diamondfireutils.commands.itemcontrol.rename;

import diamondfireutils.commands.CommandUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static diamondfireutils.commands.MessageUtils.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandRenameAnvil extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "renameanvil";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/renameanvil <name>";
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
            commandError("You need to be in build mode or dev mode to do this!");
            return;
        }
    
        if (commandArgs.length == 0) {
            commandError("Usage:\n" + getUsage(sender));
            return;
        }
    
        commandArgs[0] = CommandBase.buildString(commandArgs, 0);
        commandArgs[0] = CommandUtils.parseColorCodes(commandArgs[0]);
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
    
        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            commandError("Invalid item!");
            return;
        }
    
        //Checks if item has NBT tag, if not, adds NBT tag.
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
    
        //Checks if item has display tag, if not, adds display tag.
        if (!itemStack.getTagCompound().hasKey("display", 10)) {
            itemStack.getTagCompound().setTag("display", new NBTTagCompound());
        }
        
        //Renames item.
        itemStack.getSubCompound("display").setTag("Name", new NBTTagString(commandArgs[0]));
    
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
    
        commandAction("Set item name.");
    }
}
