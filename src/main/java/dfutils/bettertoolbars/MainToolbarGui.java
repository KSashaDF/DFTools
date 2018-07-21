package dfutils.bettertoolbars;

import dfutils.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MainToolbarGui extends GuiContainer {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("dfutils:textures/gui/toolbar_tab.png");
    private Slot hoveredSlot;

    public MainToolbarGui() {
        super(new MainToolbarContainer());
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, 195, 147);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //Makes the background tinted gray.
        drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        //Finds the currently hovered slot.
        //(note that the hovered slot is already found in the GuiContainer class, but it has private access)
        for (int slotIndex = 0; slotIndex < inventorySlots.inventorySlots.size(); slotIndex++) {
            Slot slot = inventorySlots.inventorySlots.get(slotIndex);

            if (isMouseOverSlot(slot, mouseX, mouseY)) {
                hoveredSlot = slot;
            }
        }

        try {
            //Detects whether the slot is the tab icon slot, if so, don't draw the item tooltip.
            if (hoveredSlot.getSlotIndex() != 0) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableLighting();
                renderHoveredToolTip(mouseX, mouseY);
            }
        } catch (NullPointerException exception) {
            //Uh oh! An NPE happened for some reason, continue on.
        }
    }

    private boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
        return isPointInRegion(slot.xPos, slot.yPos, 16, 16, mouseX, mouseY);
    }

    @Override
    protected void handleMouseClick(Slot slot, int slotIndex, int mouseButton, ClickType clickType) {

        if (slot != null) {
            slotIndex = slot.slotNumber;
        }

        if (clickType == ClickType.PICKUP_ALL)
            clickType = ClickType.PICKUP;

        switch (clickType) {
            case PICKUP:

                //If the slot index is -999, it means the player clicked outside the GUI.
                if (slotIndex == -999) {
                    if (mouseButton == 0) {
                        minecraft.player.inventory.setItemStack(ItemStack.EMPTY);
                    } else if (mouseButton == 1) {
                        minecraft.player.inventory.setItemStack(incrementStackSize(minecraft.player.inventory.getItemStack(), -1));
                    }
                } else if (slot != null) {

                    //Sets the icon slot to the currently dragged item.
                    if (isIconSlot(slotIndex)) {
                        inventorySlots.getSlot(0).putStack(minecraft.player.inventory.getItemStack());
                    }
                }
                break;

            case QUICK_MOVE:
                break;

            case SWAP:
                break;

            case CLONE:
                //If the player is not dragging an item, and is hovering over another item,
                //set their dragged item to the one being hovered over with their cursor.
                //Otherwise, if the player already has an item on their cursor, increment that item's
                //stack size.

                if (minecraft.player.inventory.getItemStack().isEmpty()) {
                    if (slot != null && slot.getHasStack()) {
                        ItemStack newDraggedItem = slot.getStack().copy();
                        newDraggedItem.setCount(newDraggedItem.getMaxStackSize());

                        minecraft.player.inventory.setItemStack(newDraggedItem);
                    }
                } else {
                    minecraft.player.inventory.setItemStack(incrementStackSize(minecraft.player.inventory.getItemStack(), 1));
                }
                break;

            case THROW:
                //If the player presses Q while hovering over a hotbar item, or the icon item,
                //decrease the item's stack size by 1. If the player presses
                //Q while hovering over a hotbar item/icon item AND while holding down control,
                //remove the entire item stack.

                if (isHotbarSlot(slotIndex) || isIconSlot(slotIndex)) {
                    if (slot != null) {
                        if (mouseButton == 0) {
                            slot.putStack(incrementStackSize(slot.getStack(), -1));
                        } else if (mouseButton == 1) {
                            slot.putStack(ItemStack.EMPTY);
                        }
                    }
                }
                break;
        }
        //MessageUtils.infoMessage(mouseButton + ", " + slotIndex + ": " + clickType);

        //Syncs the players hotbar with the hotbar in the toolbar menu.
        detectAndSendHotbarChanges();

        minecraft.player.inventoryContainer.detectAndSendChanges();
    }

    private void detectAndSendHotbarChanges() {

        for (int slotIndex = 0; slotIndex < 9; slotIndex++) {
            ItemStack hotbarStack = inventorySlots.getSlot(slotIndex + 46).getStack();
            ItemStack playerInvItem = minecraft.player.inventory.getStackInSlot(slotIndex);

            if (!ItemStack.areItemStackShareTagsEqual(hotbarStack, playerInvItem)) {
                minecraft.playerController.sendSlotPacket(hotbarStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + slotIndex);
            }
        }
    }

    private ItemStack incrementStackSize(ItemStack itemStack, int stackIncrement) {

        if (itemStack.getCount() + stackIncrement <= 0) {
            itemStack = ItemStack.EMPTY;
        } else {
            if (itemStack.getCount() + stackIncrement > itemStack.getMaxStackSize()) {
                itemStack.setCount(itemStack.getMaxStackSize());
            } else {
                itemStack.setCount(itemStack.getCount() + stackIncrement);
            }
        }

        return itemStack;
    }

    private boolean isIconSlot(int slotIndex) {
        return slotIndex == 0;
    }

    private boolean isToolbarSlot(int slotIndex) {
        return !isIconSlot(slotIndex) && !isHotbarSlot(slotIndex);
    }

    private boolean isHotbarSlot(int slotIndex) {
        return slotIndex >= inventorySlots.inventorySlots.size() - 9 && slotIndex < inventorySlots.inventorySlots.size();
    }
}