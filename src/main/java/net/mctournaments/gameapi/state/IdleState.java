package net.mctournaments.gameapi.state;

/**
 * Default {@link MinigameState} which does nothing.
 */
public class IdleState implements MinigameState {

    @Override
    public String getName() {
        return "idle";
    }

}
