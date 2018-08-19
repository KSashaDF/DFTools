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

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("dfutils:textures/gui/toolbar_tab.png");
    private int selectedTabIndex = 0;
    private int scrollPosition = 0;
    private boolean startOfItemDrag = false;

    public MainToolbarGui() {
        super(new MainToolbarContainer());

        //If there are no toolbar tabs created yet, create a new one.
        if (ToolbarTabHandler.toolbarTabs == null) {
            ToolbarTabHandler.createToolbarTab();
        }

        updateToolbarSlots();
    }

    private void updateToolbarSlots() {
        dragSplitting = false;
        
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

        try {
            //Detects whether the hovered slot is the tab icon slot, if so, don't draw the item tooltip.
            if (getSlotUnderMouse() == null || !isIconSlot(getSlotUnderMouse().slotNumber)) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableLighting();
                renderHoveredToolTip(mouseX, mouseY);
            }
        } catch (NullPointerException exception) {
            //Uh oh! An NPE happened for some reason, continue on.
        }
    }

    //This method overrides the default Minecraft item manipulation behaviour,
    //basically this is a rewrite of Minecraft's entire item manipulation code.
    @Override
    protected void handleMouseClick(Slot slot, int slotIndex, int mouseButton, ClickType clickType) {
        
        if (slot != null) {
            slotIndex = slot.slotNumber;
        }

        if (clickType == ClickType.PICKUP_ALL) {
            clickType = ClickType.PICKUP;
        }
        
        //The QUICK_CRAFT click type is activated when the player drags an item.
        //If the mouse button is 0 or 4 it means this is the start of the drag item
        //sequence.
        if (clickType == ClickType.QUICK_CRAFT) {
            if (mouseButton == 0 || mouseButton == 4) {
                startOfItemDrag = true;
                return;
            }
            
            if ((mouseButton == 1 || mouseButton == 5) && startOfItemDrag) {
                startOfItemDrag = false;
                clickType = ClickType.PICKUP;
                
                if (mouseButton == 1) {
                    mouseButton = 0;
                } else {
                    mouseButton = 1;
                }
            } else {
                return;
            }
        }

        InventoryPlayer playerInventory = minecraft.player.inventory;

        switch (clickType) {
            case PICKUP:

                //If the slot index is -999, it means the player clicked outside the GUI.
                //The following code handles clicking outside the GUI. Normally clicking outside the GUI would
                //drop the dragged item. (here it simply deletes it)
                if (slotIndex == -999) {
                    if (mouseButton == 0) {
                        playerInventory.setItemStack(ItemStack.EMPTY);
                    } else if (mouseButton == 1) {
                        playerInventory.getItemStack().shrink(1);
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

                        if (mouseButton == 0 || playerInventory.getItemStack().isEmpty()) {
                            if (slot.getHasStack()) {
                                if (ItemUtils.areItemsStackable(slot.getStack(), playerInventory.getItemStack())) {
                                    playerInventory.setItemStack(incrementStackSize(playerInventory.getItemStack(), slot.getStack().getCount()));
                                } else {
                                    if (mouseButton == 0) {
                                        playerInventory.setItemStack(slot.getStack().copy());
                                    } else if (mouseButton == 1) {
                                        playerInventory.setItemStack(slot.getStack().copy().splitStack(MathUtils.roundUpDivide(slot.getStack().getCount(), 2)));
                                    }
                                }
                            } else {
                                playerInventory.setItemStack(ItemStack.EMPTY);
                            }
                        } else {
                            playerInventory.getItemStack().shrink(1);
                        }
                    } else if (isHotbarSlot(slotIndex)) {
                        if (slot.getHasStack()) {
                            //CONDITION BRANCH: The following actions are for when the slot HAS an item and
                            //when there is NO dragged item.
                            if (playerInventory.getItemStack().isEmpty()) {
                                if (mouseButton == 0) {
                                    playerInventory.setItemStack(slot.getStack());
                                    slot.putStack(ItemStack.EMPTY);
                                } else if (mouseButton == 1) {
                                    playerInventory.setItemStack(slot.getStack().splitStack(MathUtils.roundUpDivide(slot.getStack().getCount(), 2)));
                                }
                                
                                //CONDITION BRANCH: The following actions are for when the slot HAS an item and
                                //when there IS a dragged item.
                            } else {
                                if (ItemUtils.areItemsStackable(slot.getStack(), playerInventory.getItemStack())) {
                                    if (mouseButton == 0) {
                                        int stackSpaceLeft = slot.getStack().getMaxStackSize() - slot.getStack().getCount();
                                        
                                        if (stackSpaceLeft > 0) {
                                            if (stackSpaceLeft >= playerInventory.getItemStack().getCount()) {
                                                slot.getStack().grow(playerInventory.getItemStack().getCount());
                                                playerInventory.setItemStack(ItemStack.EMPTY);
                                            } else {
                                                slot.getStack().setCount(slot.getStack().getMaxStackSize());
                                                playerInventory.getItemStack().shrink(stackSpaceLeft);
                                            }
                                        }
                                    } else if (mouseButton == 1) {
                                        if (slot.getStack().getCount() < slot.getStack().getMaxStackSize()) {
                                            slot.getStack().grow(1);
                                            playerInventory.getItemStack().shrink(1);
                                        }
                                    }
                                } else {
                                    //Swaps the dragged item and the slot item.
                                    ItemStack tempItemStack = slot.getStack();
                                    slot.putStack(playerInventory.getItemStack());
                                    playerInventory.setItemStack(tempItemStack);
                                }
                            }
                        } else {
                            //CONDITION BRANCH: The following actions are for when the slot is EMPTY and
                            //when there IS a dragged item.
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
                if (slot != null) {
                    if (isToolbarSlot(slotIndex)) {
                        if (mouseButton == 0) {
                            if (slot.getHasStack()) {
                                playerInventory.setItemStack(slot.getStack().copy());
                                playerInventory.getItemStack().setCount(playerInventory.getItemStack().getMaxStackSize());
                            } else {
                                slot.putStack(playerInventory.getItemStack());
                                playerInventory.setItemStack(ItemStack.EMPTY);
                            }
                        } else if (mouseButton == 1) {
                            slot.putStack(ItemStack.EMPTY);
                        }
                    } else {
                        if (slot.getHasStack()) {
                            if (mouseButton == 0) {
                                slot.putStack(ItemStack.EMPTY);
                            } else if (mouseButton == 1) {
                                slot.getStack().shrink(1);
                            }
                        }
                    }
                }
                break;

            case SWAP:
                if (slot != null) {
                    if (isIconSlot(slotIndex)) {
                        slot.putStack(inventorySlots.getSlot(inventorySlots.inventorySlots.size() - 9 + mouseButton).getStack().copy());
                    } else if (isToolbarSlot(slotIndex)) {
                        ItemStack swapStack =  slot.getStack().copy();
                        if (swapStack.getCount() < swapStack.getMaxStackSize()) {
                            swapStack.setCount(swapStack.getMaxStackSize());
                        }
                        
                        inventorySlots.getSlot(inventorySlots.inventorySlots.size() - 9 + mouseButton).putStack(swapStack);
                    } else if (isHotbarSlot(slotIndex)) {
                        Slot hotbarSlot = inventorySlots.getSlot(inventorySlots.inventorySlots.size() - 9 + mouseButton);
                        ItemStack tempItemStack = hotbarSlot.getStack();
                        hotbarSlot.putStack(slot.getStack());
                        slot.putStack(tempItemStack);
                    }
                }
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

            if (!ItemStack.areItemStackShareTagsEqual(hotbarStack, playerInvItem) || hotbarStack.getCount() != playerInvItem.getCount()) {
                minecraft.playerController.sendSlotPacket(hotbarStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + slotIndex);
            }
        }
    }

    private ItemStack incrementStackSize(ItemStack itemStack, int stackIncrement) {

        if (itemStack.getCount() + stackIncrement < 1) {
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