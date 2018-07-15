package dfutils.codetools.misctools;

import dfutils.ColorReference;
import dfutils.codetools.utils.GraphicsUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LocationHighlighting {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {

        if (minecraft.player.isCreative()) {
            try {

                ItemStack itemStack = minecraft.player.getHeldItemMainhand();
                if (!itemStack.isEmpty() &&
                        itemStack.getDisplayName().equals("§aLocation") &&
                        itemStack.getTagCompound().getInteger("HideFlags") == 63) {

                    try {
                        NBTTagList itemLore = itemStack.getSubCompound("display").getTagList("Lore", 8);

                        GlStateManager.disableDepth();
                        GraphicsUtils.drawBlock(event.getPartialTicks(), new BlockPos(
                                        CommandBase.parseDouble(itemLore.getStringTagAt(0)),
                                        CommandBase.parseDouble(itemLore.getStringTagAt(1)),
                                        CommandBase.parseDouble(itemLore.getStringTagAt(2))),
                                ColorReference.HIGHLIGHT_LOCATION);
                        GlStateManager.enableDepth();

                    } catch (NumberInvalidException exception) {
                        //Uh oh! Invalid location lore... Continue on.
                    }
                }
            } catch (NullPointerException exception) {
                //Looks like the item didn't have a certain NBT tag, continue on.
            }
        }
    }
}
