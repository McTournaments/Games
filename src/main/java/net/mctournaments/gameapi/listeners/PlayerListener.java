package net.mctournaments.gameapi.listeners;

import com.google.common.base.Preconditions;
import net.mctournaments.gameapi.Minigame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A {@link Listener} for {@link Minigame} which listens for player related {@link Event}.
 *
 * @author Desetude
 */
public class PlayerListener implements Listener {

    private final Minigame<?> minigame;

    public PlayerListener(Minigame<?> minigame) {
        this.minigame = Preconditions.checkNotNull(minigame);
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        if (!this.minigame.isPlayer(player)) {
            return;
        }

        if (!this.minigame.getConfig().getCanBuild().test(this.minigame, player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        if (!this.minigame.isPlayer(player)) {
            return;
        }

        if (!this.minigame.getConfig().getCanDestroy().test(this.minigame, player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerBucketEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        if (!this.minigame.isPlayer(player)) {
            return;
        }

        if (!this.minigame.getConfig().getCanUseBuckets().test(this.minigame, player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(HangingBreakByEntityEvent event) {
        if (event.isCancelled() || !(event.getRemover() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getRemover();
        if (!this.minigame.isPlayer(player)) {
            return;
        }

        if (!this.minigame.getConfig().getCanDestroyHanging().test(this.minigame, player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerDropItemEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        if (!this.minigame.isPlayer(player)) {
            return;
        }

        if (!this.minigame.getConfig().getCanDrop().test(this.minigame, player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerPickupItemEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        if (!this.minigame.isPlayer(player)) {
            return;
        }

        if (!this.minigame.getConfig().getCanPickup().test(this.minigame, player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(FoodLevelChangeEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (!this.minigame.isPlayer(player)) {
            return;
        }

        if (!this.minigame.getConfig().getCanLoseHunger().test(this.minigame, player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        this.minigame.removePlayer(event.getPlayer());
        this.minigame.removeSpectator(event.getPlayer());
    }

}
