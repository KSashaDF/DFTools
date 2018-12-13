package dfutils;

import dfutils.codetools.utils.CodeBlockData;
import dfutils.commands.CommandHelp;
import dfutils.commands.codetools.CommandNumberRange;
import dfutils.commands.codetools.CommandRejoin;
import dfutils.commands.codetools.CommandTextItem;
import dfutils.commands.codetools.CommandVarItem;
import dfutils.commands.codetools.code.CommandCodeBase;
import dfutils.commands.codetools.locations.CommandLocBase;
import dfutils.config.ConfigHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID,
		name = Reference.NAME,
		version = Reference.VERSION,
		acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
		guiFactory = Reference.GUI_FACTORY)
public class DiamondFireUtils {
	
	@SuppressWarnings("unused")
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		registerCommands();
		initializeData();
		
		ConfigHandler.init(event.getSuggestedConfigurationFile());
	}
	
	private void registerCommands() {
		ClientCommandHandler commandHandler = ClientCommandHandler.instance;
		
		// Code tools command initialization.
		commandHandler.registerCommand(new CommandCodeBase());
		commandHandler.registerCommand(new CommandLocBase());
		commandHandler.registerCommand(new CommandNumberRange());
		commandHandler.registerCommand(new CommandTextItem());
		commandHandler.registerCommand(new CommandVarItem());
		commandHandler.registerCommand(new CommandRejoin());
		
		// Misc commands.
		commandHandler.registerCommand(new CommandHelp());
	}
	
	private void initializeData() {
		new CodeBlockData();
		InputHandler.initializeKeys();
	}
}
