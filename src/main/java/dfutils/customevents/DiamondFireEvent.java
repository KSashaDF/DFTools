package dfutils.customevents;

import dfutils.utils.playerdata.PlayerMode;

public class DiamondFireEvent {
    
    public static class JoinPlotEvent extends DiamondFireEvent {
        
        public int plotId;
        public String plotName;
        public String plotOwner;
        public PlayerMode playerMode;
        
        public JoinPlotEvent(int plotId, String plotName, String plotOwner, PlayerMode playerMode) {
            this.plotId = plotId;
            this.plotName = plotName;
            this.plotOwner = plotOwner;
            this.playerMode = playerMode;
        }
    }
    
    public static class LeavePlotEvent extends DiamondFireEvent {}
    
    public static class ChangeModeEvent extends DiamondFireEvent {
        
        public PlayerMode playerMode;
        
        public ChangeModeEvent(PlayerMode playerMode) {
            this.playerMode = playerMode;
        }
    }
    
    
    public static class EnterSessionEvent extends DiamondFireEvent {
    
    }
    
    public static class ExitSessionEvent extends DiamondFireEvent {
    
    }
}
