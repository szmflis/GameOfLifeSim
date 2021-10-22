package io.szflis.gameoflife;

import io.szflis.gameoflife.viewmodel.BoardViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private Simulation simulation;
    private BoardViewModel boardViewModel;

    public Simulator(Simulation simulation, BoardViewModel boardViewModel) {
        this.simulation = simulation;
        this.boardViewModel = boardViewModel;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void doStep() {
        this.simulation.step();
        this.boardViewModel.setBoard(this.simulation.getBoard());
    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }
}
