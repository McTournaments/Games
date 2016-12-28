package net.mctournaments.gameapi.listeners;

import com.google.common.base.Preconditions;
import net.mctournaments.gameapi.Minigame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * A {@link Listener} for {@link Minigame} which listens for damage related {@link Event}.
 *
 * @author Desetude
 */
public class DamageListener implements Listener {

    private final Minigame<?> minigame;

    public DamageListener(Minigame<?> minigame) {
        this.minigame = Preconditions.checkNotNull(minigame);
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        if (!this.minigame.isPlayer(player)) {
            return;
        }

        if (event.getEntity() instanceof Player) {
            if (!this.minigame.getConfig().getCanPvP().test(this.minigame, player)) {
                event.setCancelled(true);
            }
        } else {
            if (!this.minigame.getConfig().getCanPvE().test(this.minigame, player)) {
                event.setCancelled(true);
            }
        }
    }

}
