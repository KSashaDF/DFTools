package dfutils.bettertoolbars;

import dfutils.utils.ItemUtils;
import dfutils.utils.MathUtils;
import dfutils.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
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
    private int selectedTabIndex = 0;
    private int scrollPosition = 0;

    public MainToolbarGui() {
        super(new MainToolbarContainer());

        //If there are no toolbar tabs created yet, create a new one.
        if (ToolbarTabHandler.toolbarTabs == null) {
            ToolbarTabHandler.createToolbarTab();
        }

        updateToolbarSlots();
    }

    private void updateToolbarSlots() {
        try {
            ItemStack[] tabItems = ToolbarTabHandler.toolbarTabs[selectedTabIndex].getTabItems();

            for (int slotIndex = 1; slotIndex < 46; slotIndex++) {
                int tabSlotIndex = (scrollPosition * 9) + (slotIndex - 1);

                //If tabSlotIndex is outside the bounds of the tabItems array,
                //just put an air item into the selected toolbar slot.
                if (tabSlotIndex < tabItems.length) {
                    inventorySlots.getSlot(slotIndex).putStack(tabItems[tabSlotIndex]);
                } else {
                    inventorySlots.getSlot(slotIndex).putStack(ItemStack.EMPTY);
                }
            }

            //Sets the icon slot to the tabs icon slot item.
            inventorySlots.getSlot(0).putStack(ToolbarTabHandler.toolbarTabs[selectedTabIndex].tabIcon);
        } catch (NullPointerException exception) {
            //Uh oh! An NPE, continue on.
        }
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

    //This method overrides the default Minecraft item manipulation behaviour,
    //basically this is a rewrite of Minecraft's entire item manipulation code.
    @Override
    protected void handleMouseClick(Slot slot, int slotIndex, int mouseButton, ClickType clickType) {

        if (slot != null) {
            slotIndex = slot.slotNumber;
        }

        if (clickType == ClickType.PICKUP_ALL)
            clickType = ClickType.PICKUP;

        InventoryPlayer playerInventory = minecraft.player.inventory;

        switch (clickType) {
            case PICKUP:

                //If the slot index is -999, it means the player clicked outside the GUI.
                if (slotIndex == -999) {
                    if (mouseButton == 0) {
                        playerInventory.setItemStack(ItemStack.EMPTY);
                    } else if (mouseButton == 1) {
                        playerInventory.setItemStack(incrementStackSize(playerInventory.getItemStack(), -1));
                    }
                } else if (slot != null) {
                    //Sets the icon slot to the currently dragged item.
                    if (isIconSlot(slotIndex)) {
                        if (mouseButton == 0) {
                            slot.putStack(playerInventory.getItemStack().copy());
                        } else if (mouseButton == 1) {
                            ItemStack itemStack = playerInventory.getItemStack().copy();
                            itemStack.setCount(1);
                            slot.putStack(itemStack);
                        }
                    } else if (isToolbarSlot(slotIndex)) {

                        if (slot.getHasStack()) {
                            if (ItemUtils.areItemsStackable(slot.getStack(), playerInventory.getItemStack())) {
                                playerInventory.setItemStack(incrementStackSize(playerInventory.getItemStack(), slot.getStack().getCount()));
                            } else {
                                playerInventory.setItemStack(slot.getStack().copy());
                            }
                        } else {
                            slot.putStack(playerInventory.getItemStack());
                            playerInventory.setItemStack(ItemStack.EMPTY);
                        }
                    } else if (isHotbarSlot(slotIndex)) {
                        if (slot.getHasStack()) {
                            if (playerInventory.getItemStack().isEmpty()) {
                                if (mouseButton == 0) {
                                    playerInventory.setItemStack(slot.getStack());
                                    slot.putStack(ItemStack.EMPTY);
                                } else if (mouseButton == 1) {
                                    playerInventory.setItemStack(slot.getStack().splitStack(
                                            MathUtils.isOdd(slot.getStack().getCount() / 2) ? (slot.getStack().getCount() / 2) + 1 : slot.getStack().getCount() / 2));
                                }
                            }
                        } else {
                            if (!playerInventory.getItemStack().isEmpty()) {
                                if (mouseButton == 0) {
                                    slot.putStack(playerInventory.getItemStack());
                                    playerInventory.setItemStack(ItemStack.EMPTY);
                                } else if (mouseButton == 1) {
                                    slot.putStack(playerInventory.getItemStack().splitStack(1));
                                }
                            }
                        }
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

                if (playerInventory.getItemStack().isEmpty() || (slot != null && slot.getHasStack() && !ItemUtils.areItemsStackable(playerInventory.getItemStack(), slot.getStack()))) {
                    if (slot != null && slot.getHasStack()) {
                        ItemStack newDraggedItem = slot.getStack().copy();
                        newDraggedItem.setCount(newDraggedItem.getMaxStackSize());

                        playerInventory.setItemStack(newDraggedItem);
                    }
                } else {
                    playerInventory.setItemStack(incrementStackSize(playerInventory.getItemStack(), 1));
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
    }

    @Override
    public void onGuiClosed() {
        if (!minecraft.player.inventory.getItemStack().isEmpty()) {
            minecraft.player.inventory.setItemStack(ItemStack.EMPTY);
        }

        ToolbarTabHandler.saveTabs();
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