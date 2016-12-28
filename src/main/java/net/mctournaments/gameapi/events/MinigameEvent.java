package net.mctournaments.gameapi.events;

import com.google.common.base.Preconditions;
import net.mctournaments.gameapi.Minigame;
import org.bukkit.event.Event;

/**
 * Class for all {@link Minigame} related events.
 *
 * @author Desetude
 */
public abstract class MinigameEvent extends Event {

    private final Minigame<?> minigame;

    protected MinigameEvent(Minigame<?> minigame) {
        this.minigame = Preconditions.checkNotNull(minigame);
    }

    public Minigame<?> getMinigame() {
        return this.minigame;
    }

}
