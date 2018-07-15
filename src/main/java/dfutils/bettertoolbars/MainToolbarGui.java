package dfutils.bettertoolbars;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MainToolbarGui extends GuiContainer {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("dfutils:textures/gui/toolbar_tab.png");

    private static Minecraft minecraft = Minecraft.getMinecraft();

    public MainToolbarGui() {
        super(minecraft.player.inventoryContainer);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }


}