package net.mctournaments.gameapi.events.minigame;

import com.google.common.base.Preconditions;
import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.events.MinigameEvent;
import net.mctournaments.gameapi.state.MinigameState;
import org.bukkit.event.HandlerList;

/**
 * A {@link MinigameEvent} which is fired when a {@link Minigame} switching state.
 *
 * @author Desetude
 */
public class MinigameSwitchStateEvent extends MinigameEvent {

    private static final HandlerList handlers = new HandlerList();

    private final MinigameState previousState;
    private final MinigameState newState;

    protected MinigameSwitchStateEvent(Minigame<?> minigame, MinigameState previousState, MinigameState newState) {
        super(minigame);
        this.previousState = Preconditions.checkNotNull(previousState);
        this.newState = Preconditions.checkNotNull(newState);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
