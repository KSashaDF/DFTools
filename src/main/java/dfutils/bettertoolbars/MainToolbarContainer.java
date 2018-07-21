package dfutils.bettertoolbars;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MainToolbarContainer extends Container {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    private InventoryBasic basicInventory = new InventoryBasic("Better Toolbars", false, 55);

    MainToolbarContainer() {
        //This ensures that this inventory will not interact with the players default inventory.
        windowId = -1;

        //Adds the tab icon slot.
        addSlotToContainer(new Slot(basicInventory, 0, 9, 8));

        //Adds the main toolbar slots.
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 5; y++) {
                addSlotToContainer(new Slot(basicInventory, x + 1 + y * 9, 9 + x * 18, 29 + y * 18));
            }
        }

        //Adds the hotbar slots.
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(basicInventory, x + 46, 9 + x * 18, 123));
            basicInventory.setInventorySlotContents(x + 46, minecraft.player.inventoryContainer.getSlot(x + 36).getStack());
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    //This method is called when an item is shift clicked.
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

        //Checks if the index is within the hotbar or is in the icon slot, if so, clears the slot.
        if (index >= inventorySlots.size() - 9 || index == 0) {
            inventorySlots.get(index).putStack(ItemStack.EMPTY);
        }

        return ItemStack.EMPTY;
    }
}
