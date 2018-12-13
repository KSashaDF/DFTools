package dfutils.codesystem.objects;

import java.util.Iterator;

public class BlockIterator implements Iterator<CodeBlock> {
	
	private CodeLine codeLine;
	private int blockIndex = 0;
	
	BlockIterator(CodeLine codeLine) {
		this.codeLine = codeLine;
	}
	
	@Override
	public boolean hasNext() {
		int checkBlockIndex = blockIndex + 1;
		
		while (checkBlockIndex < codeLine.getArrayLength()) {
			if (codeLine.getBlock(checkBlockIndex).getBlockType() != CodeBlockType.END_BRACKET) {
				return true;
			}
			
			checkBlockIndex++;
		}
		
		return false;
	}
	
	@Override
	public CodeBlock next() {
		blockIndex++;
		
		while (codeLine.getBlock(blockIndex).getBlockType() != CodeBlockType.END_BRACKET) {
			blockIndex++;
		}
		
		return codeLine.getBlock(blockIndex);
	}
}
