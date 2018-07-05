package dfutils.codetools.codeprinting;

import dfutils.codetools.CodeItems;
import dfutils.codetools.utils.CodeBlockUtils;
import dfutils.codetools.utils.MathUtils;
import dfutils.codetools.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

class PrintController {
    
    static boolean isPrinting = false;
    static PrintState printState;
    static PrintSubState printSubState;
    static BlockPos printPos;
    static BlockPos[] printSelection = new BlockPos[2];
    static PrintNbtHandler printNbtHandler = new PrintNbtHandler();
    
    private static int actionWaitTicks = 0;
    
    private static int codeChestSlot;
    private static PrintChestStage codeChestStage;
    private static short actionNumber;
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    
    //TODO - Finish print init and add proper checks
    static void initializePrint(NBTTagList codeData) {
        isPrinting = true;
        printState = PrintState.NULL;
        printSubState = PrintSubState.NULL;
        printPos = minecraft.objectMouseOver.getBlockPos().up();
        
        printSelection[0] = printPos;
        printSelection[1] = printPos.south(getCodeLength(codeData));
        
        printNbtHandler.initializePrintData(codeData);
    }
    
    static void resetPrint() {
        isPrinting = false;
    }
    
    private static void finishPrint() {
        isPrinting = false;
        minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    
        MessageUtils.infoMessage("Finished printing code.");
    }
    
    //The main update method for the entire print system.
    static void updatePrint() {
        
        if (printSubState == PrintSubState.ACTION_WAIT) {
            if (actionWaitTicks > 0) {
                actionWaitTicks--;
            } else {
                printSubState = PrintSubState.ACTION_WAIT_FINISH;
            }
        }
        
        if (printState == PrintState.NULL) {
            printState = PrintState.CORE;
            printSubState = PrintSubState.NULL;
        }
        
        if (printState == PrintState.CORE) {
            
            if (printSubState == PrintSubState.NULL) {
                printSubState = PrintSubState.MOVEMENT_WAIT;
            }
            
            if (printSubState == PrintSubState.MOVEMENT_WAIT) {
                if (MathUtils.distance(minecraft.player.getPosition().up(), printPos.down()) < 5) {
                    minecraft.playerController.sendSlotPacket(CodeItems.getCodeBlock(printNbtHandler.selectedBlock.getString("Name")), minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
                    minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(printPos.down(), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                    
                    printSubState = PrintSubState.ACTION_WAIT;
                    actionWaitTicks = 3;
                }
            }
            
            if (printSubState == PrintSubState.ACTION_WAIT_FINISH) {
                if (printNbtHandler.selectedBlock.hasKey("ChestItems")) {
                    printState = PrintState.CHEST;
                    printSubState = PrintSubState.NULL;
                } else {
                    if (CodeBlockUtils.stringToBlock(printNbtHandler.selectedBlock.getString("Name")).hasCodeSign) {
                        
                        //NOTE - Change the printState to PrintState.SIGN
                        printState = PrintState.NULL;
                        printSubState = PrintSubState.NULL;
                    } else {
                        nextCodeBlock();
                        printState = PrintState.NULL;
                    }
                }
            }
        }
        
        if (printState == PrintState.CHEST) {
            if (printSubState == PrintSubState.NULL) {
                printSubState = PrintSubState.MOVEMENT_WAIT;
            }
    
            if (printSubState == PrintSubState.MOVEMENT_WAIT) {
                if (MathUtils.distance(minecraft.player.getPosition(), printPos) < 5) {
                    minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(printPos.up(), EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));
                    
                    printSubState = PrintSubState.ACTION_WAIT;
                    actionWaitTicks = 5;
                }
            }
            
            if (printSubState == PrintSubState.ACTION_WAIT_FINISH) {
                printSubState = PrintSubState.EVENT_WAIT;
                codeChestSlot = 0;
                codeChestStage = PrintChestStage.CREATE_ITEM;
            }
            
            if (printSubState == PrintSubState.EVENT_WAIT_FINISH) {
    
                nextCodeBlock();
                printState = PrintState.NULL;
                
                /*
                if (CodeBlockUtils.stringToBlock(printNbtHandler.selectedBlock.getString("Name")).hasCodeSign) {
                    printState = PrintState.SIGN;
                    printSubState = PrintSubState.NULL;
                } else {
                    nextCodeBlock();
                    printState = PrintState.NULL;
                }
                */
            }
        }
    }
    
    static void openedCodeChest(Container codeChest) {
        if (!printNbtHandler.getChestItem(codeChestSlot).isEmpty()) {
            switch (codeChestStage) {
                case NULL:
                    codeChestStage = PrintChestStage.CREATE_ITEM;
                    break;
                
                case CREATE_ITEM:
                    minecraft.playerController.sendSlotPacket(printNbtHandler.getChestItem(codeChestSlot), minecraft.player.inventoryContainer.inventorySlots.size() - 10);
                    codeChestStage = PrintChestStage.PICKUP_ITEM;
                    break;
        
                case PICKUP_ITEM:
                    actionNumber = codeChest.getNextTransactionID(minecraft.player.inventory);
                    minecraft.player.connection.sendPacket(new CPacketClickWindow(codeChest.windowId, 54, 0, ClickType.PICKUP, printNbtHandler.getChestItem(codeChestSlot), actionNumber));
                    codeChestStage = PrintChestStage.PLACE_ITEM;
                    break;
        
                case PLACE_ITEM:
                    System.out.println(codeChestSlot);
                    minecraft.player.connection.sendPacket(new CPacketClickWindow(codeChest.windowId, codeChestSlot, 0, ClickType.PICKUP, printNbtHandler.getChestItem(codeChestSlot), actionNumber));
                    codeChestStage = PrintChestStage.CREATE_ITEM;
                    codeChestSlot++;
            }
        } else {
            codeChestSlot++;
        }
        
        if (codeChestSlot > 27) {
            printSubState = PrintSubState.EVENT_WAIT_FINISH;
            minecraft.player.closeScreen();
        }
        
        if (printNbtHandler.isCodeChestEmpty(codeChestSlot)) {
            printSubState = PrintSubState.EVENT_WAIT_FINISH;
            minecraft.player.closeScreen();
        }
    }
    
    static void openedCodeGui(Container codeGui) {
    
    }
    
    static int getCodeLength(NBTTagList codeData) {
        
        if (codeData.tagCount() == 0)
            return 0;
        
        PrintNbtHandler codeLengthNbt = new PrintNbtHandler();
        codeLengthNbt.initializePrintData(codeData);
        
        int codeLength = 0;
        
        do {
            if (codeLengthNbt.selectedBlock.hasKey("CodeData")) {
                codeLength += 4;
            } else {
                codeLength += 2;
            }
    
            //In case there are multiple closing pistons grouped together, the next code block finder is looped.
            do {
                codeLengthNbt.nextCodeBlock();
            } while (!codeLengthNbt.selectedBlock.hasKey("Name") && !codeLengthNbt.reachedCodeEnd);
            
        } while (!codeLengthNbt.reachedCodeEnd);
        
        return codeLength;
    }
    
    private static void nextCodeBlock() {
        //Checks if the selected code block is before a closing piston, if so, skip ahead 2 blocks.
        if (printNbtHandler.shouldExitScope())
            printPos = printPos.south(2);
    
        //Checks if code brackets are empty, if so, skip ahead 2 blocks.
        if (printNbtHandler.selectedBlock.hasKey("CodeData") && printNbtHandler.selectedBlock.getTagList("CodeData", 10).tagCount() == 0)
            printPos = printPos.south(2);
    
        //In case there are multiple closing pistons grouped together, the next code block finder is looped.
        do {
            //Gets the next code block and goes forward 2 blocks.
            printNbtHandler.nextCodeBlock();
            printPos = printPos.south(2);
        } while (!printNbtHandler.selectedBlock.hasKey("Name") && !printNbtHandler.reachedCodeEnd);
        
        if (printNbtHandler.reachedCodeEnd) {
            finishPrint();
        }
    }
    
    private static boolean isContainerEmpty(Container container, int startPos) {
        for (int slot = startPos; slot < minecraft.player.inventoryContainer.inventorySlots.size() - 36; slot++) {
            if (!container.getSlot(slot).getStack().isEmpty())
                return false;
        }
        
        return true;
    }
}
