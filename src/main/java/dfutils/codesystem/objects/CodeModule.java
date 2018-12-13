package dfutils.codesystem.objects;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;

public class CodeModule implements Iterable<CodeLine> {
	
	private ArrayList<CodeLine> codeLines = new ArrayList<>();
	
	public CodeModule() {
	}
	
	public CodeModule(ArrayList<CodeLine> codeLines) {
		this.codeLines = codeLines;
	}
	
	@Nonnull
	@Override
	public Iterator<CodeLine> iterator() {
		return new LineIterator(this);
	}
	
	public CodeLine getLine(int index) {
		return codeLines.get(index);
	}
	
	public void setLine(CodeLine codeLine, int index) {
		this.codeLines.set(index, codeLine);
	}
	
	public int getLineCount() {
		return codeLines.size();
	}
}
