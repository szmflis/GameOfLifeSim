package io.szflis.gameoflife.components.resizer;

import io.szflis.app.event.Event;

public class CanvasResizeEvent implements Event {

    public enum Type {
        INCREASE,
        DECREASE
    }

    private final Type eventType;
    private final int amount;

    public CanvasResizeEvent(Type eventType, int amount) {
        this.eventType = eventType;
        this.amount = amount;
    }

    public Type getEventType() {
        return this.eventType;
    }

    public int getAmount() {
        return amount;
    }
}
