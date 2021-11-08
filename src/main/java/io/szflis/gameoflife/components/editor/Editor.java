package io.szflis.gameoflife.components.editor;

import io.szflis.app.command.CommandExecutor;
import io.szflis.gameoflife.components.simulator.SimulatorEvent;
import io.szflis.gameoflife.model.CellPosition;

public class Editor {

    private EditorState state;
    CommandExecutor commandExecutor;

    private boolean drawingEnabled = true;

    public Editor(EditorState state, CommandExecutor commandExecutor) {
        this.state = state;
        this.commandExecutor = commandExecutor;
    }

    public void handle(DrawModeEvent drawModeEvent) {
        DrawModeCommand command = new DrawModeCommand(drawModeEvent.getDrawMode());
        commandExecutor.execute(command);
    }

    public void handle(BoardEvent boardEvent) {
        switch (boardEvent.getEventType()) {
            case PRESSED:
                boardPressed(boardEvent.getCursorPosition());
                break;
            case MOVED:
                cursorPositionChanged(boardEvent.getCursorPosition());
                break;
        }
    }

    public void handleSimulatorEvent(SimulatorEvent event) {
        if (event.getEventType() == SimulatorEvent.Type.RESET) {
            drawingEnabled = true;
        } else if (event.getEventType() == SimulatorEvent.Type.START
                || event.getEventType() == SimulatorEvent.Type.STEP){
            drawingEnabled = false;
        }
    }

    private void boardPressed(CellPosition cursorPosition) {
        cursorPositionChanged(cursorPosition);
        if (drawingEnabled) {
            BoardEditCommand command = new BoardEditCommand(cursorPosition, state.getDrawMode().get());
            commandExecutor.execute(command);
        }
    }

    private void cursorPositionChanged(CellPosition cursorPosition) {
        CursorPositionCommand command = new CursorPositionCommand(cursorPosition);
        commandExecutor.execute(command);
    }
}
