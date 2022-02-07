package codes.ultux.plugins.dimensionmanager.dimensionmanager.managers;

import codes.ultux.plugins.dimensionmanager.dimensionmanager.Main;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class NetherDimensionManager extends DimensionManager {
    World netherWorld;
    public NetherDimensionManager(Main instance) {
        super(instance);
        try {
            netherWorld = instance.getServer().getWorlds().get(1);
        }
        catch (IndexOutOfBoundsException e){
            instance.getLogger().warning("Could not locate nether world, was it generated?");
        }
    }

    @Override
    void movePlayers() {
        try {
            teleportAllPlayersToHome(netherWorld.getPlayers(), defaultWorld);
        } catch (IndexOutOfBoundsException e){
            instance.getLogger().warning("Could not locate end world, was it generated?");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (state) return;
        Location to = event.getTo();
        boolean isToNether = to != null && to.getWorld() != null && to.getWorld().equals(netherWorld);
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) || isToNether) {
            event.setCancelled(true);
            printMessage(event.getPlayer(), "Nether został wyłączony!");

        }

    }
}
