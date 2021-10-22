package io.szflis.gameoflife;

import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.model.StandardRule;
import io.szflis.gameoflife.viewmodel.ApplicationState;
import io.szflis.gameoflife.viewmodel.ApplicationViewModel;
import io.szflis.gameoflife.viewmodel.BoardViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private final MainView mainView;
    private ApplicationViewModel applicationViewModel;
    private BoardViewModel boardViewModel;
    private Simulator simulator;

    public Toolbar(MainView mainView,
                   ApplicationViewModel applicationViewModel,
                   BoardViewModel boardViewModel) {
        this.mainView = mainView;
        this.applicationViewModel = applicationViewModel;
        this.boardViewModel = boardViewModel;

        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);

        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);

        Button step = new Button("Step");
        step.setOnAction(this::handleStep);

        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);

        Button start = new Button("Start");
        start.setOnAction(this::handleStart);

        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        this.getItems().addAll(draw, erase, reset, step, start, stop);
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulator.stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.simulator.start();
    }

    private void handleReset(ActionEvent actionEvent) {
        System.out.println("Pressed reset");
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
        this.simulator = null;
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("Pressed step");
        switchToSimulatingState();
        this.simulator.doStep();
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("Pressed erase");
        mainView.setDrawMode(CellState.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("Pressed draw");
        mainView.setDrawMode(CellState.ALIVE);
    }

    private void switchToSimulatingState() {
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        Simulation simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        this.simulator = new Simulator(simulation, this.boardViewModel);
    }
}
