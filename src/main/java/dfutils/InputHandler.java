package dfutils;

import dfutils.bettertoolbars.MainToolbarGui;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber
class InputHandler {

    private Minecraft minecraft = Minecraft.getMinecraft();

    private static final KeyBinding BETTER_TOOLBARS_MENU = new KeyBinding("Better Toolbars (WIP)", Keyboard.KEY_GRAVE, "DiamondFire Utilities");
    private static final KeyBinding CODE_QUICK_SELECT = new KeyBinding("Quick Codeblock Selection", Keyboard.KEY_V, "DiamondFire Utilities");

    static void initializeKeys() {

        ClientRegistry.registerKeyBinding(BETTER_TOOLBARS_MENU);
        ClientRegistry.registerKeyBinding(CODE_QUICK_SELECT);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent event) {

        //Makes sure there is no GUI already open.
        if (minecraft.currentScreen == null) {
            if (BETTER_TOOLBARS_MENU.isPressed()) {
                if (minecraft.player.isCreative()) {
                    System.out.println("Pressed Better Toolbar key!");
                    minecraft.displayGuiScreen(new MainToolbarGui());
                } else {
                    MessageUtils.errorMessage("You need to be in creative mode to use this!");
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
