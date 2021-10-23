package io.szflis.gameoflife.viewmodel;

import io.szflis.gameoflife.util.Property;

public class ApplicationViewModel {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

    public ApplicationViewModel() {
    }

    public Property<ApplicationState> getApplicationState() {
        return applicationState;
    }
}
