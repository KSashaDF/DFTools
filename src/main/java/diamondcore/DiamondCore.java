package diamondcore;

import diamondcore.commands.CommandTest;
import diamondcore.commands.internal.CommandClipboard;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID,
		name = Reference.NAME,
		version = Reference.VERSION,
		acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class DiamondCore {
	
	public static boolean devEnvironment = false;
	
	@SuppressWarnings("unused")
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		// Checks if the mod is being run in a development environment.
		devEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
		
		registerCommands();
	}
	
	private void registerCommands() {
		ClientCommandHandler commandHandler = ClientCommandHandler.instance;
		
		if (DiamondCore.devEnvironment) {
			commandHandler.registerCommand(new CommandTest());
		}
		
		// Internal commands.
		commandHandler.registerCommand(new CommandClipboard());
	}
}
