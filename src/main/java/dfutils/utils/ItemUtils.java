package dfutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class ItemUtils {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    public static void setItemInHotbar(ItemStack itemStack, boolean selectSlot) {

        int firstEmptySlot = minecraft.player.inventory.getFirstEmptyStack();

        if (firstEmptySlot <= 9) {
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
