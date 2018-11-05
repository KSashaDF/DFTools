package dfutils.colorcodes;

import net.minecraft.util.Tuple;

class ColorCodeParser {
	
	public static String parseColorText(String text, boolean replaceColorCodes) {
		StringBuilder parsedColorText = new StringBuilder();
		
		for (int index = 0; index < text.length(); index++) {
			char textChar = text.charAt(index);
			
			if (textChar == '&') {
				Tuple parsedColorCodeTuple = parseColorCode(text, index, false);
				String parsedColorCode = (String) parsedColorCodeTuple.getFirst();
				int newIndex = (Integer) parsedColorCodeTuple.getSecond();
				
				parsedColorText.append(parsedColorCode);
				if (!replaceColorCodes) {
					parsedColorText.append(text.substring(index, newIndex + 1));
				}
				
				index = newIndex;
			} else {
				parsedColorText.append(textChar);
			}
		}
		
		return parsedColorText.toString();
	}
	
	private static Tuple parseColorCode(String text, int colorCodeIndex, boolean requireColorChar) {
		StringBuilder parsedColorCode = new StringBuilder();
		
		return new Tuple<>(parsedColorCode.toString(), 0);
	}
}
