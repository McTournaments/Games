package net.mctournaments.gameapi.exceptions;

import net.mctournaments.gameapi.components.Component;

/**
 * {@link RuntimeException} called when a {@link Component} conflicts with another {@link Component} that is loaded.
 *
 * @author Desetude
 */
public class DependencyNotFoundException extends RuntimeException {

    public DependencyNotFoundException(Component component, Class<? extends Component> dependedComponent) {
        super(component.getName() + "depends on: " + dependedComponent.getSimpleName() +
                " but was not found and therefore, " + component.getName() + " has failed to enable.");
    }

}
