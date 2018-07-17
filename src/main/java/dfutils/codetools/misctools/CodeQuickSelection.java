package dfutils.codetools.misctools;

import dfutils.codetools.CodeData;
import dfutils.codetools.CodeItems;
import dfutils.codetools.utils.CodeBlockName;
import dfutils.codetools.utils.CodeBlockType;
import dfutils.codetools.printing.PrintSignStage;
import dfutils.codetools.utils.BlockUtils;
import dfutils.codetools.utils.CodeBlockUtils;
import dfutils.utils.ItemUtils;
import dfutils.utils.MathUtils;
import dfutils.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CodeQuickSelection {

    private static boolean isPrintingSign = false;
    private static boolean resetItem;
    private static boolean clickedSign = false;
    //Used for preventing the next opened GUI after the sign is printed to be auto-closed.
    //(and next opened GUI being a GUI that should not actually be closed)
    private static int signPrintTime;
    private static PrintSignStage signStage;
    private static boolean eventWait;
    private static NBTTagCompound signData;
    private static BlockPos signPos;
    private static ItemStack oldHeldItem;

    private static NBTTagList functionPath;
    private static int functionPathPos;

    private static Minecraft minecraft = Minecraft.getMinecraft();

    public static void getSelectionItem() {
        if (minecraft.player.isCreative()) {
            if (CodeBlockUtils.isCodeBlock(minecraft.objectMouseOver.getBlockPos())) {

                BlockPos blockPos = CodeBlockUtils.getBlockCore(minecraft.objectMouseOver.getBlockPos());
                CodeBlockName blockName = CodeBlockUtils.getBlockName(blockPos);
                NBTTagCompound signData = new NBTTagCompound();
                signData.setString("Name", blockName.name());

                if (blockName.hasCodeSign) {
                    String[] signText = BlockUtils.getSignText(blockPos.west());

                    if (blockName == CodeBlockName.FUNCTION || blockName == CodeBlockName.CALL_FUNCTION || blockName == CodeBlockName.LOOP) {
                        if (signText[1] != null) {
                            signData.setString("DynamicFunction", signText[1]);
                        }
                    } else {
                        if (signText[1] != null) {
                            signData.setString("Function", signText[1]);
                        }

                        if (blockName == CodeBlockName.SELECT_OBJECT || blockName == CodeBlockName.REPEAT) {
                            if (signText[2] != null) {
                                signData.setString("SubFunction", signText[2]);
                            }

                            if (signText[3] != null && signText[3].equals("NOT")) {
                                signData.setByte("ConditionalNot", (byte) 1);
                            }
                        }
                    }

                    if (blockName.codeBlockType == CodeBlockType.CONDITIONAL) {
                        if (signText[3] != null && signText[3].equals("NOT")) {
                            signData.setByte("ConditionalNot", (byte) 1);
                        }
                    }

                    if (blockName == CodeBlockName.PLAYER_ACTION || blockName == CodeBlockName.IF_PLAYER || blockName == CodeBlockName.ENTITY_ACTION) {
                        if (signText[2] != null) {
                            signData.setString("Target", signText[2]);
                        }
                    }

                    ItemStack itemStack = new ItemStack(Item.getItemById(421), 1, 0);
                    itemStack.setTagCompound(new NBTTagCompound());
                    itemStack.getTagCompound().setTag("SelectionData", signData);
                    itemStack.getTagCompound().setTag("display", new NBTTagCompound());

                    if (signData.hasKey("Function")) {
                        itemStack.getSubCompound("display").setTag("Name", new NBTTagString("§2§l[ §aCode Sign §2| §a" + signData.getString("Function") + " §2§l]"));
                    } else if (signData.hasKey("DynamicFunction")) {
                        itemStack.getSubCompound("display").setTag("Name", new NBTTagString("§2§l[ §aCode Sign §2| §a" + signData.getString("DynamicFunction") + " §2§l]"));
                    } else {
                        itemStack.getSubCompound("display").setTag("Name", new NBTTagString("§2§l[ §aCode Sign §2§l]"));
                    }

                    ItemUtils.setItemInHotbar(itemStack, true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (minecraft.player.isCreative() && CodeBlockUtils.isCodeBlock(minecraft.objectMouseOver.getBlockPos()) && !BlockUtils.getName(minecraft.objectMouseOver.getBlockPos()).equals("Chest")) {
            ItemStack itemStack = minecraft.player.getHeldItemMainhand();

            if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("SelectionData")) {
                if (itemStack.getSubCompound("SelectionData").getString("Name").equals(CodeBlockUtils.getBlockName(CodeBlockUtils.getBlockCore(minecraft.objectMouseOver.getBlockPos())).name())) {

                    isPrintingSign = true;
                    resetItem = false;
                    eventWait = false;
                    signData = itemStack.getSubCompound("SelectionData");
                    signPos = CodeBlockUtils.getBlockCore(minecraft.objectMouseOver.getBlockPos()).west();
                    oldHeldItem = itemStack;

                    //If player has clicked the sign, don't click it again.
                    clickedSign = minecraft.objectMouseOver.getBlockPos().equals(signPos) || minecraft.objectMouseOver.getBlockPos().equals(signPos.east());
                    signPrintTime = minecraft.player.ticksExisted;

                    if (signData.hasKey("Function")) {
                        signStage = PrintSignStage.FUNCTION;
                    } else if (signData.hasKey("DynamicFunction")) {
                        signStage = PrintSignStage.DYNAMIC_FUNCTION;
                    } else if (signData.hasKey("Target")) {
                        signStage = PrintSignStage.TARGET;
                    } else if (signData.hasKey("ConditionalNot")) {
                        signStage = PrintSignStage.CONDITIONAL_NOT;
                    } else {
                        isPrintingSign = false;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {

        if (isPrintingSign) {
            if (!eventWait) {

                //If the player is too far away from the code block, cancel the sign selection.
                if (MathUtils.distance(minecraft.player.getPosition().up(), signPos) >= 5) {
                    isPrintingSign = false;
                    return;
                }

                if (signStage == PrintSignStage.FUNCTION) {

                    //Tests if code function exists within code reference data.
                    if (!CodeData.codeReferenceData.getCompoundTag(signData.getString("Name")).hasKey(signData.getString("Function"))) {
                        MessageUtils.errorMessage("Unable to identify code function! Moving onto next code block.");
                        isPrintingSign = false;
                        return;
                    }

                    functionPathPos = 0;

                    if (signData.hasKey("SubFunction")) {
                        functionPath = CodeData.codeReferenceData.getCompoundTag(signData.getString("Name")).
                                getCompoundTag(signData.getString("Function")).
                                getCompoundTag(signData.getString("SubFunction")).
                                getTagList("path", 8);
                    } else {
                        functionPath = CodeData.codeReferenceData.getCompoundTag(signData.getString("Name")).
                                getCompoundTag(signData.getString("Function")).
                                getTagList("path", 8);
                    }

                    if (!clickedSign)
                        minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(signPos, EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));
                    eventWait = true;
                }

                if (signStage == PrintSignStage.DYNAMIC_FUNCTION) {
                    if (signData.getString("Name").equals("LOOP")) {
                        try {
                            minecraft.playerController.sendSlotPacket(CodeItems.getNumberSlimeball(CommandBase.parseInt(signData.getString("DynamicFunction")), 1), minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
                        } catch (NumberInvalidException exception) {
                            MessageUtils.errorMessage("Hey! You edited the the NBT and made the number for the loop delay invalid. D:<");
                        }
                    } else {
                        minecraft.playerController.sendSlotPacket(CodeItems.getTextBook(signData.getString("DynamicFunction"), 1), minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
                    }

                    minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(signPos, EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));

                    isPrintingSign = false;
                    resetItem = true;
                    return;
                }

                if (signStage == PrintSignStage.TARGET) {
                    minecraft.player.connection.sendPacket(new CPacketEntityAction(minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                    minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(signPos, EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));
                    minecraft.player.connection.sendPacket(new CPacketEntityAction(minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));

                    eventWait = true;
                }

                if (signStage == PrintSignStage.CONDITIONAL_NOT) {

                    //If the code block already has the NOT tag, don't set it again!
                    if (BlockUtils.getSignText(signPos)[3] == null) {
                        minecraft.playerController.sendSlotPacket(CodeItems.getNotArrow(), minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
                        minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(signPos, EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));
                    }

                    isPrintingSign = false;
                    resetItem = true;
                }
            }
        } else {

            //Sets the player's mainhand item back to what it was when they started printing the sign.
            if (resetItem) {
                resetItem = false;
                minecraft.playerController.sendSlotPacket(oldHeldItem, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerContainerEvent(GuiContainerEvent event) {
        if (isPrintingSign) {
            if (eventWait) {
                Container codeGui = event.getGuiContainer().inventorySlots;

                if (!isContainerEmpty(codeGui)) {
                    if (signStage == PrintSignStage.FUNCTION) {
                        //Tries to find the specified item within the code GUI, returns item slot number.
                        int itemSlot = findContainerItem(codeGui, functionPath.getStringTagAt(functionPathPos));

                        //Tests if the item actually exists within the GUI.
                        if (itemSlot != -1) {
                            short actionNumber = codeGui.getNextTransactionID(minecraft.player.inventory);
                            minecraft.player.connection.sendPacket(new CPacketClickWindow(codeGui.windowId, itemSlot, 0, ClickType.PICKUP, new ItemStack(Item.getItemById(0)), actionNumber));
                            functionPathPos++;

                            //If reached end of code function path, move onto next sign element or next code block.
                            if (functionPathPos >= functionPath.tagCount()) {
                                if (signData.hasKey("Target")) {
                                    eventWait = false;
                                    signStage = PrintSignStage.TARGET;
                                } else if (signData.hasKey("ConditionalNot")) {
                                    eventWait = false;
                                    signStage = PrintSignStage.CONDITIONAL_NOT;
                                } else {
                                    isPrintingSign = false;
                                }

                                //Speeds up the process of closing the GUI screen.
                                minecraft.player.closeScreen();
                                return;
                            }
                        }
                    }

                    if (signStage == PrintSignStage.TARGET) {
                        //Tries to find the specified item within the code GUI, returns item slot number.
                        int itemSlot = findContainerItem(codeGui,
                                CodeData.codeReferenceData.getCompoundTag(signData.getString("Name")).
                                        getCompoundTag("CodeTarget").
                                        getString(signData.getString("Target")));

                        //Tests if the item actually exists within the GUI.
                        if (itemSlot != -1) {
                            short actionNumber = codeGui.getNextTransactionID(minecraft.player.inventory);
                            minecraft.player.connection.sendPacket(new CPacketClickWindow(codeGui.windowId, itemSlot, 0, ClickType.PICKUP, new ItemStack(Item.getItemById(0)), actionNumber));

                            //Checks if the code block has the NOT tag, if not, continues onto the next code block.
                            if (signData.hasKey("ConditionalNot")) {
                                eventWait = false;
                                signStage = PrintSignStage.CONDITIONAL_NOT;
                            } else {
                                isPrintingSign = false;
                            }

                            //Speeds up the process of closing the GUI screen.
                            minecraft.player.closeScreen();
                        }
                    }
                }
            }
        } else {

            //If the player has clicked a code sign, close the GUI.
            if (clickedSign) {
                if (minecraft.player.ticksExisted < signPrintTime + 10) {
                    clickedSign = false;
                    minecraft.player.closeScreen();
                }
            }
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
