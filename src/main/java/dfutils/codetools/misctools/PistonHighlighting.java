package dfutils.codetools.misctools;

import dfutils.codetools.codehandler.utils.CodeBlockUtils;
import diamondcore.utils.BlockUtils;
import diamondcore.utils.ColorReference;
import diamondcore.utils.GraphicsUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PistonHighlighting {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    private static boolean doPistonHighlight = false;
    private static int highlightCooldown = 0;
    
    private static BlockPos openingPistonPos;
    private static BlockPos closingPistonPos;
    
    public static void pistonHighlightingLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        
        if (highlightCooldown - 3 > minecraft.player.ticksExisted) highlightCooldown = 0;
        
        if (minecraft.player.isCreative() && minecraft.player.isSneaking() && minecraft.player.ticksExisted > highlightCooldown) {
    
            BlockPos blockPos = minecraft.objectMouseOver.getBlockPos();
            String blockName = BlockUtils.getName(blockPos);
            
            if ((blockName.equals("minecraft:piston") || blockName.equals("minecraft:sticky_piston")) && CodeBlockUtils.isCodeBlock(blockPos) && CodeBlockUtils.hasOppositePiston(blockPos)) {
                
                highlightCooldown = minecraft.player.ticksExisted + 2;
                event.setCanceled(true);
                
                if (BlockUtils.getFacing(blockPos) == EnumFacing.SOUTH) {
            
                    if (blockPos.equals(openingPistonPos)) {
                
                        clearHighlight();
                        
                    } else {
                        doPistonHighlight = true;
                        openingPistonPos = blockPos;
                        closingPistonPos = CodeBlockUtils.getOppositePiston(blockPos);
                    }

                    minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3f, 1.0f);
                } else if (BlockUtils.getFacing(blockPos) == EnumFacing.NORTH) {
            
                    if (blockPos.equals(closingPistonPos)) {
                
                        clearHighlight();
                        
                    } else {
                        doPistonHighlight = true;
                        closingPistonPos = blockPos;
                        openingPistonPos = CodeBlockUtils.getOppositePiston(blockPos);
                    }
    
                    minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.3f, 1.0f);
                }
            }
        }
    }
    
    public static void pistonHighlightingRenderWorldLast(RenderWorldLastEvent event) {
        
        if (doPistonHighlight) {
            
            GlStateManager.disableDepth();
            
            GraphicsUtils.drawCube(event.getPartialTicks(), openingPistonPos.getX() - 0.01, openingPistonPos.getY() - 0.01, openingPistonPos.getZ() - 0.01,
                    openingPistonPos.getX() + 1.01, openingPistonPos.getY() + 1.01, openingPistonPos.getZ() + 1.01,
                    ColorReference.HIGHLIGHT_CODE);
    
            GraphicsUtils.drawCube(event.getPartialTicks(), closingPistonPos.getX() - 0.01, closingPistonPos.getY() - 0.01, closingPistonPos.getZ() - 0.01,
                    closingPistonPos.getX() + 1.01, closingPistonPos.getY() + 1.01, closingPistonPos.getZ() + 1.01,
                    ColorReference.HIGHLIGHT_CODE);

            GlStateManager.enableDepth();

            if (!minecraft.player.isCreative()) {
                clearHighlight();
                return;
            }
            
            if (!(BlockUtils.getName(openingPistonPos).equals("minecraft:piston") ||
                    BlockUtils.getName(openingPistonPos).equals("minecraft:sticky_piston"))) {
    
                clearHighlight();
                return;
            }
    
            if (!(BlockUtils.getName(closingPistonPos).equals("minecraft:piston") ||
                    BlockUtils.getName(closingPistonPos).equals("minecraft:sticky_piston"))) {
                
                if (CodeBlockUtils.hasOppositePiston(openingPistonPos)) {
                    closingPistonPos = CodeBlockUtils.getOppositePiston(openingPistonPos);
                } else {
                    clearHighlight();
                }
            }
        }
    }
    
    private static void clearHighlight() {
        doPistonHighlight = false;
        openingPistonPos = null;
        closingPistonPos = null;
    }
}
