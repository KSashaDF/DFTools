package dfutils.codetools.commands;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.commands.MessageUtils.commandError;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandCodeBase extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "code";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/code select \n" +
                "§c/code copy";
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
            commandError("Usage: \n" + getUsage(sender));
            return;
        }
        
        switch (commandArgs[0]) {
            case "select":
                CommandSelectStick.executeSelectStick(sender, commandArgs);
                return;
                
            case "copy":
        }
    }
}
