package dfutils.utils.playerdata;

public enum PlotSize {
    NULL(0),
    BASIC(50),
    LARGE(100),
    MASSIVE(300);

    public final int size;

    PlotSize(int size) {
        this.size = size;
    }
}
