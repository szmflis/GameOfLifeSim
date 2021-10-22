package io.szflis.gameoflife.viewmodel;

import java.util.LinkedList;
import java.util.List;

public class ApplicationViewModel {

    private ApplicationState currentState;
    private final List<SimpleChangeListener<ApplicationState>> appStateListeners;

    public ApplicationViewModel(ApplicationState applicationState) {
        this.currentState = applicationState;
        this.appStateListeners = new LinkedList<>();
    }

    public void listenToApplicationState(SimpleChangeListener<ApplicationState> simpleChangeListener) {
        this.appStateListeners.add(simpleChangeListener);
    }

    public void setCurrentState(ApplicationState newState) {
        if (newState != this.currentState) {
            this.currentState = newState;
            notifyAppStateListeners();
        }
    }

    private void notifyAppStateListeners() {
        for (SimpleChangeListener<ApplicationState> appStateListener : appStateListeners) {
            appStateListener.valueChanged(this.currentState);
        }
    }


}
