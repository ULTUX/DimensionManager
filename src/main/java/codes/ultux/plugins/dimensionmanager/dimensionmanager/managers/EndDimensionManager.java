package codes.ultux.plugins.dimensionmanager.dimensionmanager.managers;

import codes.ultux.plugins.dimensionmanager.dimensionmanager.Main;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EndDimensionManager extends DimensionManager{
    World endWorld;
    public EndDimensionManager(Main instance) {
        super(instance);
        try {
            endWorld = instance.getServer().getWorlds().get(2);
        }
        catch (IndexOutOfBoundsException e) {
            instance.getLogger().warning("Could not locate nether world, was it generated?");
        }
    }

    @Override
    void movePlayers() {
        try {
            teleportAllPlayersToHome(endWorld.getPlayers(), defaultWorld);
        } catch (IndexOutOfBoundsException e){
            instance.getLogger().warning("Could not locate end world, was it generated?");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (state) return;
        Location to = event.getTo();
        boolean isToEnd = to != null && to.getWorld() != null && to.getWorld().equals(endWorld);
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) || isToEnd) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED+"End został wyłączony!");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
            event.getPlayer().damage(0.5);

        }
    }
}
