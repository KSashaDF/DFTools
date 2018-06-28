package diamondfireutils;

public enum ColorReference {
    HIGHLIGHT_CODE(70, 100, 200, 250),
    DULL_COPY_CODE(60, 40, 150, 50),
    BRIGHT_COPY_CODE(80, 40, 200, 50),
    DULL_PASTE_CODE(60, 255, 210, 0),
    BRIGHT_PASTE_CODE(80, 255, 240, 0);
    
    public final int alpha;
    public final int red;
    public final int green;
    public final int blue;
    
    ColorReference(int a, int r, int g, int b) {
        alpha = a;
        red = r;
        green = g;
        blue = b;
    }
}
