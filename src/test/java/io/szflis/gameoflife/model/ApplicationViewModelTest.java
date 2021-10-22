package io.szflis.gameoflife.model;

import io.szflis.gameoflife.viewmodel.ApplicationState;
import io.szflis.gameoflife.viewmodel.ApplicationViewModel;
import io.szflis.gameoflife.viewmodel.SimpleChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationViewModelTest {

    private ApplicationViewModel applicationViewModel;

    @BeforeEach
    void setUp() {
        applicationViewModel = new ApplicationViewModel(ApplicationState.EDITING);
    }

    @Test
    void setApplicationState_setToNewState() {
        TestAppStateListener listener = new TestAppStateListener();
        applicationViewModel.listenToApplicationState(listener);
        applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        assertTrue(listener.appStateUpdated);
        assertEquals(ApplicationState.SIMULATING, listener.updatedAppState);
    }

    @Test
    void setApplicationState_setToSameState() {
        TestAppStateListener listener = new TestAppStateListener();
        applicationViewModel.listenToApplicationState(listener);
        applicationViewModel.setCurrentState(ApplicationState.EDITING);
        assertFalse(listener.appStateUpdated);
        assertNull(listener.updatedAppState);
    }

    private static class TestAppStateListener implements SimpleChangeListener<ApplicationState> {
        private boolean appStateUpdated = false;
        private ApplicationState updatedAppState = null;

        @Override
        public void valueChanged(ApplicationState newApplicationState) {
            appStateUpdated = true;
            updatedAppState = newApplicationState;
        }
    }
}
