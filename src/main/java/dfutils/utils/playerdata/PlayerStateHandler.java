package dfutils.utils.playerdata;

import dfutils.commands.CommandUtils;
import dfutils.customevents.ClickItemEvent;
import dfutils.customevents.DiamondFireEvent;
import dfutils.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlayerStateHandler {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final BlockPos DEV_SPAWN_OFFSET = new BlockPos(10, -3, -10);
    public static final String DIAMONDFIRE_IP = "158.69.123.235:25565";
    
    public static boolean isOnDiamondFire = false;
    
    public static int plotId;
    public static String plotName;
    public static String plotOwner;
    public static BlockPos plotCorner;
    
    public static PlotSize plotSize;
    private static boolean findPlotSize = false;

    public static PlayerMode playerMode;
    
    
    private static int nextPlotJoinId = 0;
    private static String nextPlotJoinName;
    private static String nextPlotJoinOwner;
    
    private static boolean waitForCreative = false;
    private static DiamondFireEvent nextEvent;
    
    
    public static boolean isInSupportSession = false;
    public static String supportPartner;
    public static SupportSessionRole supportSessionRole;

    public static void playerStateHandlerJoinEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        isOnDiamondFire = true;
        playerMode = PlayerMode.SPAWN;
        findPlotSize = false;
        waitForCreative = false;
        isInSupportSession = false;
    }
    
    public static void playerStateHandlerChatReceived(ClientChatReceivedEvent event) {
        String messageRawText = event.getMessage().getUnformattedText();
        
        if (messageRawText.equals("You are now in dev mode.")) {
            diamondFireEventHandler(new DiamondFireEvent.ChangeModeEvent(PlayerMode.DEV));
        }
    
        if (messageRawText.equals("You are now in build mode.")) {
            diamondFireEventHandler(new DiamondFireEvent.ChangeModeEvent(PlayerMode.BUILD));
        }
        
        if (messageRawText.startsWith("You have been kicked from ")) {
            diamondFireEventHandler(new DiamondFireEvent.LeavePlotEvent());
        }
    
        if (messageRawText.startsWith("You have been banned from ")) {
            diamondFireEventHandler(new DiamondFireEvent.LeavePlotEvent());
        }
        
        if (messageRawText.startsWith("Joined game: ")) {
            String[] messageWords = MiscUtils.splitString(messageRawText);
            
            if (messageWords.length >= 5 && messageWords[messageWords.length - 2].equals("by")) {
                DiamondFireEvent.JoinPlotEvent joinPlotEvent = new
                        DiamondFireEvent.JoinPlotEvent(nextPlotJoinId, MiscUtils.buildString(messageWords, 2, messageWords.length - 3), messageWords[messageWords.length - 1], PlayerMode.PLAY);
                
                diamondFireEventHandler(joinPlotEvent);
            }
        }
        
        
        if (messageRawText.startsWith("You have entered a support session with player ")) {
            diamondFireEventHandler(new DiamondFireEvent.EnterSessionEvent());
        }
        
        if (messageRawText.startsWith("You have entered a support session! Your helper is ")) {
            diamondFireEventHandler(new DiamondFireEvent.EnterSessionEvent());
        }
        
        if (messageRawText.startsWith("Support session ended! (")) {
            diamondFireEventHandler(new DiamondFireEvent.ExitSessionEvent());
        }
    
        if (messageRawText.equals("Uh oh! Your helper disconnected. They may have experienced a connection issue.")) {
            diamondFireEventHandler(new DiamondFireEvent.ExitSessionEvent());
        }
        
        if (messageRawText.equals("Your support session was terminated by the support staff.")) {
            diamondFireEventHandler(new DiamondFireEvent.ExitSessionEvent());
        }
    }
    
    public static void playerStateHandlerChatSent(ClientChatEvent event) {
        String messageText = event.getMessage();
        
        if (messageText.equals("/spawn") || messageText.equals("/leave")) {
            diamondFireEventHandler(new DiamondFireEvent.LeavePlotEvent());
        }
        
        if (messageText.startsWith("/join ") && playerMode == PlayerMode.SPAWN) {
            try {
                nextPlotJoinId = CommandBase.parseInt(MiscUtils.splitString(messageText)[1]);
            } catch (NumberInvalidException exception) {
                //Invalid number argument! Continue on, the server will output the error message.
            }
        }
    }
    
    public static void playerStateHandlerClickItemEvent(ClickItemEvent event) {
        
        //Checks if the player has clicked a plot icon item.
        if (playerMode == PlayerMode.SPAWN) {
            if (event.clickedItem.hasTagCompound() && event.clickedItem.getTagCompound().hasKey("HideFlags") && event.clickedItem.getTagCompound().getInteger("HideFlags") == 35) {
    
                //Gets the name of the plot, also removes all color codes from the item name.
                nextPlotJoinName = CommandUtils.clearColorCodes(event.clickedItem.getDisplayName());
                
                NBTTagList itemLore = event.clickedItem.getOrCreateSubCompound("display").getTagList("Lore", 8);
                
                //Gets the line of item lore that contains the plot owner.
                nextPlotJoinOwner = itemLore.getStringTagAt(0).replace("§7By ", "");
                
                //Gets the line of item lore that contains the plot ID.
                String plotIdLore = itemLore.getStringTagAt(1);
                plotIdLore = plotIdLore.replace("§8ID:", "");
                
                try {
                    nextPlotJoinId = CommandBase.parseInt(plotIdLore);
                } catch (NumberInvalidException exception) {
                    //Hmm... that should not happen, continue on I guess.
                }
            } else if (ItemUtils.getName(event.clickedItem.getItem()).equals("minecraft:anvil") && event.clickedItem.getDisplayName().equals("§e§lBuild")) {
    
                nextEvent = new DiamondFireEvent.JoinPlotEvent(nextPlotJoinId, nextPlotJoinName, nextPlotJoinOwner, PlayerMode.BUILD);
                waitForCreative = true;
                
            } else if (ItemUtils.getName(event.clickedItem.getItem()).equals("minecraft:command_block") && event.clickedItem.getDisplayName().equals("§b§lCode")) {
    
                nextEvent = new DiamondFireEvent.JoinPlotEvent(nextPlotJoinId, nextPlotJoinName, nextPlotJoinOwner, PlayerMode.DEV);
                waitForCreative = true;
    
            }
        }
    }
    
    public static void playerStateHandlerTickEvent(TickEvent.ClientTickEvent event) {
        
        //If the player is not in creative mode, but is in build or dev mode, it means the player has probably left the plot.
        if (playerMode.isCreative && !minecraft.player.isCreative()) {
            diamondFireEventHandler(new DiamondFireEvent.LeavePlotEvent());
        }
        
        if (waitForCreative && minecraft.player.isCreative()) {
            diamondFireEventHandler(nextEvent);
            waitForCreative = false;
        }
        
        if (findPlotSize) {
            if (plotCorner != null) {
                BlockPos basicPlotCheck = MathUtils.incrementPosition(plotCorner, new BlockPos(-1, 0, PlotSize.BASIC.size));
                BlockPos largePlotCheck = MathUtils.incrementPosition(plotCorner, new BlockPos(-1, 0, PlotSize.LARGE.size));
    
                if (minecraft.world.isBlockLoaded(basicPlotCheck)) {
                    if (BlockUtils.getName(basicPlotCheck).equals("minecraft:stone")) {
                        plotSize = PlotSize.BASIC;
                        findPlotSize = false;
                    } else if (minecraft.world.isBlockLoaded(largePlotCheck)) {
                        if (BlockUtils.getName(largePlotCheck).equals("minecraft:stone")) {
                            plotSize = PlotSize.LARGE;
                            findPlotSize = false;
                        } else {
                            plotSize = PlotSize.MASSIVE;
                            findPlotSize = false;
                        }
                    }
                }
            } else {
                findPlotSize = false;
            }
        }
    }
    
    static void diamondFireEventHandler(DiamondFireEvent event) {
        
        if (event instanceof DiamondFireEvent.JoinPlotEvent) {
            plotId = ((DiamondFireEvent.JoinPlotEvent) event).plotId;
            plotName = ((DiamondFireEvent.JoinPlotEvent) event).plotName;
            plotOwner = ((DiamondFireEvent.JoinPlotEvent) event).plotOwner;
            playerMode = ((DiamondFireEvent.JoinPlotEvent) event).playerMode;
            
            if (playerMode == PlayerMode.DEV) {
                plotCorner = MathUtils.incrementPosition(minecraft.player.getPosition(), DEV_SPAWN_OFFSET);
                findPlotSize = true;
            }
        }
        
        if (event instanceof DiamondFireEvent.LeavePlotEvent) {
            playerMode = PlayerMode.SPAWN;
            
            findPlotSize = false;
            waitForCreative = false;
        }
        
        if (event instanceof DiamondFireEvent.ChangeModeEvent) {
            playerMode = ((DiamondFireEvent.ChangeModeEvent) event).playerMode;
            
            if (playerMode == PlayerMode.DEV) {
                plotCorner = MathUtils.incrementPosition(minecraft.player.getPosition(), DEV_SPAWN_OFFSET);
                findPlotSize = true;
            }
        }
        
        if (event instanceof DiamondFireEvent.EnterSessionEvent) {
            isInSupportSession = true;
        }
        
        if (event instanceof DiamondFireEvent.ExitSessionEvent) {
            isInSupportSession = false;
        }
    }
}
