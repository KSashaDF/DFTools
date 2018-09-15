package itemcontrol.bettertoolbars.slots;

import dfutils.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class SlotBase extends Slot {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    protected final Container container;
    
    SlotBase(IInventory inventory, Container container, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
        this.container = container;
    }
    
    public void onSlotClick(int mouseButton, ClickType clickType) {
        
        InventoryPlayer playerInventory = minecraft.player.inventory;
    
        switch (clickType) {
            case PICKUP:
                break;
        
            case QUICK_MOVE:
                if (super.getHasStack()) {
                    if (mouseButton == 0) {
                        super.putStack(ItemStack.EMPTY);
                    } else if (mouseButton == 1) {
                        super.getStack().shrink(1);
                    }
                }
                break;
        
            case SWAP:
                break;
        
            case CLONE:
                //If the player is not dragging an item, and is hovering over another item,
                //set their dragged item to the one being hovered over with their cursor.
                //Otherwise, if the player already has an item on their cursor, increment that item's
                //stack size.
    
                if (super.getHasStack() && (playerInventory.getItemStack().isEmpty() || !ItemUtils.areItemsStackable(playerInventory.getItemStack(), super.getStack()))) {
                    ItemStack newDraggedItem = super.getStack().copy();
                    newDraggedItem.setCount(newDraggedItem.getMaxStackSize());
    
                    playerInventory.setItemStack(newDraggedItem);
                } else {
                    playerInventory.setItemStack(ItemUtils.incrementStackSize(playerInventory.getItemStack(), 1));
                }
                break;
        
            case THROW:
                //If the player presses Q while hovering over a hotbar item, or the icon item,
                //decrease the item's stack size by 1. If the player presses
                //Q while hovering over a hotbar item/icon item AND while holding down control,
                //remove the entire item stack.
    
                if (mouseButton == 0) {
                    super.putStack(ItemUtils.incrementStackSize(super.getStack(), -1));
                } else if (mouseButton == 1) {
                    super.putStack(ItemStack.EMPTY);
                }
                break;
        }
    }
}
