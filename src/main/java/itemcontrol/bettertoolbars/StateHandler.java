package itemcontrol.bettertoolbars;

public class StateHandler {
    public static int selectedTabIndex = 0;
    public static int selectedTabPage = 0;
    public static float scrollPosition = 0.0f;
    
    public static boolean needsScrollBar() {
        try {
            return ToolbarTabManager.getTabItems().length / 9 > 5;
        } catch (NullPointerException exception) {
            return false;
        }
    }
    
    public static int getScrollRow() {
        return (int) ((ToolbarTabManager.getTabItems().length / 9) * scrollPosition);
    }
}
