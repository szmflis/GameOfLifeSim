package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import io.szflis.app.observable.Property;

public class EditorState {
    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();
    private Property<Board> editingBoard = new Property<>();
    private Property<Boolean> editInProgress = new Property<>(false);
    private Property<Edit> currentEdit = new Property<>();

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

    public Property<Boolean> getEditInProgress() {
        return editInProgress;
    }

    public Property<Edit> getCurrentEdit() {
        return currentEdit;
    }
}
