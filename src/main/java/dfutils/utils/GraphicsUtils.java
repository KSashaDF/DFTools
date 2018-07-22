package dfutils.utils;

import dfutils.ColorReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class GraphicsUtils {
    
    public static void drawSign(float partialTicks, BlockPos blockPos, ColorReference color) {
        drawCube(partialTicks, blockPos.getX() + 0.893, blockPos.getY() + 0.27, blockPos.getZ() - 0.0001,
                blockPos.getX() + 1.0001, blockPos.getY() + 0.771, blockPos.getZ() + 1.0001, color);
    }
    
    public static void drawChest(float partialTicks, BlockPos blockPos, ColorReference color) {
        drawCube(partialTicks, blockPos.getX() + 0.062, blockPos.getY() - 0.0001, blockPos.getZ() + 0.062,
                blockPos.getX() + 0.938, blockPos.getY() + 0.876, blockPos.getZ() + 0.938, color);
    }
    
    public static void drawBlock(float partialTicks, BlockPos blockPos, ColorReference color) {
        drawCube(partialTicks, blockPos.getX() - 0.0005, blockPos.getY() - 0.0005, blockPos.getZ() - 0.0005,
                blockPos.getX() + 1.0005, blockPos.getY() + 1.0005, blockPos.getZ() + 1.0005, color);
    }

    //Draws a box - Like a normal cube except you can see all the sides, even from inside it.
    public static void drawBox(float partialTicks, double x0, double y0, double z0, double x1, double y1, double z1, ColorReference color) {

        drawCube(partialTicks, x0, y0, z0, x0, y1, z1, color);
        drawCube(partialTicks, x0, y0, z0, x1, y0, z1, color);
        drawCube(partialTicks, x0, y0, z0, x1, y1, z0, color);
        drawCube(partialTicks, x1, y0, z0, x1, y1, z1, color);
        drawCube(partialTicks, x0, y1, z0, x1, y1, z1, color);
        drawCube(partialTicks, x0, y0, z1, x1, y1, z1, color);
    }


    public static void drawCube(float partialTicks, double x0, double y0, double z0, double x1, double y1, double z1, ColorReference color) {
        drawCube(partialTicks, x0, y0, z0, x1, y1, z1, color.alpha, color.red, color.green, color.blue);
    }
    
    private static void drawCube(float partialTicks, double x0, double y0, double z0, double x1, double y1, double z1, int a, int r, int g, int b) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        EntityPlayer player = Minecraft.getMinecraft().player;
    
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        
        x0 = x0 - (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks);
        y0 = y0 - (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks);
        z0 = z0 - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks);
    
        x1 = x1 - (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks);
        y1 = y1 - (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks);
        z1 = z1 - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks);
        
        buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
    
        buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
    
        buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
    
        buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
    
        buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
    
        buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();
    
        tessellator.draw();
    
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    public static void drawVertices(float partialTicks, double x0, double y0, double z0, double x1, double y1, double z1, ColorReference color) {
        drawVertices(partialTicks, x0, y0, z0, x1, y1, z1, color.alpha, color.red, color.green, color.blue);
    }

    private static void drawVertices(float partialTicks, double x0, double y0, double z0, double x1, double y1, double z1, int a, int r, int g, int b) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        EntityPlayer player = Minecraft.getMinecraft().player;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

        x0 = x0 - (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks);
        y0 = y0 - (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks);
        z0 = z0 - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks);

        x1 = x1 - (player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks);
        y1 = y1 - (player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks);
        z1 = z1 - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks);

        buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();

        buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();

        buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();

        buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();


        buffer.pos(x0, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();

        buffer.pos(x1, y0, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();

        buffer.pos(x0, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();

        buffer.pos(x1, y0, z1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();


        buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();

        buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();

        buffer.pos(x0, y1, z0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z1).color(r, g, b, a).endVertex();

        buffer.pos(x1, y1, z0).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z1).color(r, g, b, a).endVertex();

        tessellator.draw();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }
}
