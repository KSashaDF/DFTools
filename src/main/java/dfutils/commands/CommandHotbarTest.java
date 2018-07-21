package dfutils.commands;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandHotbarTest extends CommandBase implements IClientCommand {

    private final Minecraft minecraft = Minecraft.getMinecraft();

    public String getName() {
        return "slot";
    }

    public String getUsage(ICommandSender sender) {
        return "§c/slot <slots>";
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {

        for (String commandArg : commandArgs) {
            try {
                minecraft.player.connection.sendPacket(new CPacketHeldItemChange(CommandBase.parseInt(commandArg)));
            } catch (NumberInvalidException exception) {
                //Uh oh! Invalid number argument format! Continue on.
            }
        }
    }
}
