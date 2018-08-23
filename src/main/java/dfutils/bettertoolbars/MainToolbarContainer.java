package dfutils.bettertoolbars;

import dfutils.bettertoolbars.slots.IconSlot;
import dfutils.bettertoolbars.slots.InventorySlot;
import dfutils.bettertoolbars.slots.ToolbarSlot;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MainToolbarContainer extends Container {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    MainToolbarContainer() {
        InventoryBasic basicInventory = new InventoryBasic("Better Toolbars", false, 55);
        
        //This ensures that this inventory will not interact with the players default inventory.
        windowId = -1;

        //Adds the tab icon slot.
        addSlotToContainer(new IconSlot(basicInventory, this, 0, 9, 8));

        //Adds the main toolbar slots.
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new ToolbarSlot(basicInventory, this, x + 1 + y * 9, 9 + x * 18, 29 + y * 18));
            }
        }

        //Adds the hotbar slots.
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new InventorySlot(basicInventory, this, x + 46, 9 + x * 18, 123));
            basicInventory.setInventorySlotContents(x + 46, minecraft.player.inventoryContainer.getSlot(x + 36).getStack());
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
