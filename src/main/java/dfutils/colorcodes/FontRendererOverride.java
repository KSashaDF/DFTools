package dfutils.colorcodes;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import dfutils.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Locale;

/**
 * NOTE: This class is simply copy-pasted from Minecraft's FontRenderer class.
 * This is done to allow for color code blending.
 */
@ParametersAreNonnullByDefault
public class FontRendererOverride extends FontRenderer implements IResourceManagerReloadListener {
    /**
     * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for
     * drop shadows.
     */
    private final int[] colorCode = new int[32];
    
    /**
     * Used for color code blending.
     */
    private int totalRColor = 0;
    private int totalBColor = 0;
    private int totalGColor = 0;
    private int alphaColor = 0;
    private int colorCodeCount = 0;
    
    private int baseAlpha;
    
    private boolean doPreviewColor = false;
    
    private boolean randomStyle;
    private boolean boldStyle;
    private boolean italicStyle;
    private boolean underlineStyle;
    private boolean strikethroughStyle;
    
    public FontRendererOverride(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
        super(gameSettingsIn, location,textureManagerIn, unicode);
        super.onResourceManagerReload(null);
    
        for (int colorCodeIndex = 0; colorCodeIndex < 32; colorCodeIndex++) {
            
            int j = (colorCodeIndex >> 3 & 1) * 85;
            int k = (colorCodeIndex >> 2 & 1) * 170 + j;
            int l = (colorCodeIndex >> 1 & 1) * 170 + j;
            int i1 = (colorCodeIndex & 1) * 170 + j;
        
            if (colorCodeIndex == 6) {
                k += 85;
            }
        
            if (gameSettingsIn.anaglyph) {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }
        
            if (colorCodeIndex >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }
        
            colorCode[colorCodeIndex] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }
    }
    
    /**
     * Render the given char
     */
    private float renderChar(char renderChar, boolean italic) {
        if (renderChar == 160) {
            return 4.0F; // forge: display nbsp as space. MC-2595
        }
        
        if (renderChar == ' ') {
            return 4.0F;
        } else {
            int charIndex = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(renderChar);
            return charIndex != -1 && !super.getUnicodeFlag() ? super.renderDefaultChar(charIndex, italic) : super.renderUnicodeChar(renderChar, italic);
        }
    }
    
    /**
     * Draws the specified string with a shadow.
     */
    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiChat && color == 14737632) {
            doPreviewColor = true;
        }
        
        int returnInt = this.drawString(text, x, y, color, true);
        doPreviewColor = false;
        
        return returnInt;
    }
    
    /**
     * Draws the specified string.
     */
    @Override
    public int drawString(String text, int x, int y, int color) {
        return this.drawString(text, x, y, color, false);
    }
    
    /**
     * Draws the specified string.
     */
    @Override
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        super.enableAlpha();
        resetStyles();
        int textXPos;
        
        if (dropShadow) {
            textXPos = renderString(text, x + 1.0F, y + 1.0F, color, true);
            textXPos = Math.max(textXPos, renderString(text, x, y, color, false));
        } else {
            textXPos = renderString(text, x, y, color, false);
        }
        
        return textXPos;
    }
    
    /**
     * Apply Unicode Bidirectional Algorithm to string and return a new possibly reordered string for visual rendering.
     */
    private String bidirectionalReorder(String text) {
        try {
            Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (ArabicShapingException exception) {
            return text;
        }
    }
    
    /**
     * Reset all style flag fields in the class to false; called at the start of string rendering
     */
    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }
    
    private void resetColor() {
        colorCodeCount = 0;
        
        totalRColor = 0;
        totalGColor = 0;
        totalBColor = 0;
    }
    
    private void addColor(int r, int g, int b) {
        colorCodeCount++;
        
        totalRColor += r;
        totalGColor += g;
        totalBColor += b;
    }
    
    private void updateColor() {
        setColor(((float) totalRColor / colorCodeCount) / 255.0f, ((float) totalGColor / colorCodeCount) / 255.0f, ((float) totalBColor / colorCodeCount) / 255.0f,
                MathUtils.lerp(alphaColor, 0, 100, 0, baseAlpha) / 255.0f);
    }
    
    /**
     * Render a single line string at the current (posX,posY) and update posX
     */
    private void renderStringAtPos(String text, boolean textShadow) {
        
        boolean resetTextColor = true;
        
        for (int stringIndex = 0; stringIndex < text.length(); stringIndex++) {
            char stringChar = text.charAt(stringIndex);
            
            //If the character is a color character and it is not the end of the text String,
            //set the text color.
            if (stringChar == 167 && stringIndex + 1 < text.length()) {
                
                int colorCodeId = "0123456789abcdefklmnor".indexOf(String.valueOf(text.charAt(stringIndex + 1)).toLowerCase(Locale.ROOT).charAt(0));
                
                if (colorCodeId < 16) {
                    if (resetTextColor) {
                        resetTextColor = false;
                        resetColor();
                    }
                    
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    
                    if (colorCodeId < 0) {
                        colorCodeId = 15;
                    }
                    
                    if (textShadow) {
                        colorCodeId += 16;
                    }
                    
                    int colorCodeColor = this.colorCode[colorCodeId];
                    addColor(colorCodeColor >> 16, colorCodeColor >> 8 & 255, colorCodeColor & 255);
                    updateColor();
    
                    //Color Code: &k
                } else if (colorCodeId == 16) {
                    this.randomStyle = true;
    
                    //Color Code: &l
                } else if (colorCodeId == 17) {
                    this.boldStyle = true;
    
                    //Color Code: &m
                } else if (colorCodeId == 18) {
                    this.strikethroughStyle = true;
    
                    //Color Code: &n
                } else if (colorCodeId == 19) {
                    this.underlineStyle = true;
    
                    //Color Code: &o
                } else if (colorCodeId == 20) {
                    this.italicStyle = true;
                    
                    //Color Code: &r
                } else {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    updateColor();
                }
                
                stringIndex++;
            } else {
                resetTextColor = true;
                int stringCharIndex = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(stringChar);
                
                if (this.randomStyle && stringCharIndex != -1) {
                    int stringCharWidth = this.getCharWidth(stringChar);
                    char randomChar;
                    
                    //Randomly chooses characters until a character of the same width is found.
                    do {
                        stringCharIndex = super.fontRandom.nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".length());
                        randomChar = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".charAt(stringCharIndex);
                    } while (stringCharWidth != this.getCharWidth(randomChar));
                    
                    stringChar = randomChar;
                }
                
                float charShadowOffset = stringCharIndex == -1 || super.getUnicodeFlag() ? 0.5f : 1f;
                boolean doCharShadow = (stringChar == 0 || stringCharIndex == -1 || super.getUnicodeFlag()) && textShadow;
                
                if (doCharShadow) {
                    super.posX -= charShadowOffset;
                    super.posY -= charShadowOffset;
                }
                
                float renderOffset = renderChar(stringChar, italicStyle);
                
                if (doCharShadow) {
                    super.posX += charShadowOffset;
                    super.posY += charShadowOffset;
                }
                
                if (boldStyle) {
                    super.posX += charShadowOffset;
                    
                    if (doCharShadow) {
                        super.posX -= charShadowOffset;
                        super.posY -= charShadowOffset;
                    }
                    
                    this.renderChar(stringChar, italicStyle);
                    super.posX -= charShadowOffset;
                    
                    if (doCharShadow) {
                        super.posX += charShadowOffset;
                        super.posY += charShadowOffset;
                    }
                    
                    renderOffset++;
                }
                doDraw(renderOffset);
            }
        }
    }
    
    @Override
    protected void doDraw(float renderOffset) {
        if (strikethroughStyle) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            
            GlStateManager.disableTexture2D();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
            
            bufferBuilder.pos(super.posX, super.posY + (float) (super.FONT_HEIGHT / 2), 0.0D).endVertex();
            bufferBuilder.pos(super.posX + renderOffset, super.posY + (float) (super.FONT_HEIGHT / 2), 0.0D).endVertex();
            bufferBuilder.pos(super.posX + renderOffset, super.posY + (float) (super.FONT_HEIGHT / 2) - 1.0F, 0.0D).endVertex();
            bufferBuilder.pos(super.posX, super.posY + (float) (super.FONT_HEIGHT / 2) - 1.0F, 0.0D).endVertex();
            
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }
        
        if (underlineStyle) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            
            GlStateManager.disableTexture2D();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
            int underlineOffset = underlineStyle ? -1 : 0;
            
            bufferBuilder.pos(super.posX + underlineOffset, super.posY + super.FONT_HEIGHT, 0.0D).endVertex();
            bufferBuilder.pos(super.posX + renderOffset, super.posY + super.FONT_HEIGHT, 0.0D).endVertex();
            bufferBuilder.pos(super.posX + renderOffset, super.posY + super.FONT_HEIGHT - 1.0F, 0.0D).endVertex();
            bufferBuilder.pos(super.posX + underlineOffset, super.posY + super.FONT_HEIGHT - 1.0F, 0.0D).endVertex();
            
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }
        
        super.posX += renderOffset;
    }
    
    /**
     * Render string either left or right aligned depending on bidiFlag
     */
    private void renderStringAligned(String text, int textX, int textY, int width, int color) {
        if (super.getBidiFlag()) {
            int stringWidth = super.getStringWidth(bidirectionalReorder(text));
            textX = textX + width - stringWidth;
        }
        
        renderString(text, textX, textY, color, false);
    }
    
    /**
     * Render single line string by setting GL color, current (posX,posY), and calling renderStringAtPos()
     */
    private int renderString(String text, float x, float y, int color, boolean dropShadow) {
        if (super.getBidiFlag()) {
            text = bidirectionalReorder(text);
        }
    
        if ((color & -67108864) == 0) {
            color |= -16777216;
        }
    
        if (dropShadow) {
            color = (color & 16579836) >> 2 | color & -16777216;
        }
    
        colorCodeCount = 1;
        totalRColor = color >> 16 & 255;
        totalGColor = color & 255;
        totalBColor = color >> 8 & 255;
    
        baseAlpha = color >> 24 & 255;
        alphaColor = 100;
    
        updateColor();
        super.posX = x;
        super.posY = y;
        renderStringAtPos(text, dropShadow);
        return (int) super.posX;
    }
    
    /**
     * Remove all newline characters from the end of the string
     */
    private String trimStringNewline(String text) {
        while (text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
        }
        
        return text;
    }
    
    /**
     * Splits and draws a String with wordwrap (maximum length is parameter k)
     */
    @Override
    public void drawSplitString(String text, int textX, int textY, int wrapWidth, int color) {
        resetStyles();
        text = trimStringNewline(text);
        renderSplitString(text, textX, textY, wrapWidth, color);
    }
    
    /**
     * Perform actual work of rendering a multi-line string with wordwrap and with darker drop shadow color if flag is
     * set
     */
    private void renderSplitString(String text, int textX, int textY, int wrapWidth, int color) {
        for (String textLine : super.listFormattedStringToWidth(text, wrapWidth)) {
            renderStringAligned(textLine, textX, textY, wrapWidth, color);
            textY += super.FONT_HEIGHT;
        }
    }
}