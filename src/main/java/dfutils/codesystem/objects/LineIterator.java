package dfutils.codesystem.objects;

import java.util.Iterator;

public class LineIterator implements Iterator<CodeLine> {
	
	private CodeModule codeModule;
	private int lineIndex = 0;
	
	LineIterator(CodeModule codeModule) {
		this.codeModule = codeModule;
	}
	
	@Override
	public boolean hasNext() {
		return lineIndex < codeModule.getLineCount() - 1;
	}
	
	@Override
	public CodeLine next() {
		lineIndex++;
		return codeModule.getLine(lineIndex);
	}
}
