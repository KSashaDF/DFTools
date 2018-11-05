package dfutils.config;

import dfutils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiFactory implements IModGuiFactory {
	
	@Override
	public void initialize(Minecraft minecraft) {
	}
	
	@Override
	public boolean hasConfigGui() {
		return true;
	}
	
	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new GuiConfig(parentScreen, getConfigElements(), Reference.MOD_ID, false, false, "DiamondFire Utilities");
	}
	
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
	
	//Gets all the base categories in the main config screen.
	private List<IConfigElement> getConfigElements() {
		final List<IConfigElement> elements = new ArrayList<>();
		for (String categoryName : ConfigHandler.config.getCategoryNames()) {
			ConfigCategory category = ConfigHandler.config.getCategory(categoryName);
			
			//Adds the category to the list if it is located in the main config screen.
			if (category.parent == null) {
				elements.add(new ConfigElement(category));
			}
		}
		
		return elements;
	}
}
