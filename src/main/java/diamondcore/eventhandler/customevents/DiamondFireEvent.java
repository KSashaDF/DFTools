package diamondcore.eventhandler.customevents;

import diamondcore.utils.playerdata.PlayerMode;
import diamondcore.utils.playerdata.SupportSessionRole;
import net.minecraftforge.fml.common.eventhandler.Event;

public class DiamondFireEvent extends Event {
	
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
	
	public static class LeavePlotEvent extends DiamondFireEvent {
	}
	
	public static class RenamePlotEvent extends DiamondFireEvent {
	}
	
	public static class ChangeModeEvent extends DiamondFireEvent {
		
		public PlayerMode playerMode;
		
		public ChangeModeEvent(PlayerMode playerMode) {
			this.playerMode = playerMode;
		}
	}
	
	
	public static class EnterSessionEvent extends DiamondFireEvent {
		
		public String supportPartner;
		public SupportSessionRole supportSessionRole;
		
		public EnterSessionEvent(String supportPartner, SupportSessionRole supportSessionRole) {
			this.supportPartner = supportPartner;
			this.supportSessionRole = supportSessionRole;
		}
	}
	
	public static class ExitSessionEvent extends DiamondFireEvent {
	}
}
