package dfutils.codetools.codeprinting;

import dfutils.codetools.CodeData;
import dfutils.codetools.CodeItems;
import dfutils.codetools.utils.CodeBlockUtils;
import dfutils.codetools.utils.MathUtils;
import dfutils.codetools.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
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

    private static PrintSignStage codeSignStage;
    private static NBTTagList functionPath;
    private static int functionPathPos;

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

                        printState = PrintState.SIGN;
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

                    printSubState = PrintSubState.EVENT_WAIT;
                    codeChestSlot = 0;
                    codeChestStage = PrintChestStage.CREATE_ITEM;
                }
            }
            
            if (printSubState == PrintSubState.EVENT_WAIT_FINISH) {

                if (CodeBlockUtils.stringToBlock(printNbtHandler.selectedBlock.getString("Name")).hasCodeSign) {
                    printState = PrintState.SIGN;
                    printSubState = PrintSubState.NULL;
                } else {
                    nextCodeBlock();
                    printState = PrintState.NULL;
                }
            }
        }

        if (printState == PrintState.SIGN) {
            if (printSubState == PrintSubState.NULL) {

                if (printNbtHandler.selectedBlock.hasKey("Function")) {
                    printSubState = PrintSubState.MOVEMENT_WAIT;
                    codeSignStage = PrintSignStage.FUNCTION;
                } else if (printNbtHandler.selectedBlock.hasKey("DynamicFunction")) {
                    printSubState = PrintSubState.MOVEMENT_WAIT;
                    codeSignStage = PrintSignStage.DYNAMIC_FUNCTION;
                } else if (printNbtHandler.selectedBlock.hasKey("Target")) {
                    printSubState = PrintSubState.MOVEMENT_WAIT;
                    codeSignStage = PrintSignStage.TARGET;
                } else if (printNbtHandler.selectedBlock.hasKey("ConditionalNot")) {
                    printSubState = PrintSubState.MOVEMENT_WAIT;
                    codeSignStage = PrintSignStage.CONDITIONAL_NOT;
                } else {
                    nextCodeBlock();
                    printState = PrintState.NULL;
                }
            }

            if (printSubState == PrintSubState.MOVEMENT_WAIT) {
                if (MathUtils.distance(minecraft.player.getPosition().up(), printPos.west()) < 5) {

                    if (codeSignStage == PrintSignStage.FUNCTION) {

                        if (!CodeData.codeReferenceData.getCompoundTag(printNbtHandler.selectedBlock.getString("Name")).hasKey(printNbtHandler.selectedBlock.getString("Function"))) {
                            MessageUtils.errorMessage("Unable to identify code function! Moving onto next code block.");
                            printState = PrintState.NULL;
                            nextCodeBlock();
                            return;
                        }

                        functionPathPos = 0;
                        functionPath = CodeData.codeReferenceData.getCompoundTag(printNbtHandler.selectedBlock.getString("Name")).
                                getCompoundTag(printNbtHandler.selectedBlock.getString("Function")).
                                getTagList("path", 8);

                        minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(printPos.west(), EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));
                        printSubState = PrintSubState.EVENT_WAIT;
                    }

                    if (codeSignStage == PrintSignStage.DYNAMIC_FUNCTION) {
                        if (printNbtHandler.selectedBlock.getString("Name").equals("LOOP")) {
                            try {
                                minecraft.playerController.sendSlotPacket(CodeItems.getNumberSlimeball(CommandBase.parseInt(printNbtHandler.selectedBlock.getString("DynamicFunction"))), minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
                            } catch (NumberInvalidException exception) {
                                MessageUtils.errorMessage("Hey! You edited the the NBT and made the number for the loop delay invalid. D:<");
                            }
                        } else {
                            minecraft.playerController.sendSlotPacket(CodeItems.getTextBook(printNbtHandler.selectedBlock.getString("DynamicFunction")), minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
                        }

                        minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(printPos.west(), EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));

                        printState = PrintState.NULL;
                        nextCodeBlock();
                    }

                    if (codeSignStage == PrintSignStage.TARGET) {
                        minecraft.player.connection.sendPacket(new CPacketEntityAction(minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                        minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(printPos.west(), EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));
                        minecraft.player.connection.sendPacket(new CPacketEntityAction(minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));

                        printSubState = PrintSubState.EVENT_WAIT;
                    }

                    if (codeSignStage == PrintSignStage.CONDITIONAL_NOT) {
                        minecraft.playerController.sendSlotPacket(CodeItems.getNotArrow(), minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
                        minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(printPos.west(), EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));

                        printState = PrintState.NULL;
                        nextCodeBlock();
                    }
                }
            }
        }
    }
    
    static void openedCodeChest(Container codeChest) {
        if (!printNbtHandler.getChestItem(codeChestSlot).isEmpty()) {
            switch (codeChestStage) {
                case CREATE_ITEM:
                    minecraft.playerController.sendSlotPacket(printNbtHandler.getChestItem(codeChestSlot), minecraft.player.inventoryContainer.inventorySlots.size() - 10);
                    codeChestStage = PrintChestStage.MOVE_ITEM;
                    break;
        
                case MOVE_ITEM:
    
                    //If item was correctly placed in chest, continue on to next slot.
                    if (codeChest.getSlot(codeChestSlot).getStack().isEmpty()) {
                        short actionNumber = codeChest.getNextTransactionID(minecraft.player.inventory);
                        minecraft.player.connection.sendPacket(new CPacketClickWindow(codeChest.windowId, 54, 0, ClickType.PICKUP, printNbtHandler.getChestItem(codeChestSlot), actionNumber));
                        minecraft.player.connection.sendPacket(new CPacketClickWindow(codeChest.windowId, codeChestSlot, 0, ClickType.PICKUP, printNbtHandler.getChestItem(codeChestSlot), actionNumber));
                    }
                    
                    codeChestStage = PrintChestStage.CHECK_ITEM;
                    break;
                    
                case CHECK_ITEM:
                    //If item was correctly placed in chest, continue on to next slot.
                    if (!codeChest.getSlot(codeChestSlot).getStack().isEmpty()) {
                        codeChestSlot++;
                    }
    
                    codeChestStage = PrintChestStage.CREATE_ITEM;
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

        if (!isContainerEmpty(codeGui)) {
            if (codeSignStage == PrintSignStage.FUNCTION) {
                //Tries to find the specified item within the code GUI, returns item slot number.
                int itemSlot = findContainerItem(codeGui, functionPath.getStringTagAt(functionPathPos));

                //Tests if the item actually exists within the GUI.
                if (itemSlot != -1) {
                    short actionNumber =  codeGui.getNextTransactionID(minecraft.player.inventory);
                    minecraft.player.connection.sendPacket(new CPacketClickWindow(codeGui.windowId, itemSlot, 0, ClickType.PICKUP, new ItemStack(Item.getItemById(0)), actionNumber));
                    functionPathPos++;

                    //If reached end of code function path, move onto next sign element or next code block.
                    if (functionPathPos >= functionPath.tagCount()) {
                        if (printNbtHandler.selectedBlock.hasKey("Target")) {
                            printSubState = PrintSubState.MOVEMENT_WAIT;
                            codeSignStage = PrintSignStage.TARGET;
                        } else if (printNbtHandler.selectedBlock.hasKey("ConditionalNot")) {
                            printSubState = PrintSubState.MOVEMENT_WAIT;
                            codeSignStage = PrintSignStage.CONDITIONAL_NOT;
                        } else {
                            printState = PrintState.NULL;
                            nextCodeBlock();
                        }

                        //Speeds up the process of closing the GUI screen.
                        minecraft.player.closeScreen();
                        return;
                    }
                }
            }

            if (codeSignStage == PrintSignStage.TARGET) {
                //Tries to find the specified item within the code GUI, returns item slot number.
                int itemSlot = findContainerItem(codeGui,
                        CodeData.codeReferenceData.getCompoundTag(printNbtHandler.selectedBlock.getString("Name")).
                                getCompoundTag("CodeTarget").
                                getString(printNbtHandler.selectedBlock.getString("Target")));

                //Tests if the item actually exists within the GUI.
                if (itemSlot != -1) {
                    short actionNumber =  codeGui.getNextTransactionID(minecraft.player.inventory);
                    minecraft.player.connection.sendPacket(new CPacketClickWindow(codeGui.windowId, itemSlot, 0, ClickType.PICKUP, new ItemStack(Item.getItemById(0)), actionNumber));

                    //Checks if the code block has the NOT tag, if not, continues onto the next code block.
                    if (printNbtHandler.selectedBlock.hasKey("ConditionalNot")) {
                        printSubState = PrintSubState.MOVEMENT_WAIT;
                        codeSignStage = PrintSignStage.CONDITIONAL_NOT;
                    } else {
                        printState = PrintState.NULL;
                        nextCodeBlock();
                    }

                    //Speeds up the process of closing the GUI screen.
                    minecraft.player.closeScreen();
                }
            }
        }
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

    private static boolean isContainerEmpty(Container container) {
        for (int slot = 0; slot < container.inventorySlots.size() - 36; slot++) {
            if (!container.getSlot(slot).getStack().isEmpty())
                return false;
        }

        return true;
    }

    private static int findContainerItem(Container container, String itemName) {
        for (int slot = 0; slot < container.inventorySlots.size() - 36; slot++) {
            if (container.getSlot(slot).getStack().getDisplayName().equals(itemName))
                return slot;
        }

        return -1;
    }
}
