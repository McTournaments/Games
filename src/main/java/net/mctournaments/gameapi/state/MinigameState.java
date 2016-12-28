package net.mctournaments.gameapi.state;

import net.mctournaments.gameapi.Minigame;

/**
 * Class to manage a {@link Minigame} when it is in different state.
 *
 * @author Desetude
 */
public interface MinigameState {

    /**
     * @return Name used for logging purposes.
     */
    String getName();

    /**
     * Called on this GameState starting.
     */
    default void onStart() {
    }

    /**
     * Called on this GameState ending.
     */
    default void onEnd() {
    }

    /**
     * Called every second while {@code this} {@link MinigameState} is active.
     */
    default void update() {
    }

}