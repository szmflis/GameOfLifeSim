package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import io.szflis.app.observable.Property;

public class EditorState {
    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();
    private Property<Board> editingBoard = new Property<>();

    public EditorState(Board editingBoard) {
        this.editingBoard.set(editingBoard);
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }

    public Property<Board> getEditingBoard() {
        return editingBoard;
    }
}
