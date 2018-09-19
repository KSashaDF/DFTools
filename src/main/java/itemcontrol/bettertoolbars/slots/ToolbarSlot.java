package itemcontrol.bettertoolbars.slots;

import itemcontrol.bettertoolbars.StateHandler;
import itemcontrol.bettertoolbars.ToolbarTabManager;
import diamondcore.utils.ItemUtils;
import diamondcore.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ToolbarSlot extends SlotBase {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public ToolbarSlot(IInventory inventory, Container container, int slotIndex, int xPosition, int yPosition) {
        super(inventory, container, slotIndex, xPosition, yPosition);
    }
    
    public void onSlotClick(int mouseButton, ClickType clickType) {
        
        InventoryPlayer playerInventory = minecraft.player.inventory;
        
        switch (clickType) {
            case PICKUP:
                if (mouseButton == 0) {
                    if (playerInventory.getItemStack().isEmpty()) {
                        playerInventory.setItemStack(super.getStack().copy());
                    } else {
                        if (ItemUtils.areItemsStackable(super.getStack(), playerInventory.getItemStack())) {
                            playerInventory.setItemStack(ItemUtils.incrementStackSize(playerInventory.getItemStack(), super.getStack().getCount()));
                        } else {
                            playerInventory.setItemStack(ItemStack.EMPTY);
                        }
                    }
                } else if (mouseButton == 1) {
                    if (playerInventory.getItemStack().isEmpty()) {
                        playerInventory.setItemStack(super.getStack().copy().splitStack(MathUtils.ceilingDivide(super.getStack().getCount(), 2)));
                    } else {
                        playerInventory.getItemStack().shrink(1);
                    }
                }
                break;
            
            case QUICK_MOVE:
                if (mouseButton == 0) {
                    if (super.getHasStack()) {
                        playerInventory.setItemStack(super.getStack().copy());
                        playerInventory.getItemStack().setCount(playerInventory.getItemStack().getMaxStackSize());
                    } else {
                        super.putStack(playerInventory.getItemStack());
                        playerInventory.setItemStack(ItemStack.EMPTY);
                        ToolbarTabManager.setTabItem(StateHandler.getScrollRow() + super.getSlotIndex() - 1, super.getStack());
                    }
                } else if (mouseButton == 1) {
                    playerInventory.setItemStack(super.getStack());
                    super.putStack(ItemStack.EMPTY);
                    ToolbarTabManager.setTabItem(StateHandler.getScrollRow() + super.getSlotIndex() - 1, ItemStack.EMPTY);
                }
                break;
            
            case SWAP:
                ItemStack swapStack =  super.getStack().copy();
                if (swapStack.getCount() < swapStack.getMaxStackSize()) {
                    swapStack.setCount(swapStack.getMaxStackSize());
                }
    
                super.container.getSlot(super.container.inventorySlots.size() - 9 + mouseButton).putStack(swapStack);
                break;
            
            case CLONE:
                super.onSlotClick(mouseButton, clickType);
                break;
            
            case THROW:
                break;
        }
    }
}
