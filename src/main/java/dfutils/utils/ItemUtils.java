package dfutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagShort;
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

    public static boolean areItemsStackable(ItemStack itemStack1, ItemStack itemStack2) {
        itemStack1.setCount(1);
        itemStack2.setCount(1);

        return ItemStack.areItemStacksEqual(itemStack1, itemStack2);
    }

    public NBTTagCompound toNbt(ItemStack itemStack) {
        NBTTagCompound itemNbt = new NBTTagCompound();

        if (!itemStack.isEmpty()) {
            itemNbt.setTag("Count", new NBTTagByte((byte) itemStack.getCount()));
            itemNbt.setTag("Damage", new NBTTagShort((short) itemStack.getMetadata()));
            itemNbt.setTag("id", new NBTTagInt(Item.getIdFromItem(itemStack.getItem())));

            if (itemStack.hasTagCompound()) {
                itemNbt.setTag("tag", itemStack.getTagCompound());
            } else {
                itemNbt.setTag("tag", new NBTTagCompound());
            }
        }

        return itemNbt;
    }
}
