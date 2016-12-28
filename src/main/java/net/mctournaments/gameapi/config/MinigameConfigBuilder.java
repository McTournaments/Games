package net.mctournaments.gameapi.config;

import net.mctournaments.gameapi.Minigame;
import org.bukkit.entity.Player;

import java.util.function.BiPredicate;

/**
 * Builder for {@link MinigameConfig}.
 *
 * @author Desetude
 */
public class MinigameConfigBuilder {

    private String name;
    private String formattedName;
    private BiPredicate<Minigame<?>, Player> canBuild;
    private BiPredicate<Minigame<?>, Player> canDestroy;
    private BiPredicate<Minigame<?>, Player> canUseBuckets;
    private BiPredicate<Minigame<?>, Player> canDestroyHanging;
    private BiPredicate<Minigame<?>, Player> canPvP;
    private BiPredicate<Minigame<?>, Player> canPvE;
    private BiPredicate<Minigame<?>, Player> canDrop;
    private BiPredicate<Minigame<?>, Player> canPickup;
    private BiPredicate<Minigame<?>, Player> canLoseHunger;

    public MinigameConfigBuilder() {
        this.canBuild = (minigame, player) -> false;
        this.canDestroy = (minigame, player) -> false;
        this.canUseBuckets = (minigame, player) -> false;
        this.canDestroyHanging = (minigame, player) -> false;
        this.canPvP = (minigame, player) -> true;
        this.canPvE = (minigame, player) -> true;
        this.canDrop = (minigame, player) -> false;
        this.canPickup = (minigame, player) -> false;
        this.canLoseHunger = (minigame, player) -> false;
    }

    /**
     * @return the {@link MinigameConfig} created with set variables.
     */
    public MinigameConfig build() {
        return new MinigameConfig(this);
    }

    public String name() {
        return this.name;
    }

    public String formattedName() {
        return this.formattedName;
    }

    public BiPredicate<Minigame<?>, Player> canBuild() {
        return this.canBuild;
    }

    public BiPredicate<Minigame<?>, Player> canDestroy() {
        return this.canDestroy;
    }

    public BiPredicate<Minigame<?>, Player> canUseBuckets() {
        return this.canUseBuckets;
    }

    public BiPredicate<Minigame<?>, Player> canDestroyHanging() {
        return this.canDestroyHanging;
    }

    public BiPredicate<Minigame<?>, Player> canPvP() {
        return this.canPvP;
    }

    public BiPredicate<Minigame<?>, Player> canPvE() {
        return this.canPvE;
    }

    public BiPredicate<Minigame<?>, Player> canDrop() {
        return this.canDrop;
    }

    public BiPredicate<Minigame<?>, Player> canPickup() {
        return this.canPickup;
    }

    public BiPredicate<Minigame<?>, Player> canLoseHunger() {
        return this.canLoseHunger;
    }

    public MinigameConfigBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MinigameConfigBuilder formattedName(String formattedName) {
        this.formattedName = formattedName;
        return this;
    }

    public MinigameConfigBuilder canDrop(BiPredicate<Minigame<?>, Player> canDrop) {
        this.canDrop = canDrop;
        return this;
    }

    public MinigameConfigBuilder canBuild(BiPredicate<Minigame<?>, Player> canBuild) {
        this.canBuild = canBuild;
        return this;
    }

    public MinigameConfigBuilder canDestroy(BiPredicate<Minigame<?>, Player> canDestroy) {
        this.canDestroy = canDestroy;
        return this;
    }

    public MinigameConfigBuilder canUseBuckets(BiPredicate<Minigame<?>, Player> canUseBuckets) {
        this.canUseBuckets = canUseBuckets;
        return this;
    }

    public MinigameConfigBuilder canDestroyHanging(BiPredicate<Minigame<?>, Player> canDestroyHanging) {
        this.canDestroyHanging = canDestroyHanging;
        return this;
    }

    public MinigameConfigBuilder canPvP(BiPredicate<Minigame<?>, Player> canPvP) {
        this.canPvP = canPvP;
        return this;
    }

    public MinigameConfigBuilder canPvE(BiPredicate<Minigame<?>, Player> canPvE) {
        this.canPvE = canPvE;
        return this;
    }

    public MinigameConfigBuilder canPickup(BiPredicate<Minigame<?>, Player> canPickup) {
        this.canPickup = canPickup;
        return this;
    }

    public MinigameConfigBuilder canLoseHunger(BiPredicate<Minigame<?>, Player> canLoseHunger) {
        this.canLoseHunger = canLoseHunger;
        return this;
    }
}
