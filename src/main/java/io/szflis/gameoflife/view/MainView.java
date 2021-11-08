package io.szflis.gameoflife.view;

import io.szflis.gameoflife.components.editor.DrawModeEvent;
import io.szflis.gameoflife.model.CellState;
import io.szflis.app.event.EventBus;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    private EventBus eventBus;

    private SimulationCanvas canvas;


    public MainView(EventBus eventBus) {
        this.eventBus = eventBus;

        this.canvas = new SimulationCanvas(eventBus);
        this.setCenter(canvas);

        Toolbar toolbar = new Toolbar(eventBus);
        this.setTop(toolbar);

        this.setOnKeyPressed(this::onKeyPressed);
    }

    public void addDrawLayer(DrawLayer drawLayer) {
        canvas.addDrawLayer(drawLayer);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.eventBus.emit(new DrawModeEvent(CellState.ALIVE));
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.eventBus.emit(new DrawModeEvent(CellState.DEAD));
        }
    }


}
