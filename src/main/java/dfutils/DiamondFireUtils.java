package dfutils;

import dfutils.codetools.codehandler.utils.CodeBlockData;
import dfutils.colorcodes.FontRendererOverride;
import dfutils.commands.CommandHelp;
import dfutils.commands.codetools.CommandNumberRange;
import dfutils.commands.codetools.CommandRejoin;
import dfutils.commands.codetools.CommandTextItem;
import dfutils.commands.codetools.CommandVarItem;
import dfutils.commands.codetools.code.CommandCodeBase;
import dfutils.commands.codetools.locations.CommandLocBase;
import dfutils.config.ConfigHandler;
import diamondcore.DiamondCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID,
		name = Reference.NAME,
		version = Reference.VERSION,
		acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
		updateJSON = Reference.UPDATE_URL,
		guiFactory = Reference.GUI_FACTORY)
public class DiamondFireUtils {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	@SuppressWarnings("unused")
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		DiamondCore.dfUtilsInstalled = true;
		
		registerCommands();
		initializeData();
		
		ConfigHandler.init(event.getSuggestedConfigurationFile());
	}
	
	@SuppressWarnings("unused")
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		//Overrides the default font renderer.
		FontRendererOverride fontRendererOverride = new FontRendererOverride(minecraft.gameSettings, new ResourceLocation("textures/font/ascii.png"), minecraft.getTextureManager(), false);
		
		if (minecraft.gameSettings.language != null) {
			fontRendererOverride.setUnicodeFlag(minecraft.isUnicode());
			fontRendererOverride.setBidiFlag(minecraft.getLanguageManager().isCurrentLanguageBidirectional());
		}
		
		minecraft.fontRenderer = fontRendererOverride;
		((SimpleReloadableResourceManager) minecraft.getResourceManager()).registerReloadListener(fontRendererOverride);
	}
	
	private void registerCommands() {
		ClientCommandHandler commandHandler = ClientCommandHandler.instance;
		
		//Code tools command initialization.
		commandHandler.registerCommand(new CommandCodeBase());
		commandHandler.registerCommand(new CommandLocBase());
		commandHandler.registerCommand(new CommandNumberRange());
		commandHandler.registerCommand(new CommandTextItem());
		commandHandler.registerCommand(new CommandVarItem());
		commandHandler.registerCommand(new CommandRejoin());
		
		//Misc commands.
		commandHandler.registerCommand(new CommandHelp());
	}
	
	private void initializeData() {
		new CodeBlockData();
		InputHandler.initializeKeys();
	}
}
