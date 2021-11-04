package io.szflis.gameoflife.logic;

import io.szflis.gameoflife.util.Property;

public class ApplicationStateManager {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

    public ApplicationStateManager() {
    }

    public Property<ApplicationState> getApplicationState() {
        return applicationState;
    }
}
