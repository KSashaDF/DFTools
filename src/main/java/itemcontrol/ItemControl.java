package itemcontrol;

import diamondcore.DiamondCore;
import itemcontrol.commands.*;
import itemcontrol.commands.attributes.CommandAttributeBase;
import itemcontrol.commands.candestroy.CommandCanDestroyBase;
import itemcontrol.commands.canplace.CommandCanPlaceBase;
import itemcontrol.commands.enchant.CommandClearEnch;
import itemcontrol.commands.enchant.CommandDisenchant;
import itemcontrol.commands.enchant.CommandEnchant;
import itemcontrol.commands.flags.CommandHideFlags;
import itemcontrol.commands.flags.CommandSetFlags;
import itemcontrol.commands.flags.CommandShowFlags;
import itemcontrol.commands.item.CommandItem;
import itemcontrol.commands.lore.CommandLoreBase;
import itemcontrol.commands.rename.CommandRename;
import itemcontrol.commands.rename.CommandRenameAnvil;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID,
		name = Reference.NAME,
		version = Reference.VERSION,
		acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class ItemControl {
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		DiamondCore.itemControlInstalled = true;
		
		registerCommands();
		
		/*ConfigHandler.init(event.getSuggestedConfigurationFile());*/
	}
	
	private void registerCommands() {
		ClientCommandHandler commandHandler = ClientCommandHandler.instance;
		
		//Item control command initialization.
		commandHandler.registerCommand(new CommandGive());
		commandHandler.registerCommand(new CommandItemData());
		commandHandler.registerCommand(new CommandAttributeBase());
		commandHandler.registerCommand(new CommandLoreBase());
		commandHandler.registerCommand(new CommandCanDestroyBase());
		commandHandler.registerCommand(new CommandCanPlaceBase());
		commandHandler.registerCommand(new CommandRenameAnvil());
		commandHandler.registerCommand(new CommandRename());
		commandHandler.registerCommand(new CommandUnbreakable());
		commandHandler.registerCommand(new CommandBreakable());
		commandHandler.registerCommand(new CommandDurability());
		commandHandler.registerCommand(new CommandHideFlags());
		commandHandler.registerCommand(new CommandShowFlags());
		commandHandler.registerCommand(new CommandSetFlags());
		commandHandler.registerCommand(new CommandEnchant());
		commandHandler.registerCommand(new CommandDisenchant());
		commandHandler.registerCommand(new CommandClearEnch());
		
		if (DiamondCore.devEnvironment) {
			// Item download/upload
			commandHandler.registerCommand(new CommandItem());
		}
	}
}
