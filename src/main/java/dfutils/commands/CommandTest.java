package dfutils.commands;

import dfutils.utils.MessageUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandTest extends CommandBase implements IClientCommand {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    public String getName() {
        return "test";
    }
    
    public String getUsage(ICommandSender sender) {
        return "§c/test";
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
    
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }
    
    public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {
        
        float f = (float)(minecraft.objectMouseOver.hitVec.x - (double)minecraft.objectMouseOver.getBlockPos().getX());
        float f1 = (float)(minecraft.objectMouseOver.hitVec.y - (double)minecraft.objectMouseOver.getBlockPos().getY());
        float f2 = (float)(minecraft.objectMouseOver.hitVec.z - (double)minecraft.objectMouseOver.getBlockPos().getZ());
    
        ItemStack oldItemStack = minecraft.player.getActiveItemStack();
        ItemStack newItemStack = new ItemStack(Item.getItemById(1), 1, 0);

        TextComponentString[] signText = {
                new TextComponentString("Test String"),
                new TextComponentString(""),
                new TextComponentString(""),
                new TextComponentString("")};
        
        //minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(minecraft.objectMouseOver.getBlockPos(), EnumFacing.WEST, EnumHand.MAIN_HAND, 0, 0, 0));
        //minecraft.playerController.sendSlotPacket(new ItemStack(Item.getItemById(1)), 0);

        //minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(1, 0, 0, 0, 0, true));
        
        //minecraft.playerController.sendSlotPacket(newItemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        //minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(minecraft.objectMouseOver.getBlockPos(), minecraft.objectMouseOver.sideHit, EnumHand.MAIN_HAND, f, f1, f2));
        //minecraft.playerController.windowClick(0, 0, 0, ClickType.PICKUP, minecraft.player);
        //minecraft.playerController.processRightClickBlock(minecraft.player, minecraft.world, minecraft.objectMouseOver.getBlockPos(), minecraft.objectMouseOver.sideHit, minecraft.objectMouseOver.hitVec, EnumHand.MAIN_HAND);
        //minecraft.playerController.sendSlotPacket(oldItemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
    }
}
