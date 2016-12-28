package net.mctournaments.gameapi.events.player;

import net.mctournaments.gameapi.Minigame;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * A {@link PlayerMinigameEvent} which is fired when a player leaves a {@link Minigame}.
 *
 * @author Desetude
 */
public class PlayerLeaveGameEvent extends PlayerMinigameEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerLeaveGameEvent(Minigame<?> minigame, Player player) {
        super(minigame, player);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
