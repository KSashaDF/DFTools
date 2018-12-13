package diamondcore.eventhandler.customevents;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class InventoryItemSetEvent extends Event {
	
	public ItemStack setItem;
	public int slot;
	
	public InventoryItemSetEvent(ItemStack setItem, int slot) {
		this.setItem = setItem;
		this.slot = slot;
	}
}