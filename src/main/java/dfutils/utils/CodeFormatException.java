package dfutils.utils;

import diamondcore.utils.MessageUtils;
import net.minecraft.util.math.BlockPos;

public class CodeFormatException extends Exception {
	
	public BlockPos codeErrorPos;
	
	public CodeFormatException() {
	}
	
	public CodeFormatException(BlockPos errorPos) {
		codeErrorPos = errorPos;
	}
	
	public void printError() {
		if (codeErrorPos == null) {
			MessageUtils.errorMessage("Invalid code block format!");
		} else {
			MessageUtils.errorMessage("Invalid code block format! Error occurred at " +
					codeErrorPos.getX() + ", " + codeErrorPos.getY() + ", " + codeErrorPos.getZ());
		}
	}
}