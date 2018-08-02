package dfutils.utils.playerdata;

public enum PlayerMode {
    NULL(false),
    SPAWN(false),
    PLAY(false),
    BUILD(true),
    DEV(true);

    public final boolean isCreative;

    PlayerMode(boolean isCreative) {
        this.isCreative = isCreative;
    }
}
