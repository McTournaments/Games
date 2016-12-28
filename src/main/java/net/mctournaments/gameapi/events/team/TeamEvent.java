package net.mctournaments.gameapi.events.team;

import com.google.common.base.Preconditions;
import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.events.MinigameEvent;
import net.mctournaments.gameapi.team.MinigameTeam;

/**
 * A {@link MinigameEvent} which includes a team.
 *
 * @author Desetude
 */
public abstract class TeamEvent extends MinigameEvent {

    private final MinigameTeam team;

    public TeamEvent(Minigame<?> minigame, MinigameTeam team) {
        super(minigame);
        this.team = Preconditions.checkNotNull(team);
    }

    public MinigameTeam getTeam() {
        return this.team;
    }

}
