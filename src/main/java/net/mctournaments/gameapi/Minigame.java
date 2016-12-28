package net.mctournaments.gameapi;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.Lists;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Sets;
import net.mctournaments.gameapi.arena.Arena;
import net.mctournaments.gameapi.components.Component;
import net.mctournaments.gameapi.config.MinigameConfig;
import net.mctournaments.gameapi.events.minigame.MinigameDestroyEvent;
import net.mctournaments.gameapi.events.player.PlayerJoinGameEvent;
import net.mctournaments.gameapi.events.player.PlayerLeaveGameEvent;
import net.mctournaments.gameapi.events.player.PlayerStartSpectatingEvent;
import net.mctournaments.gameapi.events.player.PlayerStopSpectatingEvent;
import net.mctournaments.gameapi.listeners.DamageListener;
import net.mctournaments.gameapi.listeners.PlayerListener;
import net.mctournaments.gameapi.state.IdleState;
import net.mctournaments.gameapi.state.MinigameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a bukkit minigame including players and spectator.
 *
 * @param <T> plugin that owns {@code this} {@link Minigame}
 * @author Desetude
 */
public class Minigame<T extends Plugin> {

    private final T plugin;
    private final ClassToInstanceMap<Component> components;
    private final MinigameConfig config;
    private final Set<Listener> listeners;
    private final List<Player> players;
    private final Set<Player> spectators;
    private final Arena arena;
    private final BukkitTask stateTimer;
    private MinigameState state;

    /**
     * @param plugin to own {@code this} {@link Minigame}
     * @param arena  for {@code this} {@link Minigame}
     * @param config for {@code this} {@link Minigame}
     */
    public Minigame(T plugin, Arena arena, MinigameConfig config) {
        this.plugin = checkNotNull(plugin);
        this.components = MutableClassToInstanceMap.create();
        this.config = checkNotNull(config);
        this.arena = checkNotNull(arena);
        this.players = Lists.newArrayList();
        this.spectators = Sets.newHashSet();
        this.state = new IdleState();

        this.listeners = Sets.newHashSet(
                new PlayerListener(this),
                new DamageListener(this)
        );

        this.stateTimer = this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, () -> this.state.update(), 20L, 20L);
    }

    /**
     * Removes all players and spectators from the game.
     */
    public void clear() {
        new ArrayList<>(this.players).forEach(this::removePlayer);
        new ArrayList<>(this.spectators).forEach(this::removePlayer);
    }

    /**
     * Destroys {@code this} {@link Minigame} by calling removing all listeners and calling {@link MinigameDestroyEvent}.
     */
    public void destroy() {
        this.clear();
        this.stateTimer.cancel();

        Bukkit.getPluginManager().callEvent(new MinigameDestroyEvent(this));
        this.listeners.forEach(HandlerList::unregisterAll);
        this.listeners.clear();
    }

    /**
     * @param component to check
     * @return whether
     */
    public boolean hasComponent(Class<? extends Component> component) {
        return this.components.containsKey(component);
    }

    /**
     * @param componentClass to get component instance from
     * @return {@link Optional} instance of {@link Component} of specified {@code class}
     */
    @SuppressWarnings("unchecked")
    public <S extends Component> Optional<S> getComponent(Class<S> componentClass) {
        S component = this.components.getInstance(checkNotNull(componentClass));
        return Optional.ofNullable(component);
    }

    /**
     * @param componentClass {@code class} of component
     * @param component      instance to set of type of componentClass
     * @return previous value associated with key or {@code null} if no such key used to exist
     */
    public <S extends Component> Component setComponent(Class<S> componentClass, S component) {
        checkNotNull(component).validate();
        return this.components.putInstance(checkNotNull(componentClass), component);
    }

    /**
     * @return unmodifiable set of {@link Player} that playing in {@code this} {@link Minigame}.
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    /**
     * @return unmodifiable set of {@link Player} that spectating in {@code this} {@link Minigame}.
     */
    public Set<Player> getSpectators() {
        return Collections.unmodifiableSet(this.spectators);
    }

    /**
     * @param player to check
     * @return whether the specified {@link Player} is playing in {@code this} {@link Minigame}.
     */
    public boolean isPlayer(Player player) {
        return this.players.contains(checkNotNull(player));
    }

    /**
     * @param uuid to check
     * @return whether the specified uuid represents a {@link Player} that is playing in {@code this} {@link Minigame}.
     */
    public boolean isPlayer(UUID uuid) {
        checkNotNull(uuid);
        return this.players.stream().anyMatch(player -> player.getUniqueId().equals(uuid));
    }

    /**
     * @param uuid to check
     * @return whether the specified uuid represents a {@link Player} that is spectating in {@code this} {@link Minigame}.
     */
    public boolean isSpectator(UUID uuid) {
        checkNotNull(uuid);
        return this.spectators.stream().anyMatch(player -> player.getUniqueId().equals(uuid));
    }

    /**
     * @param player to check
     * @return whether the specified {@link Player} is spectating in {@code this} {@link Minigame}.
     */
    public boolean isSpectator(Player player) {
        return this.spectators.contains(checkNotNull(player));
    }

    /**
     * Adds a player to the {@link Set} of {@link Player}s currently playing and
     * also removes the player from the spectator {@link Set} if they are in it.
     *
     * @param player to add
     */
    public void addPlayer(Player player) {
        PlayerJoinGameEvent event = new PlayerJoinGameEvent(this, checkNotNull(player));
        this.plugin.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            this.spectators.remove(player);
            this.players.add(player);
        }

    }

    /**
     * @param player to remove from {@code this} {@link Minigame}'s players list
     * @return if the player was successfully removed
     */
    public boolean removePlayer(Player player) {
        if (this.players.remove(checkNotNull(player))) {
            this.plugin.getServer().getPluginManager().callEvent(new PlayerLeaveGameEvent(this, player));
            return true;
        }

        return false;
    }

    /**
     * Adds a player to the {@link Set} of {@link Player} currently spectating and
     * also removes the player from the players {@link Set} if they are in it.
     *
     * @param player to add
     */
    public void addSpectator(Player player) {
        PlayerStartSpectatingEvent event = new PlayerStartSpectatingEvent(this, checkNotNull(player));
        this.plugin.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            this.removePlayer(player);
            this.spectators.add(player);
        }
    }

    /**
     * @param player to remove from {@code this} {@link Minigame}'s spectators list
     * @return if a player was removed
     */
    public boolean removeSpectator(Player player) {
        if (this.spectators.remove(checkNotNull(player))) {
            this.plugin.getServer().getPluginManager().callEvent(new PlayerStopSpectatingEvent(this, player));
            return true;
        }

        return false;
    }

    /**
     * @return owning {@link Plugin} of {@code this} {@link Minigame}
     */
    public T getPlugin() {
        return this.plugin;
    }

    /**
     * @return {@link MinigameConfig} which includes settings/options for {@code this} {@link Minigame}
     */
    public MinigameConfig getConfig() {
        return this.config;
    }

    /**
     * Registers {@link Listener} which is automatically unregistered when {@code this} {@link Minigame} is destroyed.
     *
     * @param listener to register
     */
    public void registerListener(Listener listener) {
        this.plugin.getServer().getPluginManager().registerEvents(checkNotNull(listener), this.plugin);
        this.listeners.add(listener);
    }

    /**
     * Adds a {@link Listener} to be unregistered when {@code this} {@link Minigame} is destroyed.
     *
     * @param listener to add
     */
    public void addListener(Listener listener) {
        this.listeners.add(checkNotNull(listener));
    }

    /**
     * @param listener to unregister
     */
    public void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(checkNotNull(listener));
        this.listeners.remove(listener);
    }

    /**
     * @return unmodifiable {@link Set} of {@link Listener}s for {@code this} {@link Minigame}
     */
    public Set<Listener> getListeners() {
        return Collections.unmodifiableSet(this.listeners);
    }

    /**
     * @return current {@link MinigameState} for {@code this} {@link Minigame}
     */
    public MinigameState getState() {
        return this.state;
    }

    /**
     * Calls the previous state's {@link MinigameState#onEnd()}, then sets {@code this} {@link Minigame}'s
     * {@link MinigameState} to the specified {@link MinigameState} and finally calls {@link MinigameState#onStart()}
     * on the new {@link MinigameState}.
     *
     * @param state to set
     */
    public void setState(MinigameState state) {
        checkNotNull(state);

        this.state.onEnd();
        this.state = state;
        this.state.onStart();
    }

    /**
     * @return active arena for {@code this} {@link Minigame}
     */
    public Arena getArena() {
        return this.arena;
    }

}
