package net.mctournaments.gameapi.arena;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Builder for {@link Arena}.
 *
 * @author Desetude
 */
public class ArenaBuilder {

    private BiFunction<Player, Integer, Location> playerSpawner;
    private Supplier<Location> spectatorSpawn;

    /**
     * @return the {@link Arena} created with set variables.
     */
    public Arena build() {
        Preconditions.checkNotNull(this.playerSpawner);
        Preconditions.checkNotNull(this.spectatorSpawn);
        return new Arena(this);
    }

    public BiFunction<Player, Integer, Location> playerSpawner() {
        return this.playerSpawner;
    }

    public Supplier<Location> spectatorSpawn() {
        return this.spectatorSpawn;
    }

    public ArenaBuilder playerSpawner(BiFunction<Player, Integer, Location> playerSpawner) {
        this.playerSpawner = playerSpawner;
        return this;
    }

    public ArenaBuilder spectatorSpawn(Supplier<Location> spectatorSpawn) {
        this.spectatorSpawn = spectatorSpawn;
        return this;
    }

}
