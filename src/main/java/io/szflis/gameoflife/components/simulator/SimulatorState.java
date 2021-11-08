package io.szflis.gameoflife.components.simulator;

import io.szflis.gameoflife.model.Board;
import io.szflis.app.observable.Property;

public class SimulatorState {

    private Property<Boolean> simulating = new Property<>(false);
    private Property<Board> board = new Property<>();

    public SimulatorState(Board board) {
        this.board = this.board;
    }

    public Property<Board> getBoard() {
        return board;
    }

    public Property<Boolean> getSimulating() {
        return simulating;
    }
}
