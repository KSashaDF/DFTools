package dfutils.codetools.misctools;

import diamondcore.utils.ColorReference;
import diamondcore.utils.GraphicsUtils;
import diamondcore.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class LocationHighlighting {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    static int locationMessageSwitch = 0;
    static boolean isHoldingLocation = false;

    public static void locationHighlightingRenderWorldLast(RenderWorldLastEvent event) {
        boolean isHoldingLocationOffHand = false;
        if (minecraft.player.isCreative()) {
            try {

                ItemStack mainHandItem = minecraft.player.getHeldItemMainhand();
                ItemStack offHandItem = minecraft.player.getHeldItemOffhand();
                if (!mainHandItem.isEmpty() &&
                        mainHandItem.getDisplayName().equals("§aLocation") &&
                        mainHandItem.getTagCompound().getInteger("HideFlags") == 63) {
                    isHoldingLocation = true;

                    //Makes it so the location highlight renders on top of everything else.
                    GlStateManager.disableDepth();

                    //If the player is also holding a location in their offhand, highlight the area between the locations.
                    if (!offHandItem.isEmpty() &&
                            offHandItem.getDisplayName().equals("§aLocation") &&
                            offHandItem.getTagCompound().hasKey("HideFlags") &&
                            offHandItem.getTagCompound().getInteger("HideFlags") == 63) {
                        isHoldingLocationOffHand = true;

                        try {
                            NBTTagList mainHandLore = mainHandItem.getSubCompound("display").getTagList("Lore", 8);
                            NBTTagList offHandLore = offHandItem.getSubCompound("display").getTagList("Lore", 8);

                            //Finds the two corners of the location area.
                            BlockPos[] locations = MathUtils.getCorners(new BlockPos(
                                            CommandBase.parseDouble(offHandLore.getStringTagAt(0)),
                                            CommandBase.parseDouble(offHandLore.getStringTagAt(1)),
                                            CommandBase.parseDouble(offHandLore.getStringTagAt(2))),
                                    new BlockPos(
                                            CommandBase.parseDouble(mainHandLore.getStringTagAt(0)),
                                            CommandBase.parseDouble(mainHandLore.getStringTagAt(1)),
                                            CommandBase.parseDouble(mainHandLore.getStringTagAt(2))));

                            //Draws the area highlight between the locations.
                            GlStateManager.enableCull();
                            GraphicsUtils.drawBox(event.getPartialTicks(),
                                    locations[0].getX() - 0.001,
                                    locations[0].getY() - 0.001,
                                    locations[0].getZ() - 0.001,
                                    locations[1].getX() + 1.001,
                                    locations[1].getY() + 1.001,
                                    locations[1].getZ() + 1.001,
                                    ColorReference.HIGHLIGHT_DULL_LOCATION);

                            GraphicsUtils.drawVertices(event.getPartialTicks(),
                                    locations[0].getX() - 0.001,
                                    locations[0].getY() - 0.001,
                                    locations[0].getZ() - 0.001,
                                    locations[1].getX() + 1.001,
                                    locations[1].getY() + 1.001,
                                    locations[1].getZ() + 1.001,
                                    ColorReference.HIGHLIGHT_LOCATION);
                            GlStateManager.disableCull();

                            //Draws a cube at where the offHand location is set.
                            GraphicsUtils.drawBlock(event.getPartialTicks(), new BlockPos(
                                            CommandBase.parseDouble(offHandLore.getStringTagAt(0)),
                                            CommandBase.parseDouble(offHandLore.getStringTagAt(1)),
                                            CommandBase.parseDouble(offHandLore.getStringTagAt(2))),
                                    ColorReference.HIGHLIGHT_LOCATION);

                            minecraft.player.sendStatusMessage(new TextComponentString("§*§(255, 0, 0)" + locations[0].getX() + " §2" + locations[0].getY() + " §1" + locations[0].getZ() + " §e| §6" + locations[1].getX() + " " + locations[1].getY() + " " + locations[1].getZ()), true);

                            if (locationMessageSwitch >= 300) locationMessageSwitch = 0;

                            locationMessageSwitch++;
                        } catch (NumberInvalidException exception) {
                            //Uh oh! Invalid location lore... Continue on.
                        }

                    }

                    try {
                        NBTTagList itemLore = mainHandItem.getSubCompound("display").getTagList("Lore", 8);

                        //Draws a cube at where the location is set.
                        GraphicsUtils.drawBlock(event.getPartialTicks(), new BlockPos(
                                        CommandBase.parseDouble(itemLore.getStringTagAt(0)),
                                        CommandBase.parseDouble(itemLore.getStringTagAt(1)),
                                        CommandBase.parseDouble(itemLore.getStringTagAt(2))),
                                ColorReference.HIGHLIGHT_LOCATION);
                        if (!isHoldingLocationOffHand) {
                            minecraft.player.sendStatusMessage(new TextComponentString("§eLocation: §6" + CommandBase.parseDouble(itemLore.getStringTagAt(0)) + " " + CommandBase.parseDouble(itemLore.getStringTagAt(1)) + " " + CommandBase.parseDouble(itemLore.getStringTagAt(2))), true);
                        }
                    } catch (NumberInvalidException exception) {
                        //Uh oh! Invalid location lore... Continue on.
                    }

                    GlStateManager.enableDepth();
                } else {
                    if (locationMessageSwitch > 0 || isHoldingLocation) {
                        locationMessageSwitch = 0;
                        isHoldingLocation = false;
                        minecraft.player.sendStatusMessage(new TextComponentString(""), true);
                    }
                }
            } catch (NullPointerException exception) {
                //Looks like the item didn't have a certain NBT tag, continue on.
            }
        }
    }
}
