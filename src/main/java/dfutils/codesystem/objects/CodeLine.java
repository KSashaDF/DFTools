package dfutils.codesystem.objects;

import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;

public class CodeLine implements Iterable<CodeBlock> {
	
	private ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
	
	/**
	 * Initializes an empty code line with no code blocks.
	 */
	public CodeLine() {
	}
	
	public CodeLine(ArrayList<CodeBlock> codeBlocks) {
		this.codeBlocks = codeBlocks;
	}
	
	@Deprecated
	public CodeLine(NBTTagList codeLineNbt) {
		fromNbt(codeLineNbt);
	}
	
	@Deprecated
	public NBTTagList toNbt() {
		NBTTagList codeLineNbt = new NBTTagList();
		
		for(CodeBlock codeBlock : codeBlocks) {
			codeLineNbt.appendTag(codeBlock.toNbt());
		}
		
		return codeLineNbt;
	}
	
	@Deprecated
	public void fromNbt(NBTTagList codeLineNbt) {
		codeBlocks.clear();
		
		for(int i = 0; i < codeLineNbt.tagCount(); i++) {
			codeBlocks.add(new CodeBlock(codeLineNbt.getCompoundTagAt(i)));
		}
	}
	
	@Nonnull
	@Override
	public Iterator<CodeBlock> iterator() {
		return new BlockIterator(this);
	}
	
	public CodeBlock getBlock(int index) {
		return codeBlocks.get(index);
	}
	
	public void setBlock(CodeBlock block, int index) {
		codeBlocks.set(index, block);
	}
	
	public int getArrayLength() {
		return codeBlocks.size();
	}
	
	/**
	 * @return A full copy of the code line, everything down to the argument items is copied.
	 */
	public CodeLine copy() {
		ArrayList<CodeBlock> newCodeBlocks = new ArrayList<>();
		
		for(CodeBlock codeBlock : codeBlocks) {
			newCodeBlocks.add(codeBlock.copy());
		}
		
		return new CodeLine(newCodeBlocks);
	}
	
	/**
	 * @return The length of the code line in blocks.
	 */
	public int getBlockLength() {
		return codeBlocks.size() * 2;
	}
	
	/**
	 * @return The amount of code blocks that are in the code line.
	 */
	public int getBlockCount() {
		int blockCount = 0;
		
		for (CodeBlock codeBlock : codeBlocks) {
			if (codeBlock.getBlockType() != CodeBlockType.END_BRACKET) {
				blockCount++;
			}
		}
		
		return blockCount;
	}
	
	/**
	 * @param blockIndex The index of the code block in the list of code blocks that should be checked.
	 * @return The amount of code pistons that surround the code block at the given index.
	 */
	public int getIndentLevel(int blockIndex) {
		int indentLevel = 0;
		
		for (int i = 0; i < blockIndex; i++) {
			if (codeBlocks.get(i).getBlockType().hasBrackets) {
				indentLevel++;
			} else if (codeBlocks.get(i).getBlockType() == CodeBlockType.END_BRACKET) {
				indentLevel++;
			}
		}
		
		return indentLevel;
	}
	
	/**
	 * @param blockIndex The index of the code block in the list of code blocks that should be checked.
	 * @return The index for the closing bracket of the given code block's local scope.
	 */
	public int getClosingBracket(int blockIndex) {
		int indentLevel = 1;
		
		do {
			blockIndex++;
			CodeBlockType blockName = codeBlocks.get(blockIndex).getBlockType();
			
			if (blockName == CodeBlockType.END_BRACKET) {
				indentLevel--;
			} else if (blockName.hasBrackets) {
				indentLevel++;
			}
		} while (indentLevel != 0);
		
		return blockIndex;
	}
	
	/**
	 * @param blockIndex The index of the code block in the list of code blocks that should be checked.
	 * @return The index for the opening bracket of the given code block's local scope.
	 */
	public int getOpeningBracket(int blockIndex) {
		int indentLevel = 1;
		
		do {
			blockIndex--;
			CodeBlockType blockName = codeBlocks.get(blockIndex).getBlockType();
			
			if (blockName == CodeBlockType.END_BRACKET) {
				indentLevel++;
			} else if (blockName.hasBrackets) {
				indentLevel--;
			}
		} while(indentLevel != 0);
		
		return blockIndex;
	}
	
	/**
	 * @param blockIndex The index of the code block in the list of code blocks that should be checked.
	 * @return A code line containing only the enclosing scope of the given code block.
	 */
	public CodeLine getLocalScope(int blockIndex) {
		int openingBracketIndex = getOpeningBracket(blockIndex);
		int closingBracketIndex = getClosingBracket(blockIndex);
		
		return new CodeLine(new ArrayList<>(codeBlocks.subList(openingBracketIndex - 1, closingBracketIndex)));
	}
	
	public boolean hasEventBlock() {
		return codeBlocks.get(0).getBlockType().blockGroup.isLineStarter;
	}
	
	public CodeBlock getEventBlock() {
		return codeBlocks.get(0);
	}
}
