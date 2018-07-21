package dfutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class ItemUtils {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    public static void setItemInHotbar(ItemStack itemStack, boolean selectSlot) {

        //If the players main hand is empty, set the item in the player's main hand.
        //Otherwise, find the next open slot and set the item in that slot.
        if (minecraft.player.getHeldItemMainhand().isEmpty()) {
            minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        } else {
            int firstEmptySlot = minecraft.player.inventory.getFirstEmptyStack();

            //If an open slot has been found, set it there, otherwise, set it in the player's main hand.
            if (firstEmptySlot < 9) {
                minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + firstEmptySlot);
                if (selectSlot) {
                    minecraft.player.inventory.currentItem = firstEmptySlot;
                    minecraft.player.connection.sendPacket(new CPacketHeldItemChange(firstEmptySlot));
                }
            } else {
                minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
            }
        }
    }
}
