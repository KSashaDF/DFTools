package dfutils.utils.playerdata;

public enum PlayerMode {
    SPAWN(false),
    PLAY(false),
    BUILD(true),
    DEV(true);

    public final boolean isCreative;

    PlayerMode(boolean isCreative) {
        this.isCreative = isCreative;
    }
}
