package codes.ultux.plugins.dimensionmanager.dimensionmanager;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigLoader {
    private final Main instance;

    public ConfigLoader(Main instance) {
        this.instance = instance;
    }

    public void loadConfig() {
        instance.saveDefaultConfig();
        instance.netherManager.setState(instance.getConfig().getBoolean("enable-nether"));
        instance.endManager.setState(instance.getConfig().getBoolean("enable-end"));
        instance.endIslandManager.setState(instance.getConfig().getBoolean("enable-end-islands"));
    }

    public void saveConfig() {
        instance.getConfig().set("enable-nether", instance.netherManager.getState());
        instance.getConfig().set("enable-end", instance.endManager.getState());
        instance.getConfig().set("enable-end-islands", instance.endIslandManager.getState());
        instance.saveConfig();
    }
}
