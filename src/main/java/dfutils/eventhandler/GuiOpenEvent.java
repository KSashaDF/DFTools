package dfutils.eventhandler;

import dfutils.colorcodes.GuiChatOverride;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class GuiOpenEvent {
    
    private static final Field defaultChatTextField = ReflectionHelper.findField(GuiChat.class, "field_146409_v", "defaultInputFieldText");
    
    @SubscribeEvent
    public void onGuiOpen(net.minecraftforge.client.event.GuiOpenEvent event) {
        
        if (event.getGui() instanceof GuiChat) {
            
            //Overrides the default chat GUI with the subclassed chat GUI.
            try {
                event.setGui(new GuiChatOverride((String) defaultChatTextField.get(event.getGui())));
            } catch (IllegalAccessException exception) {
                //Impossible condition under normal circumstances!
            }
        }
    }
}
