package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;

public class Change {

    private CellPosition position;
    private CellState newCellState;
    private CellState previousCellState;

    public Change(CellPosition position, CellState newCellState, CellState previousCellState) {
        this.position = position;
        this.newCellState = newCellState;
        this.previousCellState = previousCellState;
    }

    public CellPosition getPosition() {
        return position;
    }

    public CellState getNewCellState() {
        return newCellState;
    }

    public CellState getPreviousCellState() {
        return previousCellState;
    }
}
