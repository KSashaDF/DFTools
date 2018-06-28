package diamondfireutils;

import diamondfireutils.codetools.CodeData;
import diamondfireutils.codetools.misctools.LocationSetter;
import diamondfireutils.codetools.misctools.PistonHighlighting;
import diamondfireutils.codetools.commands.CommandCodeBase;
import diamondfireutils.commands.CommandHelp;
import diamondfireutils.commands.CommandTest;
import diamondfireutils.commands.itemcontrol.CommandGive;
import diamondfireutils.commands.shortcuts.ShortcutLastMsg;
import diamondfireutils.commands.shortcuts.ShortcutSupportChat;
import diamondfireutils.commands.itemcontrol.CommandBreakable;
import diamondfireutils.commands.itemcontrol.CommandUnbreakable;
import diamondfireutils.commands.itemcontrol.CommandDurability;
import diamondfireutils.commands.itemcontrol.attributes.CommandAttributeBase;
import diamondfireutils.commands.itemcontrol.lore.CommandLoreBase;
import diamondfireutils.commands.itemcontrol.rename.CommandRename;
import diamondfireutils.commands.itemcontrol.rename.CommandRenameAnvil;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class DiamondFireUtils {
    
    @Mod.Instance
    public static DiamondFireUtils instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        
        //Shortcut initialization.
        MinecraftForge.EVENT_BUS.register(new ShortcutLastMsg());
        MinecraftForge.EVENT_BUS.register(new ShortcutSupportChat());
        
        ClientCommandHandler commandHandler = ClientCommandHandler.instance;
        
        commandHandler.registerCommand(new CommandHelp());
        
        //Item control command initialization.
        commandHandler.registerCommand(new CommandGive());
        commandHandler.registerCommand(new CommandAttributeBase());
        commandHandler.registerCommand(new CommandLoreBase());
        commandHandler.registerCommand(new CommandRenameAnvil());
        commandHandler.registerCommand(new CommandRename());
        commandHandler.registerCommand(new CommandUnbreakable());
        commandHandler.registerCommand(new CommandBreakable());
        commandHandler.registerCommand(new CommandDurability());
        
        commandHandler.registerCommand(new CommandCodeBase());
        
        commandHandler.registerCommand(new CommandTest());
    
        new CodeData();
        MinecraftForge.EVENT_BUS.register(new PistonHighlighting());
        MinecraftForge.EVENT_BUS.register(new LocationSetter());
    }
}
