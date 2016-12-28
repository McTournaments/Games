package net.mctournaments.gameapi.state;

import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.YELLOW;

import com.google.common.base.Preconditions;
import net.mctournaments.bukkit.utils.player.Title;
import net.mctournaments.gameapi.Minigame;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * {@link MinigameState} for counting down to a {@link Minigame} start.
 *
 * {@link #countdownCycle(Player, int)} and {@link #countdownEnd()} can be overridden to edit behaviour.
 */
public class CountdownState implements MinigameState, Listener {

    protected final Minigame<?> minigame;
    private final MinigameState nextState;

    private int timeLeft;

    public CountdownState(Minigame<?> minigame, MinigameState nextState, int length) {
        this.minigame = Preconditions.checkNotNull(minigame);
        this.nextState = Preconditions.checkNotNull(nextState);

        Preconditions.checkState(length > 0, "timeLeft for a CountdownState must be more than 0");
        this.timeLeft = length;
    }

    @Override
    public String getName() {
        return "countdown";
    }

    @Override
    public void onStart() {
        this.minigame.registerListener(this);
    }

    @Override
    public void onEnd() {
        this.minigame.unregisterListener(this);
    }

    @Override
    public void update() {
        if (this.timeLeft == 0) {
            this.countdownEnd();
            this.minigame.setState(this.nextState);
            return;
        }

        this.minigame.getPlayers().forEach(player -> this.countdownCycle(player, this.timeLeft));
        this.timeLeft--;
    }

    protected void countdownCycle(Player player, int timeLeft) {
        String msg = String.format(YELLOW + "The game starts in " + GOLD + "%d" + YELLOW + " seconds!", this.timeLeft);

        if (timeLeft % 5 == 0 || timeLeft <= 10) {
            player.sendMessage(msg);
        }

        if (timeLeft <= 5) {
            new Title().title(msg).send(player);
        }
    }

    protected void countdownEnd() {
        for (Player player : this.minigame.getPlayers()) {
            String msg = YELLOW + BOLD.toString() + GREEN + "GO!";
            new Title().title(msg).send(player);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!this.minigame.isPlayer(event.getPlayer())) {
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() != to.getBlockX()) {
            to.setX(from.getBlockX());
        }

        if (from.getBlockZ() != to.getBlockZ()) {
            to.setZ(from.getBlockZ());
        }
    }

}
