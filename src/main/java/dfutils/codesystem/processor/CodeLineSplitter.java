package dfutils.codesystem.processor;

import dfutils.codesystem.objects.CodeLine;
import diamondcore.utils.Tuple;

import java.util.ArrayList;

public class CodeLineSplitter {
	
	/**
	 * @param codeLine The code line to split.
	 * @param splitBlockIndex The index at which the code line exceeds the given bounds.
	 * @return A tuple containing the split code lines.
	 */
	
	// TODO - FINISH THIS
	public Tuple<CodeLine> splitCodeLine(CodeLine codeLine, int splitBlockIndex) {
		int indentLevel = codeLine.getIndentLevel(splitBlockIndex);
		
		if (indentLevel == 0) {
			return simpleSplitCodeLine(codeLine, splitBlockIndex);
		} else {
			CodeLine localScope = codeLine.getLocalScope(splitBlockIndex);
		}
		
		return new Tuple<>();
	}
	
	private Tuple<CodeLine> simpleSplitCodeLine(CodeLine codeLine, int splitBlockIndex) {
		Tuple<CodeLine> lineTuple = new Tuple<>();
		
		/*lineTuple.valueA = new CodeLine(new ArrayList<>(codeLine.codeBlocks.subList(0, splitBlockIndex)));
		lineTuple.valueB = new CodeLine(new ArrayList<>(codeLine.codeBlocks.subList(splitBlockIndex, codeLine.codeBlocks.size())));*/
		
		return lineTuple;
	}
}
