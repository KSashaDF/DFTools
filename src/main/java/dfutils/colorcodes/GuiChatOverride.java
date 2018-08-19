package dfutils.colorcodes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ITabCompleter;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.annotation.Nullable;
import java.io.IOException;

public class GuiChatOverride extends GuiChat implements ITabCompleter {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    private String historyBuffer = "";
    /**
     * keeps position of which chat message you will select when you press up, (does not increase for duplicated
     * messages sent immediately after each other)
     */
    private int sentHistoryCursor = -1;
    private TabCompleter tabCompleter;
    /** is the text that appears when you press the chat key and the input box appears pre-filled */
    private String defaultInputFieldText;
    
    public GuiChatOverride(String defaultText) {
        defaultInputFieldText = defaultText;
    }
    
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window re-sizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui() {
        
        super.inputField = new GuiTextField(0, fontRenderer, 4, height - 12, width - 4, 12);
        super.inputField.setMaxStringLength(256);
        super.inputField.setEnableBackgroundDrawing(false);
        super.inputField.setFocused(true);
        super.inputField.setText(defaultInputFieldText);
        super.inputField.setCanLoseFocus(false);
    
        Keyboard.enableRepeatEvents(true);
        sentHistoryCursor = mc.ingameGUI.getChatGUI().getSentMessages().size();
        tabCompleter = new GuiChat.ChatTabCompleter(super.inputField);
    }
    
    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen() {
        super.inputField.updateCursorCounter();
    }
    
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        tabCompleter.resetRequested();
        
        if (keyCode == 15) {
            tabCompleter.complete();
        } else {
            tabCompleter.resetDidComplete();
        }
        
        if (keyCode == 1) {
            minecraft.displayGuiScreen(null);
        } else if (keyCode != 28 && keyCode != 156) {
            
            if (keyCode == 200) {
                getSentHistory(-1);
            } else if (keyCode == 208) {
                getSentHistory(1);
            } else if (keyCode == 201) {
                minecraft.ingameGUI.getChatGUI().scroll(minecraft.ingameGUI.getChatGUI().getLineCount() - 1);
            } else if (keyCode == 209) {
                minecraft.ingameGUI.getChatGUI().scroll(-minecraft.ingameGUI.getChatGUI().getLineCount() + 1);
            } else {
                inputField.textboxKeyTyped(typedChar, keyCode);
            }
        } else {
            
            String chatMessage = super.inputField.getText().trim();
            
            if (!chatMessage.isEmpty()) {
                sendChatMessage(chatMessage);
            }
            
            minecraft.displayGuiScreen(null);
        }
    }
    
    /**
     * Handles mouse input.
     */
    @Override
    public void handleMouseInput() throws IOException {
        
        super.handleMouseInput();
        int mouseScroll = Mouse.getEventDWheel();
        
        if (mouseScroll != 0) {
            
            if (mouseScroll > 1) {
                mouseScroll = 1;
            }
            
            if (mouseScroll < -1) {
                mouseScroll = -1;
            }
            
            if (!isShiftKeyDown()) {
                mouseScroll *= 7;
            }
            
            minecraft.ingameGUI.getChatGUI().scroll(mouseScroll);
        }
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        
        if (mouseButton == 0) {
            ITextComponent textComponent = minecraft.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
            
            if (textComponent != null && handleComponentClick(textComponent)) {
                return;
            }
        }
        
        super.inputField.mouseClicked(mouseX, mouseY, mouseButton);
    
        //NOTE: This is taken from the GuiScreen.mouseClicked method! This is because super cannot be called here without
        //causing things to break.
        if (mouseButton == 0) {
            for (int i = 0; i < super.buttonList.size(); i++) {
                GuiButton button = super.buttonList.get(i);
            
                if (button.mousePressed(minecraft, mouseX, mouseY)) {
                    GuiScreenEvent.ActionPerformedEvent.Pre event = new GuiScreenEvent.ActionPerformedEvent.Pre(this, button, super.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) {
                        break;
                    }
                    
                    button = event.getButton();
                    super.selectedButton = button;
                    button.playPressSound(minecraft.getSoundHandler());
                    super.actionPerformed(button);
                    
                    if (this.equals(minecraft.currentScreen)) {
                        MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), super.buttonList));
                    }
                }
            }
        }
    }
    
    /**
     * Sets the text of the chat
     */
    @Override
    protected void setText(String newChatText, boolean shouldOverwrite) {
        
        if (shouldOverwrite) {
            super.inputField.setText(newChatText);
        } else {
            super.inputField.writeText(newChatText);
        }
    }
    
    /**
     * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
     * message from the current cursor position
     */
    @Override
    public void getSentHistory(int messagePosition) {
        
        int offsetMessagePosition = sentHistoryCursor + messagePosition;
        int chatHistoryLength = mc.ingameGUI.getChatGUI().getSentMessages().size();
        offsetMessagePosition = MathHelper.clamp(offsetMessagePosition, 0, chatHistoryLength);
        
        if (offsetMessagePosition != sentHistoryCursor) {
            
            if (offsetMessagePosition == chatHistoryLength) {
                sentHistoryCursor = chatHistoryLength;
                super.inputField.setText(historyBuffer);
            } else {
                
                if (sentHistoryCursor == chatHistoryLength) {
                    historyBuffer = super.inputField.getText();
                }
                
                super.inputField.setText(mc.ingameGUI.getChatGUI().getSentMessages().get(offsetMessagePosition));
                sentHistoryCursor = offsetMessagePosition;
            }
        }
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        
        drawRect(2, height - 14, width - 2, height - 2, Integer.MIN_VALUE);
        super.inputField.drawTextBox();
        ITextComponent textComponent = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        
        if (textComponent != null && textComponent.getStyle().getHoverEvent() != null) {
            handleComponentHover(textComponent, mouseX, mouseY);
        }
    
        //NOTE: This is taken from the GuiScreen.drawScreen method! This is because super cannot be called here without
        //causing things to break.
        for (GuiButton button : super.buttonList) {
            button.drawButton(minecraft, mouseX, mouseY, partialTicks);
        }
    
        for (GuiLabel label : super.labelList) {
            label.drawLabel(minecraft, mouseX, mouseY);
        }
    }
    
    /**
     * Sets the list of tab completions, as long as they were previously requested.
     */
    @Override
    public void setCompletions(String... newCompletions) {
        this.tabCompleter.setCompletions(newCompletions);
    }
    
    @SideOnly(Side.CLIENT)
    public static class ChatTabCompleter extends TabCompleter {
        
        private final Minecraft minecraft = Minecraft.getMinecraft();
        
        public ChatTabCompleter(GuiTextField textField) {
            super(textField, false);
        }
        
        /**
         * Called when tab key pressed. If it's the first time we tried to complete this string, we ask the server
         * for completions. When the server responds, this method gets called again (via setCompletions).
         */
        @Override
        public void complete() {
            super.complete();
            
            if (super.completions.size() > 1) {
                StringBuilder stringBuilder = new StringBuilder();
                
                for (String tabCompletion : super.completions) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(", ");
                    }
                    
                    stringBuilder.append(tabCompletion);
                }
                
                minecraft.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(stringBuilder.toString()), 1);
            }
        }
        
        @Nullable
        public BlockPos getTargetBlockPos() {
            BlockPos blockPos = null;
            
            if (minecraft.objectMouseOver != null && minecraft.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                blockPos = minecraft.objectMouseOver.getBlockPos();
            }
            
            return blockPos;
        }
    }
}