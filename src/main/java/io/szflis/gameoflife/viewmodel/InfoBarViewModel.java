package io.szflis.gameoflife.viewmodel;

import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.util.Property;

public class InfoBarViewModel {

    private Property<CellState> currentDrawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();

    public Property<CellState> getCurrentDrawMode() {
        return currentDrawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }
}
