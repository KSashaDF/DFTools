package dfutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.ResourceLocation;

public class ItemUtils {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public static String getName(ItemStack itemStack) {
        return getName(itemStack.getItem());
    }
    
    public static String getName(Item item) {
        return Item.REGISTRY.getNameForObject(item).toString();
    }
    
    public static ItemStack getItem(String itemName, int count, int metadata) {
        if (Item.getByNameOrId(itemName) == null) {
            return ItemStack.EMPTY;
        } else {
            return new ItemStack(Item.getByNameOrId(itemName), count, metadata);
        }
    }
    
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

    //This method converts an item stack into NBT.
    public static NBTTagCompound toNbt(ItemStack itemStack) {
        NBTTagCompound itemNbt = new NBTTagCompound();

        if (!itemStack.isEmpty()) {
            itemNbt.setTag("Count", new NBTTagByte((byte) itemStack.getCount()));
            itemNbt.setTag("Damage", new NBTTagShort((short) itemStack.getMetadata()));

            ResourceLocation itemName = Item.REGISTRY.getNameForObject(itemStack.getItem());
            itemNbt.setString("id", itemName == null ? "minecraft:air" : itemName.toString());

            if (itemStack.getTagCompound() != null) {
                itemNbt.setTag("tag", itemStack.getTagCompound());
            } else {
                itemNbt.setTag("tag", new NBTTagCompound());
            }
        }

        return itemNbt;
    }
}
