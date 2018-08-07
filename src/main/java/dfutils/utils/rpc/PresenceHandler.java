package dfutils.utils.rpc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import dfutils.utils.playerdata.PlayerMode;
import dfutils.utils.playerdata.PlayerStateHandler;
import dfutils.utils.playerdata.SupportSessionRole;

public class PresenceHandler {
    private static long lastTimestamp = 0;
    private static PlayerMode lastMode;
    private static DiscordRPC lib = DiscordRPC.INSTANCE;
    private static boolean DiscordRPCSetup = false;
    private static boolean wasInSession = false;

    public static void updatePresence() {
        if(!DiscordRPCSetup) {
            createPresence();
        }

        System.out.println("IN SESSION: " + PlayerStateHandler.isInSupportSession);

        if(!PlayerStateHandler.isOnDiamondFire) {
            lib.Discord_ClearPresence();
            lastTimestamp = 0;
        } else {
            if(wasInSession || lastMode != PlayerStateHandler.playerMode) {
                lastTimestamp = System.currentTimeMillis() / 1000; // epoch second
                lastMode = PlayerStateHandler.playerMode;
                wasInSession = false;

                updatePresenceData();
            } else if(PlayerStateHandler.isInSupportSession) {
                wasInSession = true;

                lastTimestamp = System.currentTimeMillis() / 1000; // epoch second
                lastMode = PlayerStateHandler.playerMode;

                updatePresenceData();
            }
        }
    }

    private static void updatePresenceData() {
        if(!PlayerStateHandler.isInSupportSession) {
            if (PlayerStateHandler.playerMode == PlayerMode.SPAWN) {
                DiscordRichPresence presence = new DiscordRichPresence();
                presence.largeImageKey = "compass";
                presence.smallImageKey = "dflogo";
                presence.details = "At spawn";
                presence.startTimestamp = lastTimestamp;
                lib.Discord_UpdatePresence(presence);
            } else if (PlayerStateHandler.playerMode == PlayerMode.PLAY) {
                DiscordRichPresence presence = new DiscordRichPresence();
                presence.largeImageKey = "ironsword";
                presence.largeImageText = "Mode Play";
                presence.smallImageKey = "dflogo";
                presence.smallImageText = "Plot ID: " + PlayerStateHandler.plotId;
                presence.details = PlayerStateHandler.plotName;
                presence.state = "By " + PlayerStateHandler.plotOwner;
                presence.startTimestamp = lastTimestamp;
                lib.Discord_UpdatePresence(presence);
            } else if (PlayerStateHandler.playerMode == PlayerMode.DEV) {
                DiscordRichPresence presence = new DiscordRichPresence();
                presence.largeImageKey = "commandblock";
                presence.largeImageText = "Mode Dev";
                presence.smallImageKey = "dflogo";
                presence.smallImageText = "Plot ID: " + PlayerStateHandler.plotId;
                presence.details = PlayerStateHandler.plotName;
                presence.state = "By " + PlayerStateHandler.plotOwner;
                presence.startTimestamp = lastTimestamp;
                lib.Discord_UpdatePresence(presence);
            } else if (PlayerStateHandler.playerMode == PlayerMode.BUILD) {
                DiscordRichPresence presence = new DiscordRichPresence();
                presence.largeImageKey = "anvil";
                presence.largeImageText = "Mode Build";
                presence.smallImageKey = "dflogo";
                presence.smallImageText = "Plot ID: " + PlayerStateHandler.plotId;
                presence.details = PlayerStateHandler.plotName;
                presence.state = "By " + PlayerStateHandler.plotOwner;
                presence.startTimestamp = lastTimestamp;
                lib.Discord_UpdatePresence(presence);
            }
        } else {
            if(PlayerStateHandler.supportSessionRole == SupportSessionRole.SUPPORTER) {
                DiscordRichPresence presence = new DiscordRichPresence();
                presence.largeImageKey = "commandblock";
                presence.largeImageText = "Supporting";
                presence.smallImageKey = "dflogo";
                presence.smallImageText = "Plot ID: " + PlayerStateHandler.plotId;
                presence.details = "Helping " + PlayerStateHandler.supportPartner;
                presence.state = "on " + PlayerStateHandler.plotName;
                presence.startTimestamp = lastTimestamp;
                lib.Discord_UpdatePresence(presence);
            } else {
                DiscordRichPresence presence = new DiscordRichPresence();
                presence.largeImageKey = "anvil";
                presence.largeImageText = "Being supported";
                presence.smallImageKey = "dflogo";
                presence.smallImageText = "Plot ID: " + PlayerStateHandler.plotId;
                presence.details = "Being supported by:";
                presence.state = PlayerStateHandler.supportPartner;
                presence.startTimestamp = lastTimestamp;
                lib.Discord_UpdatePresence(presence);
            }
        }
    }

    private static void createPresence() {
        String applicationId = "476455349780611072";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Discord RPC Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRPCSetup = true;
    }
}
