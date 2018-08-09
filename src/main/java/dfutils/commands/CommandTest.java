package dfutils.commands;

import dfutils.utils.language.LanguageManager;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandTest extends CommandBase implements IClientCommand {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "test";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/test";
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
    
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }
    
    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {
        
        /*try {
            MessageUtils.infoMessage("Plot Name: " + PlayerStateHandler.plotName);
            MessageUtils.infoMessage("Plot Owner: " + PlayerStateHandler.plotOwner);
            MessageUtils.infoMessage("Plot ID: " + PlayerStateHandler.plotId);
            MessageUtils.infoMessage("Plot Size: " + PlayerStateHandler.plotSize.name());
            MessageUtils.infoMessage("Plot Corner: " + PlayerStateHandler.plotCorner.toString());
            MessageUtils.infoMessage("Player Mode: " + PlayerStateHandler.playerMode.name());
            MessageUtils.infoMessage("");
            MessageUtils.infoMessage("In Session: " + PlayerStateHandler.isInSupportSession);
            MessageUtils.infoMessage("Session Partner: " + PlayerStateHandler.supportPartner);
            MessageUtils.infoMessage("Session Role: " + PlayerStateHandler.supportSessionRole.name());
        } catch (NullPointerException exception) {
        
        }*/
    
        minecraft.player.sendMessage(LanguageManager.getMessage("test.text"));
        minecraft.player.sendMessage(LanguageManager.getMessage("test.colorText"));
        minecraft.player.sendMessage(LanguageManager.getMessage("test.textList"));
        minecraft.player.sendMessage(LanguageManager.getMessage("test.colorTextList"));
    
        minecraft.player.sendMessage(LanguageManager.getMessage("test.variableText", "String1", "String2"));
        minecraft.player.sendMessage(LanguageManager.getMessage("test.variableColorText", "String1", "String2"));
    
        minecraft.player.sendMessage(LanguageManager.getMessage("test.multiColorText"));
        minecraft.player.sendMessage(LanguageManager.getMessage("test.multiColorTextList"));
    }
}
