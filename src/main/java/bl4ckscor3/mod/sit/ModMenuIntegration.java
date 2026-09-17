package bl4ckscor3.mod.sit;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import me.shedaniel.autoconfig.gui.registry.DefaultGuiRegistryAccess;

public class ModMenuIntegration implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory()
	{
		return parent -> {
			ConfigManager<SitConfig> manager = (ConfigManager<SitConfig>) AutoConfig.getConfigHolder(SitConfig.class);
			ConfigScreenProvider<SitConfig> provider = new ConfigScreenProvider<>(manager, new DefaultGuiRegistryAccess(), parent);
			return provider.get();
		};
	}
}
