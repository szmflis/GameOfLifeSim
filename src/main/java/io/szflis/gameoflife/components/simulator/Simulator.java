package io.szflis.gameoflife.components.simulator;

import io.szflis.app.command.CommandExecutor;
import io.szflis.gameoflife.model.Simulation;
import io.szflis.gameoflife.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private Simulation simulation;
    private SimulatorState state;
    private CommandExecutor commandExecutor;
    private boolean reset = true;

    public Simulator(SimulatorState state, CommandExecutor commandExecutor) {
        this.state = state;
        this.commandExecutor = commandExecutor;
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
            this.state.getSimulating().set(true);
        }

        this.simulation.step();

        SetBoardCommand command = new SetBoardCommand(simulation);
        commandExecutor.execute(command);
    }

    private void reset() {
        reset = true;
        this.state.getSimulating().set(false);
    }

    private void start() {
        this.timeline.play();
    }

    private void stop() {
        this.timeline.stop();
    }
}
