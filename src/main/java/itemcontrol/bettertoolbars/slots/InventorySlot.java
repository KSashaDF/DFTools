package itemcontrol.bettertoolbars.slots;

import diamondcore.utils.ItemUtils;
import diamondcore.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class InventorySlot extends SlotBase {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public InventorySlot(IInventory inventory, Container container, int slotIndex, int xPosition, int yPosition) {
        super(inventory, container, slotIndex, xPosition, yPosition);
    }
    
    public void onSlotClick(int mouseButton, ClickType clickType) {
        
        InventoryPlayer playerInventory = minecraft.player.inventory;
    
        switch (clickType) {
            case PICKUP:
                if (super.getHasStack()) {
                    //CONDITION BRANCH: The following actions are for when the slot HAS an item and
                    //when there is NO dragged item.
                    if (playerInventory.getItemStack().isEmpty()) {
                        if (mouseButton == 0) {
                            playerInventory.setItemStack(super.getStack());
                            super.putStack(ItemStack.EMPTY);
                        } else if (mouseButton == 1) {
                            playerInventory.setItemStack(super.getStack().splitStack(MathUtils.ceilingDivide(super.getStack().getCount(), 2)));
                        }
            
                        //CONDITION BRANCH: The following actions are for when the slot HAS an item and
                        //when there IS a dragged item.
                    } else {
                        if (ItemUtils.areItemsStackable(super.getStack(), playerInventory.getItemStack())) {
                            if (mouseButton == 0) {
                                int stackSpaceLeft = super.getStack().getMaxStackSize() - super.getStack().getCount();
                    
                                if (stackSpaceLeft > 0) {
                                    if (stackSpaceLeft >= playerInventory.getItemStack().getCount()) {
                                        super.getStack().grow(playerInventory.getItemStack().getCount());
                                        playerInventory.setItemStack(ItemStack.EMPTY);
                                    } else {
                                        super.getStack().setCount(super.getStack().getMaxStackSize());
                                        playerInventory.getItemStack().shrink(stackSpaceLeft);
                                    }
                                }
                            } else if (mouseButton == 1) {
                                if (super.getStack().getCount() < super.getStack().getMaxStackSize()) {
                                    super.getStack().grow(1);
                                    playerInventory.getItemStack().shrink(1);
                                }
                            }
                        } else {
                            //Swaps the dragged item and the slot item.
                            ItemStack tempItemStack = super.getStack();
                            super.putStack(playerInventory.getItemStack());
                            playerInventory.setItemStack(tempItemStack);
                        }
                    }
                } else {
                    //CONDITION BRANCH: The following actions are for when the slot is EMPTY and
                    //when there IS a dragged item.
                    if (!playerInventory.getItemStack().isEmpty()) {
                        if (mouseButton == 0) {
                            super.putStack(playerInventory.getItemStack());
                            playerInventory.setItemStack(ItemStack.EMPTY);
                        } else if (mouseButton == 1) {
                            super.putStack(playerInventory.getItemStack().splitStack(1));
                        }
                    }
                }
                break;
                
            case QUICK_MOVE:
                super.onSlotClick(mouseButton, clickType);
                break;
                
            case SWAP:
                Slot hotbarSlot = super.container.getSlot(super.container.inventorySlots.size() - 9 + mouseButton);
                ItemStack tempItemStack = hotbarSlot.getStack();
                hotbarSlot.putStack(super.getStack());
                super.putStack(tempItemStack);
                break;
                
            case CLONE:
                super.onSlotClick(mouseButton, clickType);
                break;
                
            case THROW:
                super.onSlotClick(mouseButton, clickType);
                break;
        }
    }
}
