package dfutils.utils.rpc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import dfutils.utils.playerdata.PlayerMode;
import dfutils.utils.playerdata.PlayerStateHandler;

import static dfutils.config.ConfigHandler.DISCORD_RPC_ENABLED;

public class PresenceHandler {
    private static long lastTimestamp = 0;
    private static PlayerMode lastMode;
    private static DiscordRPC lib = DiscordRPC.INSTANCE;
    private static boolean DiscordRPCSetup = false;
    private static boolean wasInSession = false;

    private static PresenceState presenceState = PresenceState.NOTREADY;

    /**
     * Returns the current State
     *
     * @return PresenceState
     */
    public static PresenceState getState() {
        if (!DISCORD_RPC_ENABLED) return PresenceState.DISABLED;
        return presenceState;
    }

    /**
     * Initializes Rich Presence.
     */
    public static void initPresence() {
        String applicationId = "476455349780611072";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> presenceState = PresenceState.READY;
        DiscordRPCSetup = true;
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
    }

    /**
     * Updates the Rich Presence.
     */
    public static void updatePresence(boolean forceReload) {
        if(DISCORD_RPC_ENABLED) {
            if(!DiscordRPCSetup) {
                initPresence();
            }

            if (!forceReload) {
                if (wasInSession && !PlayerStateHandler.isInSupportSession ||
                        lastMode != PlayerStateHandler.playerMode &&
                                !PlayerStateHandler.isInSupportSession) {
                    lastTimestamp = System.currentTimeMillis() / 1000; // epoch second
                    lastMode = PlayerStateHandler.playerMode;
                    wasInSession = false;

                    updatePresenceData();
                } else if (!wasInSession && PlayerStateHandler.isInSupportSession) {
                    wasInSession = true;

                    lastTimestamp = System.currentTimeMillis() / 1000; // epoch second
                    lastMode = PlayerStateHandler.playerMode;

                    updatePresenceData();
                }
            } else updatePresenceData();
        } else destroyPresence();
    }

    private static void updatePresenceData() {
        DiscordRichPresence presence = new DiscordRichPresence();

        presence.smallImageKey = "dflogo";
        presence.startTimestamp = lastTimestamp;

        if(!PlayerStateHandler.isInSupportSession) {
            switch(PlayerStateHandler.playerMode) {
                case SPAWN:
                    presence.largeImageKey = "spawn";
                    presence.details = "At spawn";
                    break;
                case DEV:
                    presence.largeImageKey = "dev";
                    presence.largeImageText = "Mode Dev";
                    break;
                case BUILD:
                    presence.largeImageKey = "build";
                    presence.largeImageText = "Mode Build";
                    break;
                case PLAY:
                    presence.largeImageKey = "play";
                    presence.largeImageText = "Mode Play";
                    break;
            }

            if(PlayerStateHandler.playerMode == PlayerMode.DEV ||
                    PlayerStateHandler.playerMode == PlayerMode.BUILD ||
                    PlayerStateHandler.playerMode == PlayerMode.PLAY) {
                if(PlayerStateHandler.plotId != 0) presence.smallImageText = "Plot ID: " + PlayerStateHandler.plotId;
                if(PlayerStateHandler.plotName != null) presence.details = PlayerStateHandler.plotName;
                if(PlayerStateHandler.plotOwner != null) presence.state = "By " + PlayerStateHandler.plotOwner;
            }
        } else {
            switch (PlayerStateHandler.supportSessionRole) {
                case SUPPORTER:
                    presence.largeImageKey = "supporter";
                    presence.largeImageText = "Supporting " + PlayerStateHandler.supportPartner;
                    presence.details = "Supporting " + PlayerStateHandler.supportPartner;
                    break;
                case SUPPORTEE:
                    presence.largeImageKey = "supportee";
                    presence.largeImageText = "Supported by " + PlayerStateHandler.supportPartner;
                    presence.smallImageText = "Plot ID: " + PlayerStateHandler.plotId;
                    presence.details = "Being Supported";
                    presence.state = "by " + PlayerStateHandler.supportPartner;
            }
        }

        lib.Discord_UpdatePresence(presence);
    }

    /**
     * Destroys the Rich Presence Instance.
     */
    public static void destroyPresence() {
        lib.Discord_Shutdown();
        lastTimestamp = 0;
        lastMode = null;
        DiscordRPCSetup = false;
        wasInSession = false;
    }
}
