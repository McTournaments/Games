package net.mctournaments.gameapi.team.teamOptions;

/**
 * Builder for {@link TeamOptions}.
 *
 * @author Desetude
 */
public class TeamOptionsBuilder {

    private boolean invisibleFriendlyVisible;
    private boolean friendlyFire;

    TeamOptionsBuilder() {
        this.invisibleFriendlyVisible = true;
        this.friendlyFire = false;
    }

    /**
     * @return the {@link TeamOptions} created with set variables.
     */
    public TeamOptions build() {
        return new TeamOptions(this);
    }

    public TeamOptionsBuilder invisibleFriendlyVisible(boolean invisibleFriendlyVisible) {
        this.invisibleFriendlyVisible = invisibleFriendlyVisible;
        return this;
    }

    public TeamOptionsBuilder friendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
        return this;
    }

    public boolean invisibleFriendlyVisible() {
        return this.invisibleFriendlyVisible;
    }

    public boolean friendlyFire() {
        return this.friendlyFire;
    }

}
