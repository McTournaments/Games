package net.mctournaments.gameapi.team.teamOptions;

/**
 * Builder for {@link TeamOptions}.
 *
 * @author Desetude
 */
public class TeamOptions {

    private final boolean invisibleFriendlyVisible;
    private final boolean friendlyFire;

    TeamOptions(TeamOptionsBuilder builder) {
        this.invisibleFriendlyVisible = builder.invisibleFriendlyVisible();
        this.friendlyFire = builder.friendlyFire();
    }

    /**
     * @return The {@link TeamOptionsBuilder} which is used to create a TeamOptions object.
     */
    public static TeamOptionsBuilder builder() {
        return new TeamOptionsBuilder();
    }

    /**
     * @return whether {@link org.bukkit.entity.Player}s can see other invisible other {@link org.bukkit.entity.Player}s even if they are invisible.
     */
    public boolean invisibleFriendlyVisible() {
        return this.invisibleFriendlyVisible;
    }

    /**
     * @return whether {@link org.bukkit.entity.Player}s can hit other players on their team.
     */
    public boolean friendlyFire() {
        return this.friendlyFire;
    }

}
