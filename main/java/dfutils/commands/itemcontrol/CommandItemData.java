package dfutils.commands.itemcontrol;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

public class CommandItemData extends CommandBase implements IClientCommand {
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "itemdata";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§e/itemdata";
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
    
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }
    
    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {
    
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
    
        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }
    
        //Checks if item has NBT tag.
        if (itemStack.getTagCompound() == null) {
            errorMessage("This item does not have any NBT.");
            return;
        }
        
        infoMessage("Item NBT: \n§6" + itemStack.getTagCompound().toString());
    }
}
