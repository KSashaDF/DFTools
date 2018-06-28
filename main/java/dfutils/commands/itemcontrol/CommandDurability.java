package dfutils.commands.itemcontrol;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.commands.MessageUtils.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandDurability extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "durability";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/durability <number>";
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
    
        if (commandArgs.length != 1) {
            commandError("Usage:\n" + getUsage(sender));
            return;
        }
        
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
        
        //Sets item durability.
        try {
            int itemDurability = itemStack.getMaxDamage() - CommandBase.parseInt(commandArgs[0]);
    
            if (itemDurability < 0) itemDurability = 0;
            
            itemStack.setItemDamage(itemDurability);
        } catch (NumberInvalidException exception) {
            commandError("Invalid number argument.");
            return;
        }
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
        commandAction("Item durability set.");
    }
}
