package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.colorcodes.GuiChatOverride;
import dfutils.config.ConfigHandler;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class GuiOpenEvent {
	
	private static final Field defaultChatTextField = ReflectionHelper.findField(GuiChat.class, "field_146409_v", "defaultInputFieldText");
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onGuiOpen(net.minecraftforge.client.event.GuiOpenEvent event) {
		
		//Overrides the default chat GUI with a subclassed chat GUI only if the player is on DiamondFire and is in creative mode.
		if (event.getGui() instanceof GuiChat && PlayerStateHandler.isOnDiamondFire && Minecraft.getMinecraft().player.isCreative() && ConfigHandler.DO_CUSTOM_CHAT) {
			try {
				event.setGui(new GuiChatOverride((String) defaultChatTextField.get(event.getGui())));
			} catch (IllegalAccessException ignored) {
				//Impossible condition under normal circumstances!
			}
		}
	}
}
