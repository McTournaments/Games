package net.mctournaments.gameapi.events.team;

import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.team.MinigameTeam;
import org.bukkit.event.HandlerList;

/**
 * A {@link TeamEvent} representing when a team is unregistered from a minigame.
 *
 * @author Desetude
 */
public class TeamUnregisteredEvent extends TeamEvent {

    private static final HandlerList handlers = new HandlerList();

    public TeamUnregisteredEvent(Minigame<?> minigame, MinigameTeam team) {
        super(minigame, team);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
