package io.szflis.gameoflife.components.simulationcavnas;

import io.szflis.app.observable.Property;

public class SimulationCanvasState {

    private final Property<Integer> blockPxSize = new Property<>();

    private final Property<Integer> canvasWidth = new Property<>();

    private final Property<Integer> canvasHeight = new Property<>();

    public SimulationCanvasState(int blockPxSize, int canvasWidth, int canvasHeight) {
        this.blockPxSize.set(blockPxSize);
        this.canvasWidth.set(canvasWidth);
        this.canvasHeight.set(canvasHeight);
    }

    public Property<Integer> getBlockPxSize() {
        return this.blockPxSize;
    }

    public Property<Integer> getCanvasWidth() {
        return this.canvasWidth;
    }

    public Property<Integer> getCanvasHeight() {
        return this.canvasHeight;
    }
}
