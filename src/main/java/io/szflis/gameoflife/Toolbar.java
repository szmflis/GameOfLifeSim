package io.szflis.gameoflife;

import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.viewmodel.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private ApplicationViewModel applicationViewModel;
    private SimulationViewModel simulationViewModel;
    private EditorViewModel editorViewModel;

    public Toolbar(EditorViewModel editorViewModel,
                   ApplicationViewModel applicationViewModel,
                   SimulationViewModel simulationViewModel) {
        this.editorViewModel = editorViewModel;
        this.applicationViewModel = applicationViewModel;
        this.simulationViewModel = simulationViewModel;

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
        this.simulationViewModel.stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.simulationViewModel.start();
    }

    private void handleReset(ActionEvent actionEvent) {
        System.out.println("Pressed reset");
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("Pressed step");
        switchToSimulatingState();
        this.simulationViewModel.doStep();
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("Pressed erase");
        editorViewModel.setDrawMode(CellState.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("Pressed draw");
        editorViewModel.setDrawMode(CellState.ALIVE);
    }

    private void switchToSimulatingState() {
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
    }
}
