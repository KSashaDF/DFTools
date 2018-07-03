package dfutils.commands;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandHelp extends CommandBase implements IClientCommand {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "dfutils";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/dfutils";
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
    
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }
    
    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {
        
        String[] helpMessage = {
                "§6§m    §6[§eCommands§6]§m    ",
                "",
                "§6> §e/give",
                "",
                "  §cNote: §7For those who might think that the",
                "  §7/give command is cheaty, the /give command (and all",
                "  §7other item commands) only work in creative mode!",
                "",
                "§6> §e/itemdata",
                "§6> §e/attribute",
                "§6> §e/lore",
                "§6> §e/candestroy",
                "§6> §e/canplace",
                "§6> §e/renameanvil",
                "§6> §e/breakable",
                "§6> §e/showflags",
                "§6> §e/setflags",
                "",
                "§5§m    §5[§dShortcuts§5]§m    ",
                "",
                "§5> §d/l §7Messages the last person you messaged.",
                "§5> §d/sb §7Shortcut for /support b.",
                "",
                "§2§m    §2[§aCode Tools§2]§m    ",
                "",
                "§2> §a§nPiston Highlighting",
                "",
                "  §7Shift left click a piston to highlight it.",
                "",
                "§2> §a§nLocation Setting",
                "",
                "  §7Left click with a location paper to set it to your",
                "  §7target block location without adding on an extra 0.5.",
                ""
        };
        
        for (String messageLine : helpMessage) {
            minecraft.player.sendMessage(new TextComponentString(messageLine));
        }
    }
}
