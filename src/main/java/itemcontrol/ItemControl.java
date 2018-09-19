package itemcontrol;

import diamondcore.DiamondCore;
import itemcontrol.commands.itemcontrol.*;
import itemcontrol.commands.itemcontrol.attributes.CommandAttributeBase;
import itemcontrol.commands.itemcontrol.candestroy.CommandCanDestroyBase;
import itemcontrol.commands.itemcontrol.canplace.CommandCanPlaceBase;
import itemcontrol.commands.itemcontrol.enchant.CommandClearEnch;
import itemcontrol.commands.itemcontrol.enchant.CommandDisenchant;
import itemcontrol.commands.itemcontrol.enchant.CommandEnchant;
import itemcontrol.commands.itemcontrol.flags.CommandHideFlags;
import itemcontrol.commands.itemcontrol.flags.CommandSetFlags;
import itemcontrol.commands.itemcontrol.flags.CommandShowFlags;
import itemcontrol.commands.itemcontrol.item.CommandItem;
import itemcontrol.commands.itemcontrol.lore.CommandLoreBase;
import itemcontrol.commands.itemcontrol.rename.CommandRename;
import itemcontrol.commands.itemcontrol.rename.CommandRenameAnvil;
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
