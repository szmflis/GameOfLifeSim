package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.CellState;
import io.szflis.app.event.Event;

public class DrawModeEvent implements Event {

    private CellState newDrawMode;

    public DrawModeEvent(CellState newDrawMode) {
        this.newDrawMode = newDrawMode;
    }

    public CellState getDrawMode() {
        return newDrawMode;
    }
}
