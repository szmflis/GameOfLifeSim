package io.szflis.gameoflife.logic.board;

import io.szflis.app.observable.Property;
import io.szflis.gameoflife.model.Board;

public class BoardState {

    private Property<Board> board = new Property<Board>();

    public BoardState(Board board) {
        this.board.set(board);
    }

    public Property<Board> getBoard() {
        return board;
    }
}
