package dfutils;

import dfutils.codehandler.utils.CodeBlockData;
import dfutils.commands.codetools.CommandNumberRange;
import dfutils.commands.codetools.CommandTextItem;
import dfutils.commands.codetools.CommandVarItem;
import dfutils.commands.codetools.locations.CommandLocBase;
import dfutils.commands.codetools.code.CommandCodeBase;
import dfutils.commands.CommandHelp;
import dfutils.commands.CommandTest;
import dfutils.commands.internal.CommandClipboard;
import dfutils.commands.itemcontrol.*;
import dfutils.commands.itemcontrol.candestroy.CommandCanDestroyBase;
import dfutils.commands.itemcontrol.canplace.CommandCanPlaceBase;
import dfutils.commands.itemcontrol.enchant.CommandClearEnch;
import dfutils.commands.itemcontrol.enchant.CommandDisenchant;
import dfutils.commands.itemcontrol.enchant.CommandEnchant;
import dfutils.commands.itemcontrol.flags.CommandHideFlags;
import dfutils.commands.itemcontrol.flags.CommandSetFlags;
import dfutils.commands.itemcontrol.flags.CommandShowFlags;
import dfutils.commands.itemcontrol.attributes.CommandAttributeBase;
import dfutils.commands.itemcontrol.lore.CommandLoreBase;
import dfutils.commands.itemcontrol.rename.CommandRename;
import dfutils.commands.itemcontrol.rename.CommandRenameAnvil;
import dfutils.config.ConfigHandler;
import dfutils.events.*;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
        updateJSON = "https://df.pocketclass.net/dfutils_update_log.json", guiFactory = Reference.GUI_FACTORY)
public class DiamondFireUtils {
    
    @Mod.Instance
    public static DiamondFireUtils instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        registerCommands();
        registerEvents();
        initializeData();

        ConfigHandler.init(event.getSuggestedConfigurationFile());
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

        //Code tools command initialization.
        commandHandler.registerCommand(new CommandCodeBase());
        commandHandler.registerCommand(new CommandLocBase());
        commandHandler.registerCommand(new CommandNumberRange());
        commandHandler.registerCommand(new CommandTextItem());
        commandHandler.registerCommand(new CommandVarItem());


        //Misc commands.
        commandHandler.registerCommand(new CommandHelp());
        commandHandler.registerCommand(new CommandTest());

        //Internal commands.
        commandHandler.registerCommand(new CommandClipboard());
    }

    private void registerEvents() {

        //Registers all the event classes.
        MinecraftForge.EVENT_BUS.register(new ClientChatEvent());
        MinecraftForge.EVENT_BUS.register(new GuiContainerEvent());
        MinecraftForge.EVENT_BUS.register(new InputEvent());
        MinecraftForge.EVENT_BUS.register(new LeftClickEmpty());
        MinecraftForge.EVENT_BUS.register(new ClientTickEvent());
        MinecraftForge.EVENT_BUS.register(new RenderWorldLastEvent());
        MinecraftForge.EVENT_BUS.register(new RightClickBlockEvent());

        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    }

    private void initializeData() {
        new CodeBlockData();
        InputHandler.initializeKeys();
    }
}
