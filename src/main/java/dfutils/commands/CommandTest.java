package dfutils.commands;

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

        /* try {
            minecraft.player.sendMessage(new TextComponentString("Plot Name: " + PlayerStateHandler.plotName));
            minecraft.player.sendMessage(new TextComponentString("Plot Owner: " + PlayerStateHandler.plotOwner));
            minecraft.player.sendMessage(new TextComponentString("Plot ID: " + PlayerStateHandler.plotId));
            minecraft.player.sendMessage(new TextComponentString("Plot Size: " + PlayerStateHandler.plotSize.name()));
            minecraft.player.sendMessage(new TextComponentString("Plot Corner: " + PlayerStateHandler.plotCorner.toString()));
            minecraft.player.sendMessage(new TextComponentString("Player Mode: " + PlayerStateHandler.playerMode.name()));
            minecraft.player.sendMessage(new TextComponentString(""));
            minecraft.player.sendMessage(new TextComponentString("In Session: " + PlayerStateHandler.isInSupportSession));
            minecraft.player.sendMessage(new TextComponentString("Session Partner: " + PlayerStateHandler.supportPartner));
            minecraft.player.sendMessage(new TextComponentString("Session Role: " + PlayerStateHandler.supportSessionRole.name()));
        } catch (NullPointerException exception) {

        } */
    
        /*minecraft.player.sendMessage(LanguageManager.getMessage("test.text"));
        minecraft.player.sendMessage(LanguageManager.getMessage("test.variableText", "String1", "String2"));
    
        MessageHelper.errorMessage("test.text");
        MessageHelper.infoMessage("test.multiColorText");
        MessageHelper.infoMessage("test.multiColorTextList");*/
    }
}
