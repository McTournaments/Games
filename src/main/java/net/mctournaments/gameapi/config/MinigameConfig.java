package net.mctournaments.gameapi.config;

import net.mctournaments.gameapi.Minigame;
import org.bukkit.entity.Player;

import java.util.function.BiPredicate;

/**
 * Holds settings for a {@link Minigame}.
 *
 * @author Desetude
 */
public class MinigameConfig {

    private final String name;
    private final String formattedName;
    private final BiPredicate<Minigame<?>, Player> canBuild;
    private final BiPredicate<Minigame<?>, Player> canDestroy;
    private final BiPredicate<Minigame<?>, Player> canUseBuckets;
    private final BiPredicate<Minigame<?>, Player> canDestroyHanging;
    private final BiPredicate<Minigame<?>, Player> canPvP;
    private final BiPredicate<Minigame<?>, Player> canPvE;
    private final BiPredicate<Minigame<?>, Player> canDrop;
    private final BiPredicate<Minigame<?>, Player> canPickup;
    private final BiPredicate<Minigame<?>, Player> canLoseHunger;

    MinigameConfig(MinigameConfigBuilder builder) {
        this.name = builder.name();
        this.formattedName = builder.formattedName();
        this.canBuild = builder.canBuild();
        this.canDestroy = builder.canDestroy();
        this.canUseBuckets = builder.canUseBuckets();
        this.canDestroyHanging = builder.canDestroyHanging();
        this.canPvP = builder.canPvP();
        this.canPvE = builder.canPvE();
        this.canDrop = builder.canDrop();
        this.canPickup = builder.canPickup();
        this.canLoseHunger = builder.canLoseHunger();
    }

    /**
     * @return The builder which is used to create a MinigameConfig object.
     */
    public static MinigameConfigBuilder builder() {
        return new MinigameConfigBuilder();
    }

    /**
     * @return name of the {@link Minigame}
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return name of the {@link Minigame} which may also include colors and styling
     */
    public String getFormattedName() {
        return this.formattedName;
    }

    public BiPredicate<Minigame<?>, Player> getCanBuild() {
        return this.canBuild;
    }

    public BiPredicate<Minigame<?>, Player> getCanDestroy() {
        return this.canDestroy;
    }

    public BiPredicate<Minigame<?>, Player> getCanUseBuckets() {
        return this.canUseBuckets;
    }

    public BiPredicate<Minigame<?>, Player> getCanDestroyHanging() {
        return this.canDestroyHanging;
    }

    public BiPredicate<Minigame<?>, Player> getCanPvP() {
        return this.canPvP;
    }

    public BiPredicate<Minigame<?>, Player> getCanPvE() {
        return this.canPvE;
    }

    public BiPredicate<Minigame<?>, Player> getCanDrop() {
        return this.canDrop;
    }

    public BiPredicate<Minigame<?>, Player> getCanPickup() {
        return this.canPickup;
    }

    public BiPredicate<Minigame<?>, Player> getCanLoseHunger() {
        return this.canLoseHunger;
    }
}
