package net.mctournaments.gameapi.team;

import com.google.common.base.Preconditions;
import net.mctournaments.gameapi.team.teamOptions.TeamOptions;

/**
 * Builder for {@link MinigameTeam}.
 *
 * @author Desetude
 */
public class MinigameTeamBuilder {

    private String name;
    private String chatPrefix;
    private TeamOptions teamOptions;

    public MinigameTeamBuilder() {
        this.chatPrefix = "";
        this.teamOptions = TeamOptions.builder().build();
    }

    /**
     * @return the {@link MinigameTeam} created with set variables.
     */
    public MinigameTeam build() {
        Preconditions.checkNotNull(this.name);
        Preconditions.checkNotNull(this.teamOptions);
        return new MinigameTeam(this);
    }

    public MinigameTeamBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MinigameTeamBuilder chatPrefix(String chatPrefix) {
        this.chatPrefix = chatPrefix;
        return this;
    }

    public MinigameTeamBuilder teamOptions(TeamOptions teamOptions) {
        this.teamOptions = teamOptions;
        return this;
    }

    public String name() {
        return this.name;
    }

    public String chatPrefix() {
        return this.chatPrefix;
    }

    public TeamOptions teamOptions() {
        return this.teamOptions;
    }

}
