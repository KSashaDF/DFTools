package dfutils.codetools.copying;

import dfutils.codesystem.objects.CodeBlockType;
import dfutils.codesystem.objects.CodeBlockGroup;
import dfutils.codetools.selection.SelectionController;
import dfutils.codetools.selection.SelectionState;
import diamondcore.utils.BlockUtils;
import dfutils.codetools.utils.CodeBlockUtils;
import dfutils.utils.CodeFormatException;
import diamondcore.utils.ItemUtils;
import diamondcore.utils.MathUtils;
import diamondcore.utils.MessageUtils;
import diamondcore.utils.chunk.ChunkCache;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.MoverType;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CopyController {
	
	static boolean isCopying = false;
	static CopyState copyState = CopyState.NULL;
	static BlockPos copyPos = null;
	static BlockPos[] copySelection = new BlockPos[2];
	private static int chestLoadTimer = 0;
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	public static void initializeCopy() {
		if (SelectionController.selectionActive && SelectionController.selectionState != SelectionState.NULL) {
			try {
				
				isCopying = true;
				CopyNbtHandler.initializeCopyData();
				copySelection = SelectionController.getSelectionEdges();
				copyPos = copySelection[0];
				
				MessageUtils.actionMessage("Starting code copy...");
				SelectionController.resetSelection();
				
			} catch (CodeFormatException exception) {
				exception.printError();
				resetCopy();
			}
			
		} else {
			MessageUtils.errorMessage("Please make a code selection first!");
		}
	}
	
	public static void resetCopy() {
		isCopying = false;
		copyState = CopyState.NULL;
		copyPos = null;
		copySelection[0] = null;
		copySelection[1] = null;
		
		CopyNbtHandler.resetCopyData();
	}
	
	public static void copyControllerTickEvent(TickEvent.ClientTickEvent event) {
		if (isCopying) {
			if (!minecraft.player.isCreative()) {
				resetCopy();
				MessageUtils.errorMessage("Cancelled copying! You are no longer in dev mode.");
				return;
			}
			
			if (copyState == CopyState.NULL) {
				
				if (!BlockUtils.isWithinRegion(copyPos, copySelection[0], copySelection[1])) {
					finishCodeCopy();
					return;
				}
				
				CodeBlockType blockName = CodeBlockUtils.getBlockName(copyPos);
				CopyNbtHandler.nextCodeBlock(blockName);
				
				if (blockName.hasSign) {
					
					String[] signText = BlockUtils.getSignText(copyPos.west(), PlayerStateHandler.devSpaceCache);
					
					if (blockName == CodeBlockType.FUNCTION || blockName == CodeBlockType.CALL_FUNCTION || blockName == CodeBlockType.LOOP) {
						if (signText[1] != null) {
							CopyNbtHandler.setDynamicCodeFunction(signText[1]);
						}
					} else {
						if (signText[1] != null) {
							CopyNbtHandler.setCodeFunction(signText[1]);
						}
						
						if (blockName == CodeBlockType.SELECT_OBJECT || blockName == CodeBlockType.REPEAT) {
							if (signText[2] != null) {
								CopyNbtHandler.setCodeSubFunction(signText[2]);
							}
							
							if (signText[3] != null && signText[3].equals("NOT")) {
								CopyNbtHandler.setConditionalNot();
							}
						}
					}
					
					if (blockName.blockGroup == CodeBlockGroup.CONDITIONAL) {
						if (signText[3] != null && signText[3].equals("NOT")) {
							CopyNbtHandler.setConditionalNot();
						}
					}
					
					if (blockName == CodeBlockType.PLAYER_ACTION || blockName == CodeBlockType.IF_PLAYER || blockName == CodeBlockType.ENTITY_ACTION) {
						if (signText[2] != null) {
							CopyNbtHandler.setCodeTarget(signText[2]);
						}
					}
					
					// Automatic movement to next code block
					minecraft.player.move(MoverType.SELF, 0, 0, 2);
				} else {
					// Automatic movement to next code block
					minecraft.player.move(MoverType.SELF, 0, 0, 5);
				}
				
				if (blockName.hasChest) {
					copyState = CopyState.MOVEMENT_WAIT;
				} else {
					if (CodeBlockUtils.getBlockName(copyPos).hasBrackets) {
						CopyNbtHandler.addCodeScope();
					}
					getNextBlock();
				}
			}
			
			if (copyState == CopyState.MOVEMENT_WAIT) {
				if (MathUtils.distance(minecraft.player.getPosition(), copyPos) < 5) {
					minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(copyPos.up(), EnumFacing.EAST, EnumHand.MAIN_HAND, 0, 0, 0));
					copyState = CopyState.OPEN_CHEST_WAIT;
					chestLoadTimer = 5;
				}
			}
			
			if (copyState == CopyState.TICK_WAIT) {
				copyState = CopyState.NULL;
			}
		}
	}
	
	private static void getNextBlock() {
		ChunkCache devSpaceCache = PlayerStateHandler.devSpaceCache;
		
		do {
			copyPos = copyPos.south();
			if (!BlockUtils.isWithinRegion(copyPos, copySelection[0], copySelection[1]))
				return;
			if (BlockUtils.getName(copyPos, devSpaceCache).equals("minecraft:piston") || BlockUtils.getName(copyPos, devSpaceCache).equals("minecraft:sticky_piston")) {
				if (BlockUtils.getFacing(copyPos, devSpaceCache) == EnumFacing.NORTH) {
					CopyNbtHandler.exitCodeScope();
				}
			}
		} while (CodeBlockUtils.isInvalidCore(copyPos));
	}
	
	private static void finishCodeCopy() {
		//Creates code template item.
		ItemStack itemStack = ItemUtils.getItem("minecraft:ender_chest", 1, 0);
		itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setTag("CodeData", CopyNbtHandler.copyData);
		itemStack.getTagCompound().setTag("display", new NBTTagCompound());
		
		//The following code sets the code template item name.
		CodeBlockType codeLineHeader = CodeBlockUtils.stringToBlock(CopyNbtHandler.copyData.getCompoundTagAt(0).getString("Name"));
		
		if (codeLineHeader.blockGroup == CodeBlockGroup.EVENT &&
				(CopyNbtHandler.copyData.getCompoundTagAt(0).hasKey("Function") || CopyNbtHandler.copyData.getCompoundTagAt(0).hasKey("DynamicFunction"))) {
			
			if (codeLineHeader == CodeBlockType.PLAYER_EVENT || codeLineHeader == CodeBlockType.ENTITY_EVENT) {
				itemStack.getTagCompound().getCompoundTag("display").
						setTag("Name", new NBTTagString("§3§l[ §bEvent §3| §b" + CopyNbtHandler.copyData.getCompoundTagAt(0).getString("Function") + " §3§l]"));
			} else if (codeLineHeader == CodeBlockType.LOOP) {
				itemStack.getTagCompound().getCompoundTag("display").
						setTag("Name", new NBTTagString("§3§l[ §bEvent §3| §bLoop §3§l]"));
			} else {
				itemStack.getTagCompound().getCompoundTag("display").
						setTag("Name", new NBTTagString("§3§l{ §bFunction §3| §b" + CopyNbtHandler.copyData.getCompoundTagAt(0).getString("DynamicFunction") + " §3§l}"));
			}
			
		} else {
			itemStack.getTagCompound().getCompoundTag("display").
					setTag("Name", new NBTTagString("§3§l( §bCode Template §3§l)"));
		}
		
		//The following sets the lore for the code template item.
		NBTTagList itemLore = new NBTTagList();
		itemStack.getSubCompound("display").setTag("Lore", itemLore);
		
		itemLore.appendTag(new NBTTagString("§8§m                          "));
		itemLore.appendTag(new NBTTagString("§7Copied By: §c" + minecraft.player.getName()));
		itemLore.appendTag(new NBTTagString(""));
		itemLore.appendTag(new NBTTagString("§c§nNote§c: §7Place this block down"));
		itemLore.appendTag(new NBTTagString("§7to print the copied code."));
		itemLore.appendTag(new NBTTagString("§7You can also take this block"));
		itemLore.appendTag(new NBTTagString("§7to another node or even"));
		itemLore.appendTag(new NBTTagString("§7give it to another player and"));
		itemLore.appendTag(new NBTTagString("§7it will still work correctly."));
		itemLore.appendTag(new NBTTagString("§8§m                          "));
		
		//Sends updated item to the server.
		ItemUtils.setItemInHotbar(itemStack, true);
		
		minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
		MessageUtils.infoMessage("Finished code copying! Place the template to paste the copied code.");
		MessageUtils.noteMessage("Code data is stored on the template item itself!");
		resetCopy();
	}
	
	static void writeChestData(Container chestItems) {
		if (copyState == CopyState.OPEN_CHEST_WAIT) {
			
			if (isContainerEmpty(chestItems)) {
				if (chestLoadTimer > 0) {
					chestLoadTimer--;
					return;
				}
			} else {
				CopyNbtHandler.setChestItems(chestItems);
			}
			
			minecraft.player.closeScreen();
			
			if (CodeBlockUtils.getBlockName(copyPos).hasBrackets)
				CopyNbtHandler.addCodeScope();
			
			copyState = CopyState.TICK_WAIT;
			getNextBlock();
		}
	}
	
	private static boolean isContainerEmpty(Container container) {
		for (int slot = 0; slot < 27; slot++) {
			if (!container.getSlot(slot).getStack().isEmpty())
				return false;
		}
		
		return true;
	}
}
