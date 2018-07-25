package dfutils.bettertoolbars;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

class ToolbarTab {

    String tabName;
    ItemStack tabIcon;
    ItemStack[] tabItems;

    //Stores the last time that the toolbar tab file was reloaded, used to determine if
    //the toolbar tab file needs to be reloaded.
    long lastReloadedTabTime = 0;

    ToolbarTab() {

    }

    ToolbarTab(NBTTagCompound tabNbt) {

    }
}
