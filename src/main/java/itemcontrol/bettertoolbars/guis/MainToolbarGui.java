package itemcontrol.bettertoolbars.guis;

import itemcontrol.bettertoolbars.StateHandler;
import itemcontrol.bettertoolbars.ToolbarTabManager;
import itemcontrol.bettertoolbars.slots.SlotBase;
import dfutils.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.ArrayList;

@ParametersAreNonnullByDefault
public class MainToolbarGui extends GuiContainer {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    private static final ResourceLocation GUI_ICON_TEXTURE = new ResourceLocation("dfutils:textures/gui/toolbar_icons.png");
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("dfutils:textures/gui/toolbar_tab.png");
    
    private GuiTextField tabNameField;
    private boolean startOfItemDrag = false;
    private boolean ignoreMouseUp = false;
    private boolean areRowButtonsDrawn = false;
    private int rowButtonRow = 0;
    private long lastClickedDeleteTime = -5000L;

    public MainToolbarGui() {
        super(new MainToolbarContainer());
    }
    
    @Override
    public void initGui() {
        super.initGui();
    
        updateToolbarSlots();
    
        tabNameField = new GuiTextField(0, super.fontRenderer, super.guiLeft + 32, super.guiTop + 12, 112, super.fontRenderer.FONT_HEIGHT);
        tabNameField.setMaxStringLength(50);
        tabNameField.setEnableBackgroundDrawing(false);
        tabNameField.setText(ToolbarTabManager.getOriginalTabName());
        tabNameField.setCanLoseFocus(true);
    }
    
    private void updateToolbarSlots() {
        try {
            ItemStack[] tabItems = ToolbarTabManager.getTabItems();

            for (int slotIndex = 1; slotIndex < 46; slotIndex++) {
                int tabSlotIndex = (StateHandler.getScrollRow() * 9) + (slotIndex - 1);

                //If tabSlotIndex is outside the bounds of the tabItems array,
                //just put an air item into the selected toolbar slot.
                if (tabSlotIndex < tabItems.length) {
                    super.inventorySlots.getSlot(slotIndex).putStack(tabItems[tabSlotIndex]);
                } else {
                    super.inventorySlots.getSlot(slotIndex).putStack(ItemStack.EMPTY);
                }
            }

            //Sets the icon slot to the tabs icon slot item.
            super.inventorySlots.getSlot(0).putStack(ToolbarTabManager.getTabIcon());
        } catch (NullPointerException exception) {
            //Uh oh! An NPE, continue on.
        }
    }
    
    @Override
    public void updateScreen() {
        tabNameField.updateCursorCounter();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        //Makes the background tinted gray.
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    
        //Detects whether the hovered slot is the tab icon slot, if so, don't draw the item tooltip.
        if (super.getSlotUnderMouse() != null && !isIconSlot(super.getSlotUnderMouse().slotNumber)) {
            super.renderHoveredToolTip(mouseX, mouseY);
        }
    
        //Draws the insert and remove row buttons.
        if (minecraft.player.inventory.getItemStack().isEmpty()) {
            minecraft.getTextureManager().bindTexture(GUI_ICON_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderHelper.enableGUIStandardItemLighting();
            
            if (super.getSlotUnderMouse() != null && isToolbarSlot(super.getSlotUnderMouse().slotNumber) && Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
                super.drawTexturedModalRect(super.guiLeft - 3, ((super.getSlotUnderMouse().slotNumber - 1) / 9 * 18) + super.guiTop + 28, 220, 15, 12, 19);
            
                areRowButtonsDrawn = true;
                rowButtonRow = (super.getSlotUnderMouse().slotNumber - 1) / 9;
            } else if (areRowButtonsDrawn) {
                int rowButtonY = (rowButtonRow * 18) + 28 + super.guiTop;
            
                if (MathUtils.withinRange(mouseX, super.guiLeft - 3, super.guiLeft + 8) && MathUtils.withinRange(mouseY, rowButtonY, rowButtonY + 18)) {
                    if (MathUtils.withinRange(mouseX, super.guiLeft - 3, super.guiLeft + 8) && MathUtils.withinRange(mouseY, rowButtonY, rowButtonY + 9)) {
                        super.drawTexturedModalRect(super.guiLeft - 3, rowButtonY, 244, 15, 12, 19);
                        super.drawHoveringText("Remove toolbar row.", mouseX, mouseY);
                    } else {
                        super.drawTexturedModalRect(super.guiLeft - 3, rowButtonY, 232, 15, 12, 19);
                        super.drawHoveringText("Insert toolbar row.", mouseX, mouseY);
                    }
                } else {
                    areRowButtonsDrawn = false;
                }
            }
        } else {
            areRowButtonsDrawn = false;
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        
        RenderHelper.enableGUIStandardItemLighting();
        for (int tabIndex = StateHandler.selectedTabPage * 6; tabIndex < ToolbarTabManager.getTabCount() + 1 && tabIndex < (StateHandler.selectedTabPage * 6) + 6; tabIndex++) {
            if (tabIndex != StateHandler.selectedTabIndex) {
                drawTab(tabIndex);
            }
        }
        
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        super.drawTexturedModalRect(super.guiLeft, super.guiTop, 0, 0, 195, 147);
        
        //Draws the text box.
        tabNameField.drawTextBox();
    
        //Draws the currently selected tab.
        if (MathUtils.withinRange(StateHandler.selectedTabIndex, StateHandler.selectedTabPage * 6, (StateHandler.selectedTabPage * 6) + 6)) {
            drawTab(StateHandler.selectedTabIndex);
        }
    
    
        if (!StateHandler.needsScrollBar()) {
            StateHandler.scrollPosition = 0.0f;
        }
        
        //Draws scroll bar.
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_ICON_TEXTURE);
        super.drawTexturedModalRect(super.guiLeft + 175, (super.guiTop + 29) + (int) (95 * StateHandler.scrollPosition), 232 + (StateHandler.needsScrollBar() ? 0 : 12), 0, 12, 15);
    
        //Draws the delete tab button.
        if (ToolbarTabManager.isNotNewTab()) {
            if (MathUtils.withinRange(mouseX, super.guiLeft + 176, super.guiLeft + 185) && MathUtils.withinRange(mouseY, super.guiTop + 11, super.guiTop + 20) && minecraft.player.inventory.getItemStack().isEmpty()) {
                super.drawTexturedModalRect(super.guiLeft + 176, super.guiTop + 11, 247, 34, 9, 9);
                if (lastClickedDeleteTime + 5000L > System.currentTimeMillis()) {
                    ArrayList<String> hoverText = new ArrayList<>();
                    hoverText.add("§cAre you sure you want to do this?");
                    hoverText.add("§cClick again to confirm this action.");
                    super.drawHoveringText(hoverText, mouseX, mouseY);
                } else {
                    super.drawHoveringText("§cDelete Tab", mouseX, mouseY);
                }
            } else {
                super.drawTexturedModalRect(super.guiLeft + 176, super.guiTop + 11, 238, 34, 9, 9);
            }
        }
        
        //Draws the toolbar mode button.
        minecraft.getTextureManager().bindTexture(GUI_ICON_TEXTURE);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (MathUtils.withinRange(mouseX, super.guiLeft + 159, super.guiLeft + 167) && MathUtils.withinRange(mouseY, super.guiTop + 11, super.guiTop + 20) && minecraft.player.inventory.getItemStack().isEmpty()) {
            super.drawTexturedModalRect(super.guiLeft + 159, super.guiTop + 11, 247, 43, 9, 9);
        } else {
            super.drawTexturedModalRect(super.guiLeft + 159, super.guiTop + 11, 238, 43, 9, 9);
        }
    
        //Draws the toolbar tab name hover text if you are hovering over a toolbar tab.
        if (minecraft.player.inventory.getItemStack().isEmpty()) {
            for (int tabIndex = StateHandler.selectedTabPage * 6; tabIndex < ToolbarTabManager.getTabCount() + 1 && tabIndex < (StateHandler.selectedTabPage * 6) + 6; tabIndex++) {
                int tabColumn = tabIndex % 6;
                int tabX = (super.guiLeft + 28 * tabColumn) + tabColumn;
                int tabY = super.guiTop - 28;
                if (MathUtils.withinRange(mouseX, tabX, tabX + 28) && MathUtils.withinRange(mouseY, tabY, tabY + 29)) {
                    super.drawHoveringText(ToolbarTabManager.getTabName(tabIndex), mouseX, mouseY);
                }
            }
        }
        
        //Draws the settings button.
        minecraft.getTextureManager().bindTexture(GUI_ICON_TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        if (MathUtils.withinRange(mouseX, 0, 29) && MathUtils.withinRange(mouseY, 0, 29)) {
            super.drawTexturedModalRect(0, 0, 149, 128, 29, 29);
        } else {
            super.drawTexturedModalRect(0, 0, 120, 128, 29, 29);
        }
    }
    
    private void drawTab(int tabIndex) {
        int tabColumn = tabIndex % 6;
        int textureX = tabColumn * 28;
        int textureY = 0;
        int tabX = (super.guiLeft + 28 * tabColumn) + tabColumn;
        int tabY = super.guiTop - 28;
        
        //Checks if the current tab is the selected tab.
        if (tabIndex == StateHandler.selectedTabIndex) {
            textureY += 32;
        }
        
        //Checks if the current tab is a new tab.
        if (tabIndex == ToolbarTabManager.getTabCount()) {
            textureY += 64;
        }
        
        //Draws tab texture.
        minecraft.getTextureManager().bindTexture(GUI_ICON_TEXTURE);
        super.drawTexturedModalRect(tabX, tabY, textureX, textureY, 28, 32);
        
        //Draws tab icon item.
        super.itemRender.renderItemAndEffectIntoGUI(ToolbarTabManager.getTabIcon(tabIndex), tabX + 6, tabY + 9);
        super.itemRender.renderItemOverlays(super.fontRenderer, ToolbarTabManager.getTabIcon(tabIndex), tabX + 6, tabY + 9);
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
            tabNameField.setSelectionPos(tabNameField.getCursorPosition());
        }
    
        //Checks if the player clicks the delete tab button.
        if (ToolbarTabManager.isNotNewTab() && MathUtils.withinRange(mouseX, super.guiLeft + 176, super.guiLeft + 185) && MathUtils.withinRange(mouseY, super.guiTop + 11, super.guiTop + 20) && minecraft.player.inventory.getItemStack().isEmpty()) {
            minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.25F, 1.0F);
            
            if (lastClickedDeleteTime + 5000L > System.currentTimeMillis()) {
                ToolbarTabManager.deleteToolbarTab();
                switchToolbarTab(StateHandler.selectedTabIndex);
            } else {
                lastClickedDeleteTime = System.currentTimeMillis();
            }
        }
        
        //Checks if the player clicks the settings button.
        if (MathUtils.withinRange(mouseX, 0, 29) && MathUtils.withinRange(mouseY, 0, 29)) {
            minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.25F, 1.0F);
        }
    
        //Checks if the player clicks on a toolbar tab.
        for (int tabIndex = StateHandler.selectedTabPage * 6; tabIndex < ToolbarTabManager.getTabCount() + 1 && tabIndex < (StateHandler.selectedTabPage * 6) + 6; tabIndex++) {
            int tabColumn = tabIndex % 6;
            int tabX = (super.guiLeft + 28 * tabColumn) + tabColumn;
            int tabY = super.guiTop - 28;
            if (tabIndex != StateHandler.selectedTabIndex && MathUtils.withinRange(mouseX, tabX, tabX + 28) && MathUtils.withinRange(mouseY, tabY, tabY + 29)) {
                switchToolbarTab(tabIndex + (StateHandler.selectedTabPage * 6));
                ignoreMouseUp = true;
                return;
            }
        }
        
        //Checks if the player clicks one of the row buttons. (remove toolbar row or insert toolbar row)
        if (areRowButtonsDrawn) {
            int rowButtonY = (rowButtonRow * 18) + 28 + super.guiTop;
    
            if (MathUtils.withinRange(mouseX, super.guiLeft - 3, super.guiLeft + 8) && MathUtils.withinRange(mouseY, rowButtonY, rowButtonY + 18)) {
                if (MathUtils.withinRange(mouseX, super.guiLeft - 3, super.guiLeft + 8) && MathUtils.withinRange(mouseY, rowButtonY, rowButtonY + 9)) {
                    ToolbarTabManager.removeTabRow(rowButtonRow);
                } else {
                    ToolbarTabManager.insertTabRow(rowButtonRow);
                }
                
                updateToolbarSlots();
                minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.25F, 1.0F);
            }
        }
        
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (ignoreMouseUp) {
            ignoreMouseUp = false;
        } else {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        
        if (!super.checkHotbarKeys(keyCode)) {
            if (keyCode == Keyboard.KEY_RETURN) {
                tabNameField.setFocused(false);
                tabNameField.setSelectionPos(tabNameField.getCursorPosition());
            } else {
    
                String oldText = tabNameField.getText();
    
                if (!tabNameField.textboxKeyTyped(typedChar, keyCode)) {
                    super.keyTyped(typedChar, keyCode);
                }
    
                //If the given text is too long, remove the added character.
                if (minecraft.fontRenderer.getStringWidth(tabNameField.getText()) > 118) {
                    tabNameField.setText(oldText);
                }
    
                if (!tabNameField.getText().equals(oldText)) {
                    ToolbarTabManager.renameToolbarTab(tabNameField.getText());
                }
            }
        }
    }
    
    private void switchToolbarTab(int newTabIndex) {
        StateHandler.selectedTabIndex = newTabIndex;
        lastClickedDeleteTime = -5000L;
        
        updateToolbarSlots();
        tabNameField.setText(ToolbarTabManager.getOriginalTabName());
    }

    @Override
    public void onGuiClosed() {
        if (!minecraft.player.inventory.getItemStack().isEmpty()) {
            minecraft.player.inventory.setItemStack(ItemStack.EMPTY);
        }

        ToolbarTabManager.saveTabs();
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