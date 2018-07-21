package dfutils.bettertoolbars;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

class ToolbarTab {

    String tabName;
    ItemStack tabIcon;
    ItemStack[] tabItems;

    ToolbarTab() {

    }

    ToolbarTab(NBTTagCompound tabNbt) {

    }
}
