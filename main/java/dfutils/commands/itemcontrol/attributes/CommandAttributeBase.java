package dfutils.commands.itemcontrol.attributes;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.commands.MessageUtils.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandAttributeBase extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "attribute";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/attribute add <attribute name> <amount> [operation] [slot] \n" +
                "§c/attribute remove <attribute name> [slot] \n" +
                "§c/attribute clear";
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
        
        switch (commandArgs[0]) {
            case "add":
                CommandAttributeAdd.executeAddAttribute(sender, commandArgs);
                return;
                
            case "remove":
                CommandAttributeRemove.executeRemoveAttribute(sender, commandArgs);
                return;
                
            case "clear":
                CommandAttributeClear.executeClearAttributes(sender, commandArgs);
                return;
                
            default:
                commandError("Usage:\n" + getUsage(sender));
        }
    }
}
