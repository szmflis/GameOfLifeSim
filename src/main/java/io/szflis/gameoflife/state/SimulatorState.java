package io.szflis.gameoflife.state;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.util.Property;

public class SimulatorState {

    private Property<Board> board = new Property<>();

    public SimulatorState(Board board) {
        this.board = this.board;
    }

    public Property<Board> getBoard() {
        return board;
    }
}
