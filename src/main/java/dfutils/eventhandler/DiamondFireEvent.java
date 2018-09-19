package dfutils.eventhandler;

import dfutils.config.ConfigHandler;
import dfutils.utils.rpc.PresenceHandler;
import diamondcore.utils.playerdata.PlayerStateHandler;
import diamondcore.utils.playerdata.SupportSessionRole;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DiamondFireEvent {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    @SubscribeEvent
    public static void onDiamondFireEvent(diamondcore.eventhandler.customevents.DiamondFireEvent event) {
    
        if (event instanceof diamondcore.eventhandler.customevents.DiamondFireEvent.EnterSessionEvent) {
            if (PlayerStateHandler.supportSessionRole == SupportSessionRole.SUPPORTER && !ConfigHandler.SUPPORT_START_MESSAGE.equals("")) {
                new Thread(new CommandWait(ConfigHandler.SUPPORT_START_MESSAGE.replace("%player%", PlayerStateHandler.supportPartner))).start();
            }
        }
        
        if (event instanceof diamondcore.eventhandler.customevents.DiamondFireEvent.ExitSessionEvent) {
            if (PlayerStateHandler.supportSessionRole == SupportSessionRole.SUPPORTER && ConfigHandler.SUPPORT_END_AUTOMATIC_LEAVE) {
                minecraft.player.sendChatMessage("/spawn");
            }
            if (PlayerStateHandler.supportSessionRole == SupportSessionRole.SUPPORTER && !ConfigHandler.SUPPORT_END_MESSAGE.equals("")) {
                new Thread(new CommandWait(ConfigHandler.SUPPORT_END_MESSAGE.replace("%player%", PlayerStateHandler.supportPartner))).start();
            }
        }
    
        //Clear all potion effect particles, this is to fix the irremovable potion particle glitch.
        minecraft.player.clearActivePotions();
        
        // Update Discord Presence
        PresenceHandler.updatePresence(PlayerStateHandler.discordRPCForceReload);
        PlayerStateHandler.discordRPCForceReload = false;
    }
    
    static class CommandWait implements Runnable {
        
        String supportMessage;
        
        CommandWait(String supportMessage) {
            this.supportMessage = supportMessage;
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                minecraft.player.sendChatMessage(supportMessage);
            } catch (InterruptedException exception) {
                //Uh oh! Thread wait interrupted, continue on.
            }
        }
    }
}
