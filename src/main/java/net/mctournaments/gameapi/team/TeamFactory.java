package net.mctournaments.gameapi.team;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.events.team.TeamRegisteredEvent;
import net.mctournaments.gameapi.events.team.TeamUnregisteredEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

/**
 * Manages all the {@link MinigameTeam} for {@link Minigame}.
 *
 * @author Desetude
 */
public final class TeamFactory {

    private static final Map<Minigame, TeamFactory> factoryMap;

    static {
        factoryMap = Maps.newHashMap();
    }

    private final Minigame minigame;
    private final Multimap<MinigameTeam, Player> teamMap;
    private final Set<MinigameTeam> teams;

    TeamFactory(Minigame minigame) {
        this.minigame = minigame;
        this.teamMap = HashMultimap.create();
        this.teams = Sets.newHashSet();
    }

    /**
     * @param minigame to get instance of
     * @return the dedicated instance of {@link TeamFactory} for the specified {@link Minigame}.
     */
    public static TeamFactory getInstance(Minigame minigame) {
        return factoryMap.computeIfAbsent(minigame, TeamFactory::new);
    }

    /**
     * @param minigame to test
     * @return whether minigame specified is registered in {@code TeamFactory}
     */
    public static boolean isRegistered(Minigame minigame) {
        return factoryMap.containsKey(minigame);
    }

    /**
     * Registers the specified {@link MinigameTeam} to {@code this} {@link TeamFactory}.
     *
     * @param team to register
     * @return {@code true} if successful
     */
    public boolean registerTeam(MinigameTeam team) {
        this.verifyRegistered();

        if (this.teams.add(team)) {
            this.minigame.getPlugin().getServer().getPluginManager().callEvent(new TeamRegisteredEvent(this.minigame, team));
            return true;
        }

        return false;
    }

    /**
     * Unregisters the specified {@link MinigameTeam} to {@code this} {@link TeamFactory}.
     *
     * @param team to register
     * @return {@code true} if successful
     */
    public boolean unregisterTeam(MinigameTeam team) {
        this.verifyRegistered();

        if (this.teams.remove(team)) {
            this.teamMap.removeAll(team);
            this.minigame.getPlugin().getServer().getPluginManager().callEvent(new TeamUnregisteredEvent(this.minigame, team));
            return true;
        }

        return false;
    }

    /**
     * @param team to test
     * @return whether the specified {@link MinigameTeam} is registered in {@code this} {@link TeamFactory}.
     */
    public boolean isRegistered(MinigameTeam team) {
        this.verifyRegistered();

        return this.teams.contains(team);
    }

    /**
     * @param team   to test
     * @param player to test
     * @return whether specified {@link Player} is on the specified {@link MinigameTeam} for {@code this} {@link TeamFactory}.
     */
    public boolean isPlayerOnTeam(MinigameTeam team, Player player) {
        this.verifyRegistered();

        return this.isRegistered(team) && this.teamMap.containsEntry(team, player);
    }

    /**
     * Unregisters {@code this} {@link TeamFactory}  from the {@code static} {@link TeamFactory} {@link Map}.
     */
    public void unregister() {
        this.verifyRegistered();

        this.teamMap.clear();
        this.teams.clear();
        factoryMap.remove(this.minigame);
    }

    /**
     * @return whether {@code this} {@link TeamFactory} is registered in the {@code static} {@link TeamFactory} {@link Map}.
     */
    public boolean isRegistered() {
        return factoryMap.containsValue(this);
    }

    private void verifyRegistered() {
        Validate.isTrue(this.isRegistered(), "Attempted to do an action with a TeamFactory when it was not registered.");
    }

}
