package io.szflis.gameoflife.model.drawlayer;

import io.szflis.app.observable.Property;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DrawLayersState {

    private final Property<List<AbstractDrawLayer>> drawLayers;

    public DrawLayersState() {
        this.drawLayers = new Property<>(new LinkedList<>());
    }

    public Property<List<AbstractDrawLayer>> getDrawLayers() {
        return drawLayers;
    }

    public void addDrawLayer(AbstractDrawLayer drawLayer) {
        drawLayers.get().add(drawLayer);
        drawLayers.get().sort(Comparator.comparingInt(AbstractDrawLayer::getLayer));
    }
}
