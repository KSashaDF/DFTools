package dfutils.commands.itemcontrol.attributes;

import dfutils.commands.CommandUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

import static dfutils.commands.MessageUtils.*;

class CommandAttributeAdd {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeAddAttribute(ICommandSender sender, String[] commandArgs) {
    
        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;
    
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        
        if (itemStack.isEmpty()) {
            commandError("Invalid item!");
            return;
        }
        
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
            
            commandAction("Added attribute to item.");
        
        } catch (NumberInvalidException exception) {
            commandError("Invalid number argument.");
        }
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length >= 3 && commandArgs.length <= 5) {
            
            if (commandArgs.length >= 4) {
                try {
                    if (CommandBase.parseInt(commandArgs[3]) < 0 || CommandBase.parseInt(commandArgs[3]) > 2) {
                        commandError("Operation argument must be equal to either 0, 1, or 2.");
                        return false;
                    }
                } catch (NumberInvalidException exception) {
                    commandError("Operation argument must be a valid number.");
                    return false;
                }
            }
            
            if (commandArgs.length == 5) {
                if (CommandUtils.parseSlotText(commandArgs[4]) == null) {
                    commandError("Invalid slot name! Valid slot names: main_hand, off_hand, helmet, chest, leggings, or boots.");
                    return false;
                }
            }
            
            return true;
            
        } else {
            commandInfo("Usage:\n" + new CommandAttributeBase().getUsage(sender));
            return false;
        }
    }
}
