package net.mctournaments.gameapi.events.player;

import static com.google.common.base.Preconditions.checkNotNull;

import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.events.MinigameEvent;
import org.bukkit.entity.Player;

/**
 * A {@link MinigameEvent} which also takes in a {@link Player}.
 *
 * @author Desetude
 */
public abstract class PlayerMinigameEvent extends MinigameEvent {

    private final Player player;

    protected PlayerMinigameEvent(Minigame<?> minigame, Player player) {
        super(minigame);
        this.player = checkNotNull(player);
    }

    public Player getPlayer() {
        return this.player;
    }

}
