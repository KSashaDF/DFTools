package dfutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumHand;

public class EventUtils {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    //Simply prevents the left click even from being fired.
    //NOTE: This can only be called on the InputEvent!
    public static void cancelLeftClick(boolean doSwingAnimation) {
        KeyBinding keyBinding = minecraft.gameSettings.keyBindAttack;

        if (minecraft.gameSettings.keyBindAttack.isKeyDown()) {
            if (doSwingAnimation) {
                minecraft.player.swingArm(EnumHand.MAIN_HAND);
            }

            KeyBinding.setKeyBindState(keyBinding.getKeyCode(), false);

            //Each time isPressed() is called, the press time is decreased by 1.
            //(this is simply to reset the press time)
            while (keyBinding.isPressed()) {}
        }
    }

    //Simply prevents the right click even from being fired.
    //NOTE: This can only be called on the InputEvent!
    public static void cancelRightClick() {
        KeyBinding keyBinding = minecraft.gameSettings.keyBindUseItem;

        if (keyBinding.isKeyDown()) {
            KeyBinding.setKeyBindState(minecraft.gameSettings.keyBindUseItem.getKeyCode(), false);

            //Each time isPressed() is called, the press time is decreased by 1.
            //(this is simply to reset the press time)
            while (keyBinding.isPressed()) {}
        }
    }
}
