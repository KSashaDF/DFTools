package dfutils.codetools.codecopying;

import dfutils.codetools.classification.CodeBlockName;
import dfutils.codetools.classification.CodeBlockType;
import dfutils.codetools.selection.SelectionController;
import dfutils.codetools.selection.SelectionState;
import dfutils.codetools.utils.BlockUtils;
import dfutils.codetools.utils.CodeBlockUtils;
import dfutils.codetools.utils.CodeFormatException;
import dfutils.codetools.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.codetools.utils.MessageUtils.actionMessage;
import static dfutils.codetools.utils.MessageUtils.errorMessage;
import static dfutils.codetools.utils.MessageUtils.infoMessage;

@Mod.EventBusSubscriber
public class CopyController {
    
    static boolean isCopying = false;
    static CopyState copyState = CopyState.NULL;
    static BlockPos copyPos = null;
    static BlockPos[] copySelection = new BlockPos[2];
    private static int chestLoadTimer = 0;
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    public static void initializeCopy() {
        if (SelectionController.selectionActive && SelectionController.selectionState != SelectionState.NULL) {
            try {
                
                isCopying = true;
                CopyNbtHandler.initializeCopyData();
                copySelection = SelectionController.getSelectionEdges();
                copyPos = copySelection[0];
                
                actionMessage("Starting code copy.");
                SelectionController.resetSelection();
                
            } catch (CodeFormatException exception) {
                exception.printError();
                resetCopy();
            }
            
        } else {
            errorMessage("Please make a code selection first!");
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
    
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (isCopying) {
            if (!minecraft.player.isCreative()) {
                resetCopy();
                errorMessage("Cancelled copying! You are no longer in creative mode.");
                return;
            }
            
            if (copyState == CopyState.NULL) {
    
                if (!BlockUtils.isWithinRegion(copyPos, copySelection[0], copySelection[1])) {
                    finishCodeCopy();
                    return;
                }
                
                CodeBlockName blockName = CodeBlockUtils.getBlockName(copyPos);
                CopyNbtHandler.nextCodeBlock(blockName);
                
                if (blockName.hasCodeSign) {
                    String[] signText = BlockUtils.getSignText(copyPos.west());
                    
                    if (blockName == CodeBlockName.FUNCTION || blockName == CodeBlockName.CALL_FUNCTION || blockName == CodeBlockName.LOOP) {
                        if (signText[1] != null) {
                            CopyNbtHandler.setDynamicCodeFunction(signText[1]);
                        }
                    } else {
                        if (signText[1] != null) {
                            CopyNbtHandler.setCodeFunction(signText[1]);
                        }
                        
                        if (blockName == CodeBlockName.SELECT_OBJECT) {
                            if (signText[2] != null) {
                                CopyNbtHandler.setCodeSubFunction(signText[2]);
                            }
                            
                            if (signText[3] != null && signText[3].equals("NOT")) {
                                CopyNbtHandler.setConditionalNot();
                            }
                        }
                    }
    
                    if (blockName.codeBlockType == CodeBlockType.CONDITIONAL) {
                        if (signText[3] != null && signText[3].equals("NOT")) {
                            CopyNbtHandler.setConditionalNot();
                        }
                    }
    
                    if (blockName == CodeBlockName.PLAYER_ACTION || blockName == CodeBlockName.IF_PLAYER || blockName == CodeBlockName.ENTITY_ACTION) {
                        if (signText[2] != null) {
                            CopyNbtHandler.setCodeTarget(signText[2]);
                        }
                    }
                }
                
                if (blockName.hasCodeChest) {
                    copyState = CopyState.MOVEMENT_WAIT;
                } else {
                    if (CodeBlockUtils.getBlockName(copyPos).hasPistonBrackets)
                        CopyNbtHandler.addCodeScope();
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
        do {
            copyPos = copyPos.south();
            
            if (!BlockUtils.isWithinRegion(copyPos, copySelection[0], copySelection[1]))
                return;
            
            if (BlockUtils.getName(copyPos).equals("Piston") || BlockUtils.getName(copyPos).equals("Sticky Piston")) {
                if (BlockUtils.getFacing(copyPos) == EnumFacing.NORTH) {
                    CopyNbtHandler.exitCodeScope();
                }
            }
            
        } while (!CodeBlockUtils.isValidCore(copyPos));
    }
    
    private static void finishCodeCopy() {
        ItemStack itemStack = new ItemStack(Item.getItemById(130), 1, 0);
        itemStack.setTagCompound(new NBTTagCompound());
        itemStack.getTagCompound().setTag("CodeData", new NBTTagList());
        itemStack.getTagCompound().setTag("CodeData", CopyNbtHandler.copyData);
    
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
    
        minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        infoMessage("Finished code copying! Place the template item down to paste the copied code. Note that the code data is stored on the template item itself.");
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
            
            if (CodeBlockUtils.getBlockName(copyPos).hasPistonBrackets)
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
