package dfutils.commands.itemcontrol;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.commands.MessageUtils.commandError;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandGive extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "give";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/give <item> [amount] [metadata] [NBT]";
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
    
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }
    
    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {
    
        //Checks if player should be able to execute command.
        if (!checkFormat(sender, commandArgs)) {
            return;
        }
        
        ItemStack itemStack;
        EntityPlayer player = minecraft.player;
        
        try {
            Item item = getItemByText(sender, commandArgs[0]);
            
            int amount = commandArgs.length >= 2 ? parseInt(commandArgs[1]) : 1;
            int metadata = commandArgs.length >= 3 ? parseInt(commandArgs[2]) : 0;
            
            itemStack = new ItemStack(item, amount, metadata);
            
        } catch (NumberInvalidException exception) {
            commandError("Invalid argument!");
            return;
        }
    
        NBTTagCompound itemNbt = new NBTTagCompound();
        
        if (commandArgs.length >= 4) {
            try {
                String itemNbtString = buildString(commandArgs, 3);
    
                itemNbt = JsonToNBT.getTagFromJson(itemNbtString);
            } catch (NBTException exception) {
                commandError("Invalid NBT format! " + exception.getMessage());
                return;
            }
        }
        
        itemStack.setTagCompound(itemNbt);
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
        player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
    }
    
    private boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (!minecraft.player.isCreative()) {
            commandError("You need to be in build mode or dev mode to do this!");
            return false;
        }
        
        if (commandArgs.length < 1) {
            commandError("Usage:\n" + getUsage(sender));
            return false;
        }
        
        if (commandArgs.length >= 2) {
            try {
                if (parseInt(commandArgs[1]) < 1 || parseInt(commandArgs[1]) > 64) {
                    commandError("Invalid stack size.");
                    return false;
                }
            } catch (NumberInvalidException exception) {
                commandError("Invalid stack size number argument.");
                return false;
            }
        }
        
        if (commandArgs.length >= 3) {
            try {
                if (parseInt(commandArgs[2]) < 0) {
                    commandError("Invalid metadata number.");
                    return false;
                }
            } catch (NumberInvalidException exception) {
                commandError("Invalid metadata number argument.");
                return false;
            }
        }
        
        return true;
    }
}
