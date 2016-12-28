package net.mctournaments.gameapi.team;

import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.team.teamOptions.TeamOptions;

/**
 * Represents a team/group of players in a {@link Minigame}.
 *
 * @author Desetude
 */
public class MinigameTeam {

    private final String name;
    private final String chatPrefix;
    private final TeamOptions teamOptions;

    public MinigameTeam(MinigameTeamBuilder builder) {
        this.name = builder.name();
        this.chatPrefix = builder.chatPrefix();
        this.teamOptions = builder.teamOptions();
    }

    /**
     * @return The {@link MinigameTeamBuilder} which is used to create a MinigameTeam object.
     */
    public static MinigameTeamBuilder builder() {
        return new MinigameTeamBuilder();
    }

    /**
     * @return name of the team
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return prefix for all players in {@code this} {@link MinigameTeam} will have when they speak in chat.
     */
    public String getChatPrefix() {
        return this.chatPrefix;
    }

    /**
     * @return options for {@code this} {@link MinigameTeam}.
     */
    public TeamOptions getTeamOptions() {
        return this.teamOptions;
    }

}
