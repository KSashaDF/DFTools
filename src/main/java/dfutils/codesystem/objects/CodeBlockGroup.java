package dfutils.codesystem.objects;

public enum CodeBlockGroup {
	REGULAR(false),
	CONDITIONAL(false),
	EVENT(true),
	SPECIAL(false);
	
	public final boolean isLineStarter;
	
	CodeBlockGroup(boolean isLineStarter) {
		this.isLineStarter = isLineStarter;
	}
}
