package net.mctournaments.gameapi.team;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.components.Component;
import net.mctournaments.gameapi.events.team.TeamRegisteredEvent;
import net.mctournaments.gameapi.events.team.TeamUnregisteredEvent;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class TeamComponent extends Component {

    private final Multimap<MinigameTeam, UUID> playerTeams;
    private final Set<MinigameTeam> teams;

    public TeamComponent(Minigame<?> minigame) {
        super(minigame);

        this.setName("teams");

        this.playerTeams = HashMultimap.create();
        this.teams = Sets.newHashSet();
    }

    /**
     * Registers the specified {@link MinigameTeam} to {@code this} {@link TeamComponent}.
     *
     * @param team to register
     * @return {@code true} if successful
     */
    public boolean registerTeam(MinigameTeam team) {
        if (this.teams.add(checkNotNull(team))) {
            this.minigame.getPlugin().getServer().getPluginManager().callEvent(new TeamRegisteredEvent(this.minigame, team));
            return true;
        }

        return false;
    }

    /**
     * Unregisters the specified {@link MinigameTeam} to {@code this} {@link TeamComponent}.
     *
     * @param team to register
     * @return {@code true} if successful
     */
    public boolean unregisterTeam(MinigameTeam team) {
        if (this.teams.remove(checkNotNull(team))) {
            this.playerTeams.removeAll(team);
            this.minigame.getPlugin().getServer().getPluginManager().callEvent(new TeamUnregisteredEvent(this.minigame, team));
            return true;
        }

        return false;
    }

    /**
     * @param team to test
     * @return whether the specified {@link MinigameTeam} is registered in {@code this} {@link TeamComponent}
     */
    public boolean isRegistered(MinigameTeam team) {
        return this.teams.contains(checkNotNull(team));
    }

    /**
     * @param uuid to test
     * @param team to test
     * @return whether the player represented by the specified {@link UUID} is on the specified {@link MinigameTeam}
     * in {@code this} {@link TeamComponent}
     */
    public boolean isPlayerOnTeam(UUID uuid, MinigameTeam team) {
        return this.isRegistered(checkNotNull(team)) && this.playerTeams.containsEntry(team, checkNotNull(uuid));
    }

    /**
     * @param team   to test
     * @param player to test
     * @return whether specified {@link Player} is on the specified {@link MinigameTeam} for {@code this} {@link TeamComponent}.
     */
    public boolean isPlayerOnTeam(Player player, MinigameTeam team) {
        return this.isPlayerOnTeam(checkNotNull(player).getUniqueId(), team);
    }

    /**
     * @param uuid to remove from all {@link MinigameTeam}s player list
     */
    public void removeFromAllTeams(UUID uuid) {
        checkNotNull(uuid);
        this.playerTeams.asMap().entrySet().forEach(entry -> entry.getValue().removeIf(compare -> compare.equals(uuid)));
    }

    /**
     * @param player to remove from all {@link MinigameTeam}s player list
     */
    public void removeFromAllTeams(Player player) {
        this.removeFromAllTeams(checkNotNull(player).getUniqueId());
    }

    /**
     * Removes specified {@link Player} from all teams they are on and adds them to the specified {@link MinigameTeam}
     *
     * @param uuid representing {@link Player} to add
     * @param team to set {@link Player} to
     * @return if method {@link Player} was successfully added to the specified {@link MinigameTeam}
     */
    public boolean setPlayerTeam(UUID uuid, MinigameTeam team) {
        Preconditions.checkState(this.isRegistered(checkNotNull(team)), "Attempted to set player to non-registered team.");
        this.removeFromAllTeams(checkNotNull(uuid));

        return this.playerTeams.put(team, uuid);
    }

    /**
     * Removes specified {@link Player} from all teams they are on and adds them to the specified {@link MinigameTeam}
     *
     * @param player representing {@link Player} to add
     * @param team   to set {@link Player} to
     * @return if method {@link Player} was successfully added to the specified {@link MinigameTeam}
     */
    public boolean setPlayerTeam(Player player, MinigameTeam team) {
        return this.setPlayerTeam(checkNotNull(player).getUniqueId(), team);
    }

}
