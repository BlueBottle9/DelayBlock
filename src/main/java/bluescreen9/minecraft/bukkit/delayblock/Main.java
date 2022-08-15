package bluescreen9.minecraft.bukkit.delayblock;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import bluescreen9.minecraft.bukkit.lang.Lang;

public class Main extends JavaPlugin{

	protected static Plugin DelayBlock;
	protected static Lang Language;
	
			@Override
			public void onEnable() {
					DelayBlock = this;
					Language = new Lang(DelayBlock);
					Language.copyDeafultLangFile();
					Language.loadLanguages();
					getCommand("delay-block").setExecutor(new DelayBlockCommand());
					getCommand("delay-block").setTabCompleter(new DelayBlockCommand());
			}
			
}
