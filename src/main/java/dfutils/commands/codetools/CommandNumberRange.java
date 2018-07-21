package dfutils.commands.codetools;

import dfutils.codetools.CodeItems;
import dfutils.utils.ItemUtils;
import dfutils.utils.MathUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandNumberRange extends CommandBase implements IClientCommand {

    private final Minecraft minecraft = Minecraft.getMinecraft();

    public String getName() {
        return "num";
    }

    public String getUsage(ICommandSender sender) {
        return "§b/num <number 1> [number 2] [stack size]";
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

        if (commandArgs.length < 1 || commandArgs.length > 3) {
            infoMessage("Usage: " + getUsage(sender));
            return;
        }

        if (commandArgs.length == 1) {
            try {
                ItemUtils.setItemInHotbar(CodeItems.getNumberSlimeball(CommandBase.parseInt(commandArgs[0]), 1), false);

                minecraft.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F, 1.5F);
                return;
            } catch(NumberInvalidException exception) {
                errorMessage("Invalid number argument! Number must be an integer.");
                return;
            }
        }

        int numRangeLower;
        int numRangeHigher;
        int stackSize;

        try {
            numRangeLower = CommandBase.parseInt(commandArgs[0]);
            numRangeHigher = CommandBase.parseInt(commandArgs[1]);

            if (commandArgs.length == 3) {
                stackSize = CommandBase.parseInt(commandArgs[2]);

                if (stackSize < 1 || stackSize > 64) {
                    errorMessage("Stack size must be between 1 and 64!");
                    return;
                }
            } else {
                stackSize = 1;
            }
        } catch (NumberInvalidException exception) {
            errorMessage("Invalid number argument! Number must be an integer.");
            return;
        }

        //Checks if the lower and higher range numbers are within a range of 36 of each other
        if (Math.abs(numRangeLower - numRangeHigher) > 35) {
            errorMessage("Number range must be less than or equal to 36!");
            return;
        }

        int slot = 0;
        int number = numRangeLower;
        minecraft.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.5F);

        do {

            if (slot < 9) {
                minecraft.playerController.sendSlotPacket(CodeItems.getNumberSlimeball(number, stackSize), minecraft.player.inventoryContainer.inventorySlots.size() - 10 + slot);
            } else {
                minecraft.playerController.sendSlotPacket(CodeItems.getNumberSlimeball(number, stackSize), slot);
            }

            slot++;
            if (numRangeLower < numRangeHigher) {
                number++;
            } else {
                number--;
            }
        } while (MathUtils.withinRange(number, numRangeLower, numRangeHigher));
    }
}
