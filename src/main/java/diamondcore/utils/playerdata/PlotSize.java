package diamondcore.utils.playerdata;

public enum PlotSize {
	BASIC(50),
	LARGE(100),
	MASSIVE(300);
	
	public final int size;
	
	PlotSize(int size) {
		this.size = size;
	}
}
