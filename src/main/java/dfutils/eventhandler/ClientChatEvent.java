package dfutils.eventhandler;

import itemcontrol.commands.itemcontrol.CommandGive;
import dfutils.commands.shortcuts.ShortcutLastMsg;
import dfutils.commands.shortcuts.ShortcutPlotClear;
import dfutils.commands.shortcuts.ShortcutSupportChat;
import dfutils.commands.shortcuts.ShortcutVarpurge;
import dfutils.config.ConfigHandler;
import diamondcore.utils.MathUtils;
import diamondcore.utils.TextUtils;
import diamondcore.utils.language.MessageHelper;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientChatEvent {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static int commandCooldown = 0;
    
    @SubscribeEvent
    public static void onClientSendMessage(final net.minecraftforge.client.event.ClientChatEvent event) {
        
        //The following section of code overrides the default DiamondFire command cooldown.
        //This is done to prevent the rest of the send message event code from being executed and
        //to make it so the command cooldown does not reset if you execute a command while the
        //cooldown is still active.
        if (commandCooldown - 40 > minecraft.player.ticksExisted) {
            commandCooldown = 0;
        }
        
        if (event.getMessage().startsWith("/") && PlayerStateHandler.isOnDiamondFire) {
            String commandName = TextUtils.splitString(event.getMessage())[0].replace("/", "");
            ICommand commandObject = ClientCommandHandler.instance.getCommands().get(commandName);
    
            if (!(commandObject instanceof IClientCommand)) {
                if (commandCooldown > minecraft.player.ticksExisted) {
                    int secondsLeft = MathUtils.ceilingDivide(commandCooldown - minecraft.player.ticksExisted, 20);
                    
                    if (secondsLeft == 1) {
                        MessageHelper.message("command.commandCooldown");
                    } else {
                        MessageHelper.message("command.commandCooldownPlural", Integer.toString(secondsLeft));
                    }
                    
                    minecraft.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                    event.setCanceled(true);
                    return;
                } else {
                    commandCooldown = minecraft.player.ticksExisted + 40;
                }
            }
        }
        
        CommandGive.commandGiveClientSendMessage(event);
        ShortcutLastMsg.shortcutLastMsgClientSendMessage(event);
        
        if (PlayerStateHandler.isOnDiamondFire) {
            ShortcutSupportChat.shortcutSupportChatClientSendMessage(event);
            PlayerStateHandler.playerStateHandlerChatSent(event);
    
            //If the /plot varpurge confirm config option is enabled, execute the varpurge confirm code.
            if (ConfigHandler.DO_VARPURGE_CONFIRM) {
                ShortcutVarpurge.shortcutVarpurgeClientSendMessage(event);
            }
            //If the /plot clear confirm config option is disabled, execute the plot clear shortcut code.
            if (!ConfigHandler.DO_PLOTCLEAR_CONFIRM) {
                ShortcutPlotClear.shortcutPlotClearClientSendMessage(event);
            }
        }
    }
}