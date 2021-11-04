package io.szflis.gameoflife.logic;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.Simulation;
import io.szflis.gameoflife.model.StandardRule;
import io.szflis.gameoflife.util.Property;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private Simulation simulation;
    private ApplicationStateManager applicationStateManager;

    private Property<Board> initialBoard = new Property<>();
    private Property<Board> currentBoard = new Property<>();

    public Simulator(ApplicationStateManager applicationStateManager) {
        this.applicationStateManager = applicationStateManager;
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
        if (applicationStateManager.getApplicationState().get() != ApplicationState.SIMULATING) {
            this.simulation = new Simulation(initialBoard.get(), new StandardRule());
            applicationStateManager.getApplicationState().set(ApplicationState.SIMULATING);
        }
        this.simulation.step();
        this.currentBoard.set(simulation.getBoard());
    }

    private void reset() {
//        this.simulation = new Simulation(initialBoard.get(), new StandardRule());
        this.applicationStateManager.getApplicationState().set(ApplicationState.EDITING);
    }

    private void start() {
        this.timeline.play();
    }

    private void stop() {
        this.timeline.stop();
    }

    public Property<Board> getInitialBoard() {
        return initialBoard;
    }

    public Property<Board> getCurrentBoard() {
        return currentBoard;
    }
}
