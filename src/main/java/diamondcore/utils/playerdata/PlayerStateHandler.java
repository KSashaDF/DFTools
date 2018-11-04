package diamondcore.utils.playerdata;

import diamondcore.eventhandler.customevents.ClickItemEvent;
import diamondcore.eventhandler.customevents.DiamondFireEvent;
import diamondcore.utils.*;
import diamondcore.utils.chunk.ChunkCache;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlayerStateHandler {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	private static final BlockPos DEV_SPAWN_OFFSET = new BlockPos(10, -3, -10);
	
	public static boolean isOnDiamondFire = false;
	
	public static int plotId;
	public static String plotName;
	public static String plotOwner;
	public static BlockPos plotCorner;
	
	public static PlotSize plotSize;
	private static boolean findPlotSize = false;
	public static ChunkCache devSpaceCache;
	
	public static PlayerMode playerMode;
	
	
	private static int nextPlotJoinId = 0;
	private static String nextPlotJoinName;
	private static String nextPlotJoinOwner;
	
	private static boolean waitForCreative = false;
	private static DiamondFireEvent nextEvent;
	
	
	public static boolean isInSupportSession = false;
	public static String supportPartner;
	public static SupportSessionRole supportSessionRole;
	
	//Used for the DFUtils Discord RPC.
	public static boolean discordRPCForceReload = false;
	
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
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.ChangeModeEvent(PlayerMode.DEV));
		}
		
		if (messageRawText.equals("You are now in build mode.")) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.ChangeModeEvent(PlayerMode.BUILD));
		}
		
		if (messageRawText.startsWith("You have been kicked from ")) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.LeavePlotEvent());
		}
		
		if (messageRawText.startsWith("You have been banned from ")) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.LeavePlotEvent());
		}
		
		if (messageRawText.startsWith("Joined game: ")) {
			String[] messageWords = TextUtils.splitString(messageRawText);
			
			if (messageWords.length >= 5 && messageWords[messageWords.length - 2].equals("by")) {
				DiamondFireEvent.JoinPlotEvent joinPlotEvent = new
						DiamondFireEvent.JoinPlotEvent(nextPlotJoinId, TextUtils.buildString(messageWords, 2, messageWords.length - 3), messageWords[messageWords.length - 1], PlayerMode.PLAY);
				
				MinecraftForge.EVENT_BUS.post(joinPlotEvent);
			}
		}
		
		
		if (messageRawText.startsWith("Your plot has now been renamed to: ")) {
			String[] messageWords = TextUtils.splitString(messageRawText);
			plotName = TextUtils.buildString(messageWords, 7);
			
			discordRPCForceReload = true;
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.RenamePlotEvent());
		}
		
		
		if (messageRawText.startsWith("You have entered a support session with player ")) {
			String[] messageWords = TextUtils.splitString(messageRawText);
			nextEvent = new DiamondFireEvent.EnterSessionEvent(messageWords[messageWords.length - 1], SupportSessionRole.SUPPORTER);
			waitForCreative = true;
		}
		
		if (messageRawText.startsWith("You have entered a support session! Your helper is ")) {
			String[] messageWords = TextUtils.splitString(messageRawText);
			nextEvent = new DiamondFireEvent.EnterSessionEvent(messageWords[messageWords.length - 1], SupportSessionRole.SUPPORTEE);
			waitForCreative = true;
		}
		
		if (messageRawText.startsWith("Support session ended! (")) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.ExitSessionEvent());
		}
		
		if (messageRawText.equals("Support session terminated.")) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.ExitSessionEvent());
		}
		
		if (messageRawText.equals("Uh oh! Your helper disconnected. They may have experienced a connection issue.")) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.ExitSessionEvent());
		}
		
		if (messageRawText.equals("Your support session was terminated by the support staff.")) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.ExitSessionEvent());
		}
	}
	
	public static void playerStateHandlerChatSent(ClientChatEvent event) {
		String messageText = event.getMessage();
		
		if (messageText.equals("/spawn") || messageText.equals("/leave")) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.LeavePlotEvent());
		}
		
		if (messageText.startsWith("/join ") && playerMode == PlayerMode.SPAWN) {
			try {
				nextPlotJoinId = CommandBase.parseInt(TextUtils.splitString(messageText)[1]);
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
				nextPlotJoinName = TextUtils.stripColorCodes(event.clickedItem.getDisplayName());
				
				NBTTagList itemLore = event.clickedItem.getOrCreateSubCompound("display").getTagList("Lore", 8);
				
				//Gets the line of item lore that contains the plot owner.
				nextPlotJoinOwner = itemLore.getStringTagAt(0).replace("§7By ", "");
				
				//Gets the line of item lore that contains the plot ID.
				String plotIdLore = itemLore.getStringTagAt(1).replace("§8ID:", "");
				nextPlotJoinId = Integer.parseInt(plotIdLore);
				
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
		
		if (waitForCreative && minecraft.player.isCreative()) {
			MinecraftForge.EVENT_BUS.post(nextEvent);
			waitForCreative = false;
		}
		
		//If the player is not in creative mode, but is in build or dev mode, it means the player has probably left the plot.
		if (playerMode.isCreative && !minecraft.player.isCreative()) {
			MinecraftForge.EVENT_BUS.post(new DiamondFireEvent.LeavePlotEvent());
		}
		
		if (findPlotSize) {
			if (plotCorner != null) {
				BlockPos basicPlotCheck = MathUtils.incrementPosition(plotCorner, new BlockPos(-1, 0, PlotSize.BASIC.size));
				BlockPos largePlotCheck = MathUtils.incrementPosition(plotCorner, new BlockPos(-1, 0, PlotSize.LARGE.size));
				
				if (minecraft.world.isBlockLoaded(basicPlotCheck, false)) {
					if (BlockUtils.getName(basicPlotCheck).equals("minecraft:stone")) {
						plotSize = PlotSize.BASIC;
						devSpaceCache = new ChunkCache(plotCorner.getX() - 21, plotCorner.getZ(), plotCorner.getX() - 1, plotCorner.getZ() + 50);
						findPlotSize = false;
					} else if (minecraft.world.isBlockLoaded(largePlotCheck, false)) {
						
						if (BlockUtils.getName(largePlotCheck).equals("minecraft:stone")) {
							plotSize = PlotSize.LARGE;
							devSpaceCache = new ChunkCache(plotCorner.getX() - 21, plotCorner.getZ(), plotCorner.getX() - 1, plotCorner.getZ() + 100);
							findPlotSize = false;
						} else {
							plotSize = PlotSize.MASSIVE;
							devSpaceCache = new ChunkCache(plotCorner.getX() - 21, plotCorner.getZ(), plotCorner.getX() - 1, plotCorner.getZ() + 300);
							findPlotSize = false;
						}
					}
				}
			} else {
				findPlotSize = false;
			}
		}
	}
	
	public static void diamondFireEventHandler(DiamondFireEvent event) {
		
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
			supportPartner = ((DiamondFireEvent.EnterSessionEvent) event).supportPartner;
			supportSessionRole = ((DiamondFireEvent.EnterSessionEvent) event).supportSessionRole;
			
			playerMode = PlayerMode.DEV;
		}
		
		if (event instanceof DiamondFireEvent.ExitSessionEvent) {
			isInSupportSession = false;
		}
		
		if (playerMode == PlayerMode.DEV && devSpaceCache != null) {
			devSpaceCache.unregister();
			devSpaceCache = null;
		}
	}
}

