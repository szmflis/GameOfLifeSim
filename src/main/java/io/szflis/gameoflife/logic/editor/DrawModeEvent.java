package io.szflis.gameoflife.logic.editor;

import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.util.event.Event;

public class DrawModeEvent implements Event {

    private CellState newDrawMode;

    public DrawModeEvent(CellState newDrawMode) {
        this.newDrawMode = newDrawMode;
    }

    public CellState getDrawMode() {
        return newDrawMode;
    }
}
