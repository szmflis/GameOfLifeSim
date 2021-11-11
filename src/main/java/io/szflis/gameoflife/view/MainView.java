package io.szflis.gameoflife.view;

import io.szflis.app.command.CommandExecutor;
import io.szflis.app.event.EventBus;
import io.szflis.gameoflife.components.editor.DrawModeEvent;
import io.szflis.gameoflife.model.CellState;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {

    private EventBus eventBus;

    private CommandExecutor commandExecutor;

    public MainView(EventBus eventBus, CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        this.eventBus = eventBus;

        this.setOnKeyPressed(this::onKeyPressed);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.eventBus.emit(new DrawModeEvent(CellState.ALIVE));
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.eventBus.emit(new DrawModeEvent(CellState.DEAD));
        } else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.Z) {
           commandExecutor.undo();
        }
    }


}
