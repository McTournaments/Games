package net.mctournaments.gameapi.components;

import com.google.common.collect.Sets;
import net.mctournaments.gameapi.Minigame;
import net.mctournaments.gameapi.exceptions.ComponentConflictException;
import net.mctournaments.gameapi.exceptions.DependencyNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * Represents an optional part of a {@link Minigame}.
 *
 * @author Desetude
 */
public abstract class Component {

    protected final Minigame<?> minigame;
    private final Set<Class<? extends Component>> conflicts;
    private final Set<Class<? extends Component>> depends;
    private String name;

    /**
     * @param minigame to associate the {@link Component} with
     */
    public Component(Minigame<?> minigame) {
        this.minigame = minigame;

        this.name = getClass().getSimpleName();

        this.conflicts = Sets.newHashSet();
        this.depends = Sets.newHashSet();
    }

    /**
     * @param conflicts which can't be present for {@code this} {@link Component} to validate.
     */
    protected void addConflicts(Class<? extends Component>... conflicts) {
        this.conflicts.addAll(Arrays.asList(conflicts));
    }

    /**
     * @param depends which have to be present for {@code this} {@link Component} to validate.
     */
    protected void addDepends(Class<? extends Component>... depends) {
        this.depends.addAll(Arrays.asList(depends));
    }

    /**
     * Validates {@code this} {@link Component} by checking if all dependencies are present and all conflicts are not.
     *
     * @throws DependencyNotFoundException if a required dependency is not found
     * @throws ComponentConflictException  if a conflicting {@link Component} is present
     */
    public void validate() {
        for (Class<? extends Component> dependency : this.depends) {
            if (!this.minigame.hasComponent(dependency)) {
                throw new DependencyNotFoundException(this, dependency);
            }
        }

        for (Class<? extends Component> conflict : this.conflicts) {
            if (this.minigame.hasComponent(conflict)) {
                throw new ComponentConflictException(this, conflict);
            }
        }
    }

    /**
     * @return owning {@link Minigame} for {@code this} {@link Component}
     */
    public Minigame getMinigame() {
        return this.minigame;
    }

    /**
     * @return name of {@code this} {@link Component}
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name to set for {@code this} {@link Component}
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@link Set} of {@link Component}s {@code this} {@link Component} conflicts with
     */
    public Set<Class<? extends Component>> getConflicts() {
        return Collections.unmodifiableSet(this.conflicts);
    }

    /**
     * @return {@link Set} of {@link Component}s {@code this} {@link Component} depends on
     */
    public Set<Class<? extends Component>> getDepends() {
        return Collections.unmodifiableSet(this.depends);
    }

}
