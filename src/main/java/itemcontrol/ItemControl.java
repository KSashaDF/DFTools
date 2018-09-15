package itemcontrol;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class ItemControl {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    
        /*registerCommands();
        registerEvents();
        initializeData();
    
        ConfigHandler.init(event.getSuggestedConfigurationFile());*/
    }
}
