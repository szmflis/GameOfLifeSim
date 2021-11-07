package io.szflis.gameoflife.logic.editor;

import io.szflis.gameoflife.model.CellPosition;
import io.szflis.app.event.Event;

public class BoardEvent implements Event {

    public enum Type {
        MOVED,
        PRESSED
    }

    private Type eventType;
    private CellPosition cursorPosition;

    public BoardEvent(Type eventType, CellPosition cursorPosition) {
        this.eventType = eventType;
        this.cursorPosition = cursorPosition;
    }

    public Type getEventType() {
        return eventType;
    }

    public CellPosition getCursorPosition() {
        return cursorPosition;
    }
}
