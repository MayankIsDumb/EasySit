package bl4ckscor3.mod.sit;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.autoconfig.AutoConfig;
//? if >=26.1 {
/*import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import me.shedaniel.autoconfig.gui.registry.DefaultGuiRegistryAccess;
*///?} else {
import net.minecraft.client.gui.screens.Screen;
//?}

public class ModMenuIntegration implements ModMenuApi
{
	@Override
	@SuppressWarnings("removal")
	public ConfigScreenFactory<?> getModConfigScreenFactory()
	{
		//? if >=26.1 {
		/*return parent -> {
			ConfigManager<SitConfig> manager = (ConfigManager<SitConfig>) AutoConfig.getConfigHolder(SitConfig.class);
			ConfigScreenProvider<SitConfig> provider = new ConfigScreenProvider<>(manager, new DefaultGuiRegistryAccess(), parent);
			return provider.get();
		};
		*///?} else {
		return parent -> AutoConfig.getConfigScreen(SitConfig.class, parent).get();
		//?}
	}
}
