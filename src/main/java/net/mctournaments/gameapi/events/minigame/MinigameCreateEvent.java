package net.mctournaments.gameapi.events.minigame;

import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.events.MinigameEvent;
import org.bukkit.event.HandlerList;

/**
 * A {@link MinigameEvent} which is fired when a {@link Minigame} being created.
 *
 * @author Desetude
 */
public class MinigameCreateEvent extends MinigameEvent {

    private static final HandlerList handlers = new HandlerList();

    protected MinigameCreateEvent(Minigame<?> minigame) {
        super(minigame);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
