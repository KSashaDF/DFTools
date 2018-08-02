package dfutils.commands.itemcontrol.attributes;

import dfutils.commands.CommandUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import static dfutils.utils.MessageUtils.actionMessage;
import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

class CommandAttributeAdd {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeAddAttribute(ICommandSender sender, String[] commandArgs) {
    
        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;
    
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }

        //If the attribute name does not already have a "generic." prefix, add it on.
        if (!commandArgs[1].contains("."))
            commandArgs[1] = "generic." + commandArgs[1];

        try {
    
            //Gets operation argument.
            int attributeOperation = 0;
            if (commandArgs.length >= 4) {
                attributeOperation = CommandBase.parseInt(commandArgs[3]);
            }
    
            //Applies attribute to item.
            if (commandArgs.length == 5) {
                itemStack.addAttributeModifier(commandArgs[1],
                        new AttributeModifier(commandArgs[1], CommandBase.parseDouble(commandArgs[2]), attributeOperation), CommandUtils.parseSlotText(commandArgs[4]));
            } else {
                itemStack.addAttributeModifier(commandArgs[1],
                        new AttributeModifier(commandArgs[1], CommandBase.parseDouble(commandArgs[2]), attributeOperation), null);
            }
        
            //Sends attribute item to server.
            minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
            
            actionMessage("Added attribute to item.");
        
        } catch (NumberInvalidException exception) {
            errorMessage("Invalid number argument.");
        }
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length >= 3 && commandArgs.length <= 5) {
            
            if (commandArgs.length >= 4) {
                try {
                    if (CommandBase.parseInt(commandArgs[3]) < 0 || CommandBase.parseInt(commandArgs[3]) > 2) {
                        errorMessage("Operation argument must be equal to either 0, 1, or 2.");
                        return false;
                    }
                } catch (NumberInvalidException exception) {
                    errorMessage("Operation argument must be a valid number.");
                    return false;
                }
            }
            
            if (commandArgs.length == 5) {
                if (CommandUtils.parseSlotText(commandArgs[4]) == null) {
                    errorMessage("Invalid slot name! Valid slot names: main_hand, off_hand, helmet, chest, leggings, or boots.");
                    return false;
                }
            }
            
            return true;
            
        } else {
            infoMessage("Usage:\n" + new CommandAttributeBase().getUsage(sender));
            return false;
        }
    }
}
