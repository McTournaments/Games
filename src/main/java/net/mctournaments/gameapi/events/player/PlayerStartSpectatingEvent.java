package net.mctournaments.gameapi.events.player;

import net.mctournaments.gameapi.Minigame;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * A cancellable {@link PlayerMinigameEvent} which is fired when a player starts spectating a {@link Minigame}.
 *
 * @author Desetude
 */
public class PlayerStartSpectatingEvent extends PlayerMinigameEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    public PlayerStartSpectatingEvent(Minigame<?> minigame, Player player) {
        super(minigame, player);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override public boolean isCancelled() {
        return this.cancelled;
    }

    @Override public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
