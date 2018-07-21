package dfutils.codetools.templateexplorer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;

import java.io.IOException;

public class MainExplorerGui extends GuiScreen {

    private GuiButton searchButton;
    private GuiTextField searchField;
    private GuiScrollingList templateList;


    private final int SEARCHBUTTON = 1;
    private final int SEARCHTEXTINPUT = 0;
    private boolean searchButtonSearching = false;
    private int searchButtonSearchingCount = 0;


    public MainExplorerGui() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(searchButtonSearching) {
            searchButton.enabled = false;
            searchField.setEnabled(false);
            if(searchButtonSearchingCount < 20) {
                searchButton.displayString = "Searching";
            } else if(searchButtonSearchingCount > 20 && searchButtonSearchingCount < 50) {
                searchButton.displayString = "Searching.";
            } else if(searchButtonSearchingCount > 50 && searchButtonSearchingCount < 80) {
                searchButton.displayString = "Searching..";
            } else if(searchButtonSearchingCount > 80 && searchButtonSearchingCount < 110) {
                searchButton.displayString = "Searching...";
            } else if(searchButtonSearchingCount > 110) {
                searchButtonSearchingCount = 0;
            }
            searchButtonSearchingCount++;
        } else {
            searchButton.enabled = true;
            searchField.setEnabled(true);
        }

        int centerX = (width / 2) - 256 / 2;
        int centerY = (height / 2) - 256 / 2;

        drawDefaultBackground();
        drawString(fontRenderer, "Select Code Template", (width / 2) - fontRenderer.getStringWidth("Select Code Template") / 2, centerY + 15, 0xFFFFFF);

        searchField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        searchField = new GuiTextField(SEARCHTEXTINPUT, fontRenderer, (width / 2) - 80 / 2 - 65, (height / 2) - 20 / 2 - 80, 150, 20);
        searchField.setMaxStringLength(25);
        searchField.setFocused(true);

        templateList = new GuiScrollingList(Minecraft.getMinecraft(), width, height, 0, 0, 0, 24, width, height) {
            String[] strings = new String[]{"one", "two", "three"};

            @Override
            protected int getSize() {
                return 5;
            }

            @Override
            protected void elementClicked(int index, boolean doubleClick) {

            }

            @Override
            protected boolean isSelected(int index) {
                return false;
            }

            @Override
            protected void drawBackground() {

            }

            @Override
            protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
                drawString(fontRenderer, "hi", width / 3, height, 0xFFFFFF);
            }
        };

        buttonList.clear();
        buttonList.add(searchButton = new GuiButton(SEARCHBUTTON, (width / 2) - 70 / 2 + 90, (height / 2) - 20 / 2 - 80, 70, 20, "Search"));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case SEARCHBUTTON:
                searchCodeTemplates();
                break;
        }
        updateButtons();
        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if(searchField.isFocused()) {
            searchField.textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public void updateButtons() {

    }

    private void searchCodeTemplates() {
        searchButtonSearching = true;
    }
}
