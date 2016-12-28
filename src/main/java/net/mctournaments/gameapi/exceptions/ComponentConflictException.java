package net.mctournaments.gameapi.exceptions;

import net.mctournaments.gameapi.components.Component;

/**
 * {@link RuntimeException} called when a {@link Component} conflicts with another {@link Component} that is loaded.
 *
 * @author Desetude
 */
public class ComponentConflictException extends RuntimeException {

    public ComponentConflictException(Component component, Class<? extends Component> conflictedComponent) {
        super(component.getName() + "'s conflicts with dependency: " + conflictedComponent.getSimpleName() +
                " was not found and therefore, " + component.getName() + " has failed to enable.");
    }

}
