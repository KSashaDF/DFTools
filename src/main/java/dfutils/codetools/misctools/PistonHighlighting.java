package dfutils.codetools.misctools;

import dfutils.utils.ColorReference;
import dfutils.codehandler.utils.CodeBlockUtils;
import dfutils.utils.GraphicsUtils;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
            IBlockState blockClicked = minecraft.world.getBlockState(blockPos);
            String blockName = blockClicked.getBlock().getLocalizedName();
            
            if ((blockName.equals("Piston") || blockName.equals("Sticky Piston")) && CodeBlockUtils.isCodeBlock(blockPos) && CodeBlockUtils.hasOppositePiston(blockPos)) {
                
                highlightCooldown = minecraft.player.ticksExisted + 2;
                event.setCanceled(true);
                
                if (blockClicked.getValue(PropertyDirection.create("facing")) == EnumFacing.SOUTH) {
            
                    if (blockPos.equals(openingPistonPos)) {
                
                        clearHighlight();
                        
                    } else {
                        doPistonHighlight = true;
                        openingPistonPos = blockPos;
                        closingPistonPos = CodeBlockUtils.getOppositePiston(blockPos);
                    }
            
                } else if (blockClicked.getValue(PropertyDirection.create("facing")) == EnumFacing.NORTH) {
            
                    if (blockPos.equals(closingPistonPos)) {
                
                        clearHighlight();
                        
                    } else {
                        doPistonHighlight = true;
                        closingPistonPos = blockPos;
                        openingPistonPos = CodeBlockUtils.getOppositePiston(blockPos);
                    }
            
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
            
            if (!(minecraft.world.getBlockState(openingPistonPos).getBlock().getLocalizedName().equals("Piston") ||
                    minecraft.world.getBlockState(openingPistonPos).getBlock().getLocalizedName().equals("Sticky Piston"))) {
    
                clearHighlight();
                return;
            }
    
            if (!(minecraft.world.getBlockState(closingPistonPos).getBlock().getLocalizedName().equals("Piston") ||
                    minecraft.world.getBlockState(closingPistonPos).getBlock().getLocalizedName().equals("Sticky Piston"))) {
                
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
