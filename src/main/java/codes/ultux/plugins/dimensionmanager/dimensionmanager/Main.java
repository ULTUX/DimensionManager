package codes.ultux.plugins.dimensionmanager.dimensionmanager;

import codes.ultux.plugins.dimensionmanager.dimensionmanager.commands.DimensionManagerCommand;
import codes.ultux.plugins.dimensionmanager.dimensionmanager.managers.EndDimensionManager;
import codes.ultux.plugins.dimensionmanager.dimensionmanager.managers.EndIslandsDimensionManager;
import codes.ultux.plugins.dimensionmanager.dimensionmanager.managers.NetherDimensionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public EndDimensionManager endManager;
    public NetherDimensionManager netherManager;
    public EndIslandsDimensionManager endIslandManager;
    private ConfigLoader configLoader;

    @Override
    public void onEnable() {
        getLogger().info("Loading config data...");
        endManager = new EndDimensionManager(this);
        netherManager = new NetherDimensionManager(this);
        endIslandManager = new EndIslandsDimensionManager(this);

        Bukkit.getPluginManager().registerEvents(netherManager, this);
        Bukkit.getPluginManager().registerEvents(endManager, this);
        Bukkit.getPluginManager().registerEvents(endIslandManager, this);

        configLoader = new ConfigLoader(this);
        configLoader.loadConfig();

        getCommand("dimensionManager").setExecutor(new DimensionManagerCommand(this));



        getLogger().info("Plugin enabled.");

    }

    @Override
    public void onDisable() {
        getLogger().info("Writing config data...");
        configLoader.saveConfig();
        getLogger().info("Plugin disabled.");
    }
}
