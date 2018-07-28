package dfutils.commands.itemcontrol;

import dfutils.commands.CommandUtils;
import dfutils.utils.ItemUtils;
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
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandGive extends CommandBase implements IClientCommand {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    //Note that this name is changed from /give to /dfGive so it can be disabled in singleplayer.
    public String getName() {
        return "give";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§b/give <item> [amount] [metadata] [NBT]";
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
            errorMessage("Invalid argument!");
            return;
        }
    
        NBTTagCompound itemNbt = new NBTTagCompound();
        
        if (commandArgs.length >= 4) {
            try {
                String itemNbtString = buildString(commandArgs, 3);
                itemNbtString = CommandUtils.parseColorCodes(itemNbtString);
    
                itemNbt = JsonToNBT.getTagFromJson(itemNbtString);
            } catch (NBTException exception) {
                errorMessage("Invalid NBT format! " + exception.getMessage());
                return;
            }
        }
        
        itemStack.setTagCompound(itemNbt);
        
        //Sends item to the server.
        ItemUtils.setItemInHotbar(itemStack, false);

        player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
    }
    
    private boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (!minecraft.player.isCreative()) {
            errorMessage("You need to be in build mode or dev mode to do this!");
            return false;
        }
        
        if (commandArgs.length < 1) {
            infoMessage("Usage:\n" + getUsage(sender));
            return false;
        }
        
        if (commandArgs.length >= 2) {
            try {
                if (parseInt(commandArgs[1]) < 1 || parseInt(commandArgs[1]) > 64) {
                    errorMessage("Invalid stack size.");
                    return false;
                }
            } catch (NumberInvalidException exception) {
                errorMessage("Invalid stack size number argument.");
                return false;
            }
        }
        
        if (commandArgs.length >= 3) {
            try {
                if (parseInt(commandArgs[2]) < 0) {
                    errorMessage("Invalid metadata number.");
                    return false;
                }
            } catch (NumberInvalidException exception) {
                errorMessage("Invalid metadata number argument.");
                return false;
            }
        }

        return true;
    }

    //This code is used for making sure this command only works if
    //the player does not have permission to use the regular /give command.
    public static void commandGiveClientSendMessage(ClientChatEvent event) {

        if (!(minecraft.player.getPermissionLevel() >= new net.minecraft.command.CommandGive().getRequiredPermissionLevel())) {
            if (event.getMessage().startsWith("/give ")) {
                event.setMessage(event.getMessage().replaceFirst("/give ", "/give "));
            } else if (event.getMessage().equals("/give")) {
                event.setMessage("/give");
            }
        }
    }
}
