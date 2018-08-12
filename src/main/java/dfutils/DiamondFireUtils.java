package dfutils;

import dfutils.codehandler.utils.CodeBlockData;
import dfutils.colorcodes.FontRendererOverride;
import dfutils.commands.CommandHelp;
import dfutils.commands.CommandTest;
import dfutils.commands.codetools.CommandNumberRange;
import dfutils.commands.codetools.CommandRejoin;
import dfutils.commands.codetools.CommandTextItem;
import dfutils.commands.codetools.CommandVarItem;
import dfutils.commands.codetools.code.CommandCodeBase;
import dfutils.commands.codetools.locations.CommandLocBase;
import dfutils.commands.internal.CommandClipboard;
import dfutils.commands.itemcontrol.*;
import dfutils.commands.itemcontrol.attributes.CommandAttributeBase;
import dfutils.commands.itemcontrol.candestroy.CommandCanDestroyBase;
import dfutils.commands.itemcontrol.canplace.CommandCanPlaceBase;
import dfutils.commands.itemcontrol.enchant.CommandClearEnch;
import dfutils.commands.itemcontrol.enchant.CommandDisenchant;
import dfutils.commands.itemcontrol.enchant.CommandEnchant;
import dfutils.commands.itemcontrol.flags.CommandHideFlags;
import dfutils.commands.itemcontrol.flags.CommandSetFlags;
import dfutils.commands.itemcontrol.flags.CommandShowFlags;
import dfutils.commands.itemcontrol.item.CommandItem;
import dfutils.commands.itemcontrol.lore.CommandLoreBase;
import dfutils.commands.itemcontrol.rename.CommandRename;
import dfutils.commands.itemcontrol.rename.CommandRenameAnvil;
import dfutils.config.ConfigHandler;
import dfutils.eventhandler.*;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;

@Mod(modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS,
        updateJSON = "https://raw.githubusercontent.com/MCDiamondFire/DiamondFireUtilities/master/changelog.json",
        guiFactory = Reference.GUI_FACTORY)
public class DiamondFireUtils {
    
    public static boolean devEnv = false;
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Check if in developer Environment
        devEnv = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

        registerCommands();
        registerEvents();
        initializeData();

        ConfigHandler.init(event.getSuggestedConfigurationFile());
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    
        //Overrides the default font renderer.
        FontRendererOverride fontRendererOverride = new FontRendererOverride(minecraft.gameSettings, new ResourceLocation("textures/font/ascii.png"), minecraft.getTextureManager(), false);
    
        if (minecraft.gameSettings.language != null) {
            fontRendererOverride.setUnicodeFlag(minecraft.isUnicode());
            fontRendererOverride.setBidiFlag(minecraft.getLanguageManager().isCurrentLanguageBidirectional());
        }
    
        minecraft.fontRenderer = fontRendererOverride;
    }

    private void registerCommands() {
        final ClientCommandHandler commandHandler = ClientCommandHandler.instance;

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
        commandHandler.registerCommand(new CommandRejoin());


        //Misc commands.
        commandHandler.registerCommand(new CommandHelp());

        if (devEnv) {
            commandHandler.registerCommand(new CommandTest());
            // Item download/upload
            commandHandler.registerCommand(new CommandItem());
        }

        //Internal commands.
        commandHandler.registerCommand(new CommandClipboard());
    }

    private void registerEvents() {
        final EventBus eventBus = MinecraftForge.EVENT_BUS;

        //Registers all the event classes.
        eventBus.register(new PlayerJoinEvent());
        eventBus.register(new PlayerLeaveEvent());
        eventBus.register(new ClientChatEvent());
        eventBus.register(new ChatReceivedEvent());
        eventBus.register(new GuiContainerEvent());
        eventBus.register(new InputEvent());
        eventBus.register(new LeftClickEmpty());
        eventBus.register(new ClientTickEvent());
        eventBus.register(new RenderWorldLastEvent());

        eventBus.register(new ConfigHandler());
    }

    private void initializeData() {
        new CodeBlockData();
        InputHandler.initializeKeys();
    }
}
