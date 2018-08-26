package dfutils.bettertoolbars;

import dfutils.bettertoolbars.slots.SlotBase;
import dfutils.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

@ParametersAreNonnullByDefault
public class MainToolbarGui extends GuiContainer {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    private static final ResourceLocation GUI_TAB_TEXTURE = new ResourceLocation("dfutils:textures/gui/toolbar_tab_icons.png");
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("dfutils:textures/gui/toolbar_tab.png");
    
    private GuiTextField tabNameField;
    private int selectedTabIndex = 0;
    private int selectedTabPage = 0;
    private float scrollPosition = 0.0f;
    private boolean startOfItemDrag = false;

    public MainToolbarGui() {
        super(new MainToolbarContainer());

        //If there are no toolbar tabs created yet, create a new one.
        if (ToolbarTabHandler.toolbarTabs == null) {
            ToolbarTabHandler.createToolbarTab();
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
    
        updateToolbarSlots();
    
        tabNameField = new GuiTextField(0, super.fontRenderer, super.guiLeft + 32, super.guiTop + 12, 112, super.fontRenderer.FONT_HEIGHT);
        tabNameField.setMaxStringLength(50);
        tabNameField.setEnableBackgroundDrawing(false);
        tabNameField.setText(ToolbarTabHandler.toolbarTabs[selectedTabIndex].tabName);
        tabNameField.setCanLoseFocus(true);
    }
    
    private void updateToolbarSlots() {
        dragSplitting = false;
        
        try {
            ItemStack[] tabItems = ToolbarTabHandler.toolbarTabs[selectedTabIndex].getTabItems();

            for (int slotIndex = 1; slotIndex < 46; slotIndex++) {
                int tabSlotIndex = (getScrollRow() * 9) + (slotIndex - 1);

                //If tabSlotIndex is outside the bounds of the tabItems array,
                //just put an air item into the selected toolbar slot.
                if (tabSlotIndex < tabItems.length) {
                    super.inventorySlots.getSlot(slotIndex).putStack(tabItems[tabSlotIndex]);
                } else {
                    super.inventorySlots.getSlot(slotIndex).putStack(ItemStack.EMPTY);
                }
            }

            //Sets the icon slot to the tabs icon slot item.
            super.inventorySlots.getSlot(0).putStack(ToolbarTabHandler.toolbarTabs[selectedTabIndex].tabIcon);
        } catch (NullPointerException exception) {
            //Uh oh! An NPE, continue on.
        }
    }
    
    @Override
    public void updateScreen() {
        tabNameField.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //Makes the background tinted gray.
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        try {
            //Detects whether the hovered slot is the tab icon slot, if so, don't draw the item tooltip.
            if (getSlotUnderMouse() == null || !isIconSlot(getSlotUnderMouse().slotNumber)) {
                renderHoveredToolTip(mouseX, mouseY);
                RenderHelper.disableStandardItemLighting();
            }
        } catch (NullPointerException exception) {
            //Uh oh! An NPE happened for some reason, continue on.
        }
    
        //Draws the delete tab button.
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TAB_TEXTURE);
        if (MathUtils.withinRange(mouseX, super.guiLeft + 176, super.guiLeft + 185) && MathUtils.withinRange(mouseY, super.guiTop + 11, super.guiTop + 20)) {
            super.drawTexturedModalRect(super.guiLeft + 176, super.guiTop + 11, 247, 34, 9, 9);
            super.drawHoveringText("§cDelete Tab", mouseX, mouseY);
        } else {
            super.drawTexturedModalRect(super.guiLeft + 176, super.guiTop + 11, 238, 34, 9, 9);
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        
        super.drawTexturedModalRect(super.guiLeft, super.guiTop, 0, 0, 195, 147);
        RenderHelper.enableGUIStandardItemLighting();
        tabNameField.drawTextBox();
        
        if (!needsScrollBar()) {
            scrollPosition = 0.0f;
        }
        
        //Draws scroll bar.
        minecraft.getTextureManager().bindTexture(GUI_TAB_TEXTURE);
        super.drawTexturedModalRect(super.guiLeft + 175, (super.guiTop + 29) + (int) (95 * scrollPosition), 232 + (needsScrollBar() ? 0 : 12), 0, 12, 15);
    }
    
    private void drawTab(int tabIndex) {
        
        boolean isSelectedTab = tabIndex == selectedTabIndex;
        ToolbarTab toolbarTab = ToolbarTabHandler.toolbarTabs[tabIndex];
        
        int tabColumn = tabIndex % 6;
        int textureX = tabColumn * 28;
        int textureY = 0;
        int tabX = (super.guiLeft + 28 * tabColumn) + tabColumn;
        int tabY = super.guiTop - 28;
        
        if (isSelectedTab) {
            textureY += 32;
        }
        
        GlStateManager.disableLighting();
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.enableBlend();
        //Draws tab texture.
        minecraft.getTextureManager().bindTexture(GUI_TAB_TEXTURE);
        super.drawTexturedModalRect(tabX, tabY, textureX, textureY, 28, 32);
        super.zLevel = 100.0F;
        super.itemRender.zLevel = 100.0F;
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        
        //Draws tab icon item.
        super.itemRender.renderItemAndEffectIntoGUI(toolbarTab.tabIcon, tabX + 6, tabY + 9);
        super.itemRender.renderItemOverlays(super.fontRenderer, toolbarTab.tabIcon, tabX + 6, tabY + 9);
        GlStateManager.disableLighting();
        super.itemRender.zLevel = 0.0F;
        super.zLevel = 0.0F;
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
        
        if (slot == null) {
            if (clickType == ClickType.PICKUP) {
                //If the slot index is -999, it means the player clicked outside the GUI.
                //The following code handles clicking outside the GUI. Normally clicking outside the GUI would
                //drop the dragged item. (here it simply deletes it)
                if (slotIndex == -999) {
                    if (mouseButton == 0) {
                        playerInventory.setItemStack(ItemStack.EMPTY);
                    } else if (mouseButton == 1) {
                        playerInventory.getItemStack().shrink(1);
                    }
                }
            }
        } else if (slot instanceof SlotBase){
            ((SlotBase) slot).onSlotClick(mouseButton, clickType);
        }

        //Syncs the players hotbar with the hotbar in the toolbar menu.
        detectAndSendHotbarChanges();
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        
        int guiLeft = super.guiLeft;
        int guiTop = super.guiTop;
        
        //Checks if the player clicks the tab name text field.
        if (MathUtils.withinRange(mouseX, guiLeft + 31, guiLeft + 151) && MathUtils.withinRange(mouseY, guiTop + 11, guiTop + 21)) {
            tabNameField.setFocused(true);
        } else {
            tabNameField.setFocused(false);
        }
        
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        
        if (!super.checkHotbarKeys(keyCode)) {
            if (!tabNameField.textboxKeyTyped(typedChar, keyCode)) {
                super.keyTyped(typedChar, keyCode);
            }
        }
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
            ItemStack hotbarStack = super.inventorySlots.getSlot(slotIndex + 46).getStack();
            ItemStack playerInvItem = minecraft.player.inventory.getStackInSlot(slotIndex);

            if (!ItemStack.areItemStackShareTagsEqual(hotbarStack, playerInvItem) || hotbarStack.getCount() != playerInvItem.getCount()) {
                minecraft.playerController.sendSlotPacket(hotbarStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + slotIndex);
            }
        }
    }
    
    private boolean needsScrollBar() {
        return ToolbarTabHandler.toolbarTabs[selectedTabIndex].getTabItems().length / 9 > 5;
    }
    
    private int getScrollRow() {
        return (int) ((ToolbarTabHandler.toolbarTabs[selectedTabIndex].getTabItems().length / 9) * scrollPosition);
    }

    private boolean isIconSlot(int slotIndex) {
        return slotIndex == 0;
    }

    private boolean isToolbarSlot(int slotIndex) {
        return !isIconSlot(slotIndex) && !isHotbarSlot(slotIndex);
    }

    private boolean isHotbarSlot(int slotIndex) {
        return slotIndex >= super.inventorySlots.inventorySlots.size() - 9 && slotIndex < super.inventorySlots.inventorySlots.size();
    }
}