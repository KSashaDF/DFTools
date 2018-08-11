package dfutils.codetools.templateexplorer;

import dfutils.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.GuiScrollingList;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class MainExplorerGui extends GuiScreen {

    private GuiButton searchButton;
    private GuiTextField searchField;
    private GuiScrollingList templateList;

    private final int SEARCH_BUTTON = 1;
    private final int SEARCH_TEXT_INPUT = 0;
    private boolean searchButtonSearching = false;
    private int searchButtonSearchingCount = 0;
    String[] templateNames = {
            "FIRST",
            "Rocket Jump",
            "Simple W/E",
            "Rocket Jump",
            "Simple W/E",
            "Simple W/E",
            "Rocket Jump",
            "Simple W/E",
            "Rocket Jump",
            "Simple W/E",
            "Rocket Jump",
            "Simple W/E",
            "Rocket Jump",
            "Simple W/E",
            "LAST"
    };
    String[] templateAuthors = {
            "FIRST",
            "K_Sasha",
            "Timeraa",
            "K_Sasha",
            "Timeraa",
            "Timeraa",
            "K_Sasha",
            "Timeraa",
            "K_Sasha",
            "Timeraa",
            "Timeraa",
            "K_Sasha",
            "Timeraa",
            "K_Sasha",
            "LAST"
    };
    String[] templateVersion = {
            "0.1.5",
            "0.2",
            "2.0",
            "1.0",
            "0.1",
            "0.2",
            "2.0",
            "1.0",
            "0.1",
            "0.2",
            "2.0",
            "1.0",
            "0.1",
            "0.2",
            "2.0"
    };
    int centerX = (width / 2) - 256 / 2;
    int centerY = (height / 2) - 256 / 2;
    private boolean scrollingNeeded = false;
    private boolean isScrolling = false;
    private int currentScroll = 0;
    int lerpedScroll = 0;


    public MainExplorerGui() {

    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (scrollingNeeded) {
            int i = Mouse.getEventDWheel();

            i = MathHelper.clamp(i, -1, 1);
            currentScroll += 2 * i;
            currentScroll = MathHelper.clamp(currentScroll, -100, 0);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (isScrolling) isScrolling = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            if (mouseX >= (width / 2) + 55 && mouseX <= (width / 2) + 60) {
                if (mouseY >= Math.abs(currentScroll) + (height / 2) - 55 && mouseY <= Math.abs(currentScroll - (height / 2) + 25)) {
                    isScrolling = true;
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (isScrolling) {
            currentScroll = mouseY * -1 + (height / 2) - 45;
            currentScroll = MathHelper.clamp(currentScroll, -100, 0);
        }


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

        drawDefaultBackground();
        drawString(fontRenderer, "Select Code template", (width / 2) - fontRenderer.getStringWidth("Select Code Template") / 2, centerY + 15, 0xFFFFFF);

        searchField.drawTextBox();

        int startX = (width / 2) - 100;
        lerpedScroll = (int) MathUtils.lerp(currentScroll * -1, 0, 100, 0, (templateNames.length - 4) * 35) * -1;

        for (int i = 0; i < templateNames.length; i++) {
            int baseDrawY = i * 35;

            if (MathUtils.withinRange(baseDrawY + 75 + lerpedScroll, 75, 200)) {
                drawString(fontRenderer, templateNames[i], startX - 5, baseDrawY + (height / 2) - 55 + lerpedScroll, 0xFFFFFF); //Draws the template name.
                drawString(fontRenderer, "By " + templateAuthors[i], startX - 5, baseDrawY + (height / 2) - 45 + lerpedScroll, 0xAAAAAA); //Draws the template author.
                drawString(fontRenderer, "V" + templateVersion[i], startX + 150 - fontRenderer.getStringWidth("V" + templateVersion[i]), baseDrawY + (height / 2) - 45 + lerpedScroll, 0xAAAAAA); //Draws the template version.

                //If this is not the last element, draw a horizontal divider line.
                if (i + 1 != templateNames.length)
                    drawHorizontalLine(startX - 5, startX + 150, baseDrawY + (height / 2) - 30 + lerpedScroll, 0xAAAAAAAA);
            }
        }

        if (templateNames.length >= 5) {
            scrollingNeeded = true;
            drawRect(startX + 155, (height / 2) - 55, startX + 160, (height / 2) + 70, 0xCCCCCCFF);
            drawRect(startX + 155, Math.abs(currentScroll) + (height / 2) - 55, startX + 160, Math.abs(currentScroll) + (height / 2) - 30, 0xFFFFFFFF);
        } else {
            scrollingNeeded = false;
            currentScroll = 0;
        }


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        searchField = new GuiTextField(SEARCH_TEXT_INPUT, fontRenderer, (width / 2) - 80 / 2 - 65, (height / 2) - 20 / 2 - 80, 150, 20);
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
        buttonList.add(searchButton = new GuiButton(SEARCH_BUTTON, (width / 2) - 70 / 2 + 90, (height / 2) - 20 / 2 - 80, 70, 20, "Search"));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case SEARCH_BUTTON:
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

    private void updateButtons() {

    }

    private void searchCodeTemplates() {
        searchButtonSearching = true;
    }
}
