package codes.ultux.plugins.dimensionmanager.dimensionmanager.managers;

import codes.ultux.plugins.dimensionmanager.dimensionmanager.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import java.util.List;
import java.util.stream.Collectors;

public class EndIslandsDimensionManager extends DimensionManager{
    World endWorld;

    public EndIslandsDimensionManager(Main instance) {
        super(instance);
        try {
            endWorld = instance.getServer().getWorlds().get(2);
        }
        catch (IndexOutOfBoundsException e){
            instance.getLogger().warning("Could not locate end world, was it generated?");
        }
    }

    @Override
    public boolean toggle() {
        super.toggle();
        if (!state){
            createBorder();
        }
        else {
            resetBorder();
        }
        return state;
    }

    @Override
    public void setState(boolean isEnabled) {
        super.setState(isEnabled);
        if (!state){
            createBorder();
        }
        else {
            resetBorder();
        }
    }

    @Override
    void movePlayers() {
            World defaultWorld = instance.getServer().getWorlds().get(0);
            List<Player> players = endWorld.getPlayers().stream()
                    .filter(player -> player.getLocation().distance(new Location(endWorld, 0, 0, 0)) > 1000)
                    .collect(Collectors.toList());
            teleportAllPlayersToHome(players, defaultWorld);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (state) return;
        Location to = event.getTo();
        boolean isToEndIsland = to != null && to.getWorld() != null && to.getWorld().equals(endWorld)
                && to.distance(new Location(to.getWorld(), 0, 0, 0)) > 1000;
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) || isToEndIsland) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED+"Wyspy endu zostały wyłączone!");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
            event.getPlayer().damage(0.5);

        }

    }
    private void createBorder() {
        WorldBorder border = endWorld.getWorldBorder();
        border.setSize(2000);
        border.setCenter(0, 0);

    }
    private void resetBorder() {
        WorldBorder border = endWorld.getWorldBorder();
        border.reset();
    }
}
