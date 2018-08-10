package dfutils;

import dfutils.bettertoolbars.MainToolbarGui;
import dfutils.bettertoolbars.ToolbarTabHandler;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.templateexplorer.MainExplorerGui;
import dfutils.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class InputHandler {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    private static final KeyBinding BETTER_TOOLBARS_MENU = new KeyBinding("Better Toolbars", Keyboard.KEY_GRAVE, "DiamondFire Utilities");
    private static final KeyBinding TEMPLATE_EXPLORER = new KeyBinding("Code Template Viewer", Keyboard.KEY_BACKSLASH, "DiamondFire Utilities");
    private static final KeyBinding CODE_QUICK_SELECT = new KeyBinding("Quick Codeblock Selection", Keyboard.KEY_V, "DiamondFire Utilities");

    static void initializeKeys() {

        ClientRegistry.registerKeyBinding(BETTER_TOOLBARS_MENU);
        if (DiamondFireUtils.devEnv)
            ClientRegistry.registerKeyBinding(TEMPLATE_EXPLORER);
        ClientRegistry.registerKeyBinding(CODE_QUICK_SELECT);
    }

    public static void inputHandlerKeyInput(InputEvent event) {

        //Makes sure there is no GUI currently open.
        if (minecraft.currentScreen == null) {
            if (BETTER_TOOLBARS_MENU.isPressed()) {
                if (minecraft.player.isCreative()) {
                    try {
                        ToolbarTabHandler.loadToolbarTabs();

                        minecraft.displayGuiScreen(new MainToolbarGui());
                    } catch (IOException exception) {
                        MessageUtils.errorMessage("Uh oh! Encountered an IO Exception while trying to load toolbar tab data.");
                    }
                }
            }

            if (TEMPLATE_EXPLORER.isPressed()) {
                if (minecraft.player.isCreative()) {
                    minecraft.displayGuiScreen(new MainExplorerGui());
                }
            }

            if (CODE_QUICK_SELECT.isPressed()) {
                if (minecraft.player.isCreative()) {
                    CodeQuickSelection.getSelectionItem();
                }
            }
        }
    }
}
