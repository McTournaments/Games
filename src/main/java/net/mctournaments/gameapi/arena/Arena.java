package net.mctournaments.gameapi.arena;

import net.mctournaments.bukkit.profile.Profile;
import net.mctournaments.gameapi.Minigame;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Holds information about a {@link Minigame}'s arena.
 *
 * @author Desetude
 */
public class Arena {

    private final BiFunction<Player, Integer, Location> playerSpawner;
    private final Supplier<Location> spectatorSpawn;

    Arena(ArenaBuilder builder) {
        this.playerSpawner = builder.playerSpawner();
        this.spectatorSpawn = builder.spectatorSpawn();
    }

    public static ArenaBuilder builder() {
        return new ArenaBuilder();
    }

    /**
     * @return {@link BiFunction} used to spawn players in a certain locations at game start.
     * {@link Profile} is the Player which is inputted.
     * {@link Integer} is the cycle number of the player for spawning.
     * {@link Location} is the outputted location of where the player is teleported at the start of the game.
     */
    public BiFunction<Player, Integer, Location> getPlayerSpawner() {
        return this.playerSpawner;
    }

    /**
     * @return {@link Supplier} for {@link Location} for spectators to spawn.
     */
    public Supplier<Location> spectatorSpawn() {
        return this.spectatorSpawn;
    }

}
