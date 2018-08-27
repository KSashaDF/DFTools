package dfutils.bettertoolbars.slots;

import dfutils.bettertoolbars.MainToolbarGui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class IconSlot extends SlotBase {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public IconSlot(IInventory inventory, Container container, int slotIndex, int xPosition, int yPosition) {
        super(inventory, container,slotIndex, xPosition, yPosition);
    }
    
    public void onSlotClick(int mouseButton, ClickType clickType) {
        
        InventoryPlayer playerInventory = minecraft.player.inventory;
        
        switch (clickType) {
            case PICKUP:
                if (mouseButton == 0) {
                    super.putStack(playerInventory.getItemStack().copy());
                } else if (mouseButton == 1) {
                    ItemStack itemStack = playerInventory.getItemStack().copy();
                    itemStack.setCount(1);
                    super.putStack(itemStack);
                }
                break;
            
            case QUICK_MOVE:
                super.onSlotClick(mouseButton, clickType);
                break;
            
            case SWAP:
                super.putStack(super.container.getSlot(super.container.inventorySlots.size() - 9 + mouseButton).getStack().copy());
                break;
            
            case CLONE:
                super.onSlotClick(mouseButton, clickType);
                break;
            
            case THROW:
                super.onSlotClick(mouseButton, clickType);
                break;
        }
        
        ((MainToolbarGui) minecraft.currentScreen).modifySlot(super.getSlotIndex());
    }
}
