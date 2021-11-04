package io.szflis.gameoflife.viewmodel;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.util.Property;

public class BoardViewModel {

    private Property<Board> board = new Property<>();
    private Property<CellPosition> cursorPosition = new Property<>();

    public BoardViewModel() {
    }

    public Property<Board> getBoard() {
        return this.board;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }
}
