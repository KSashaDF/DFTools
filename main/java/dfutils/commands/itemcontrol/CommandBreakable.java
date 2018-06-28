package dfutils.commands.itemcontrol;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.commands.MessageUtils.commandAction;
import static dfutils.commands.MessageUtils.commandError;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandBreakable extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "breakable";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/breakable";
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
        
        //Makes item breakable.
        itemStack.getTagCompound().setTag("Unbreakable", new NBTTagByte((byte) 0));
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
        commandAction("Item is no longer unbreakable.");
    }
}
