package itemcontrol.commands;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandItemData extends CommandBase implements IClientCommand {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "itemdata";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§b/itemdata";
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

        //Creates the click and hover events for the message.
        Style messageStyle = new Style();
        messageStyle.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/internal-set_clipboard true " + itemStack.getTagCompound().toString().replaceAll("§", "&")));
        messageStyle.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("§dClick here to copy the\n§dNBT to your clipboard.")));

        //Creates the actual message text component.
        TextComponentString messageComponent = new TextComponentString("§b" + itemStack.getTagCompound().toString());
        messageComponent.setStyle(messageStyle);

        //Sends the message.
        infoMessage("Item NBT:");
        minecraft.player.sendMessage(messageComponent);
    }
}
