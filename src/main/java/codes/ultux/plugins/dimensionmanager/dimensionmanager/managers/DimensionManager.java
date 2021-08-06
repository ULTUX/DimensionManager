package codes.ultux.plugins.dimensionmanager.dimensionmanager.managers;

import codes.ultux.plugins.dimensionmanager.dimensionmanager.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public abstract class DimensionManager implements Listener {
    final Main instance;
    World defaultWorld;
    /**
     * Current state of this dimension
     */
    boolean state = true;

    protected DimensionManager(Main instance) {
        this.instance = instance;
        try {
            defaultWorld = Bukkit.getWorlds().get(0);
        } catch (IndexOutOfBoundsException e){
            instance.getLogger().warning("Could not locate default world, was it generated?");
        }
    }

    /**
     * Toggle dimension.
     * @return True if dimension was enabled or false if disabled.
     */
    public boolean toggle(){
        state = !state;
        if (!state) {
            movePlayers();
        }
        return state;
    };

    /**
     * Set dimension state (enable or disable).
     * @param isEnabled Desired state of dimension.
     */
    public void setState(boolean isEnabled){
        state = isEnabled;
        if (!state) {
            movePlayers();
        }
    };

    /**
     * Get state of dimension.
     * @return Whether the dimension is enabled or disabled.
     */
    public boolean getState(){return state;}

    abstract void movePlayers();


    protected void teleportAllPlayersToHome(List<Player> players, World defWorld){
        players.forEach(player -> {
            Location playerTeleportLocation = player.getBedSpawnLocation() != null ?
                    player.getBedSpawnLocation() : defWorld.getSpawnLocation();
            player.teleport(playerTeleportLocation);
            player.sendTitle(ChatColor.RED+"Teleportowano cię", ChatColor.GRAY+"Świat w ktorym się znajdowałeś został wyłączony.",
                    20, 40, 10);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.1f);
        });
    }

}
