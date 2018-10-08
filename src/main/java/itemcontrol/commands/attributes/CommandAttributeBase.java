package itemcontrol.commands.attributes;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandAttributeBase extends CommandBase implements IClientCommand {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "attribute";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§b/attribute add <attribute name> <amount> [operation] [slot] \n" +
                "§b/attribute remove <attribute name> [slot] \n" +
                "§b/attribute clear \n" +
                "§b/attribute list";
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
    
        if (commandArgs.length == 0) {
            infoMessage("Usage:\n" + getUsage(sender));
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

            case "list":
                listAttributes();
                return;
                
            default:
                infoMessage("Usage:\n" + getUsage(sender));
        }
    }

    private void listAttributes() {
        String[] attributeList = {
                "§5§m    §5[§dAttributes§5]§m    ",
                "",
                "§5>§d maxHealth",
                "§5>§d followRange",
                "§5>§d knockbackResistance",
                "§5>§d movementSpeed",
                "§5>§d attackDamage",
                "§5>§d armor",
                "§5>§d armorToughness",
                "§5>§d attackSpeed",
                "§5>§d luck",
                ""
        };

        for (String messageLine : attributeList) {
            minecraft.player.sendMessage(new TextComponentString(messageLine));
        }
    }
}
