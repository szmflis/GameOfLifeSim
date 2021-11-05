package io.szflis.gameoflife.logic.simulator;

import io.szflis.gameoflife.logic.ApplicationState;
import io.szflis.gameoflife.logic.ApplicationStateManager;
import io.szflis.gameoflife.model.Simulation;
import io.szflis.gameoflife.model.StandardRule;
import io.szflis.gameoflife.state.SimulatorState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private Simulation simulation;
    private ApplicationStateManager applicationStateManager;
    private SimulatorState state;
    // only simulator cares about it, never persisted, so not in state
    private boolean reset = true;

    public Simulator(ApplicationStateManager applicationStateManager, SimulatorState state) {
        this.applicationStateManager = applicationStateManager;
        this.state = state;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void handle(SimulatorEvent event) {
        switch (event.getEventType()) {
            case START:
                start();
                break;
            case STOP:
                stop();
                break;
            case STEP:
                doStep();
                break;
            case RESET:
                reset();
                break;
        }
    }

    private void doStep() {
        if (reset) {
            reset = false;
            this.simulation = new Simulation(this.state.getBoard().get(), new StandardRule());
            applicationStateManager.getApplicationState().set(ApplicationState.SIMULATING);
        }

        this.simulation.step();

        SimulatorCommand command = (state) -> {
            state.getBoard().set(simulation.getBoard());
        };
        command.execute(state);
    }

    private void reset() {
        reset = true;
        this.applicationStateManager.getApplicationState().set(ApplicationState.EDITING);
    }

    private void start() {
        this.timeline.play();
    }

    private void stop() {
        this.timeline.stop();
    }
}
