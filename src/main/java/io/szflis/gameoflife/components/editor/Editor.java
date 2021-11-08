package io.szflis.gameoflife.components.editor;

import io.szflis.app.command.CommandExecutor;
import io.szflis.gameoflife.components.simulator.SimulatorEvent;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;

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
        cursorPositionChanged(boardEvent.getCursorPosition());
        switch (boardEvent.getEventType()) {
            case PRESSED:
                beginEdit();
                handleEdit(boardEvent.getCursorPosition());
                break;
            case MOVED:
                if (state.getEditInProgress().get()) {
                    handleEdit(boardEvent.getCursorPosition());
                }
                break;
            case RELEASED:
                handleEdit(boardEvent.getCursorPosition());
                endEdit();
                break;
        }
    }

    private void beginEdit() {
        state.getEditInProgress().set(true);
        state.getCurrentEdit().set(new Edit());
    }

    private void endEdit() {
        BoardEditCommand boardEditCommand = new BoardEditCommand(state.getCurrentEdit().get());
        commandExecutor.execute(boardEditCommand);
        state.getEditInProgress().set(false);
        state.getCurrentEdit().set(null);
    }

    public void handleSimulatorEvent(SimulatorEvent event) {
        if (event.getEventType() == SimulatorEvent.Type.RESET) {
            drawingEnabled = true;
        } else if (event.getEventType() == SimulatorEvent.Type.START
                || event.getEventType() == SimulatorEvent.Type.STEP){
            drawingEnabled = false;
        }
    }

    private void handleEdit(CellPosition cursorPosition) {
        cursorPositionChanged(cursorPosition);
        if (drawingEnabled) {
            CellState currentState = this.state
                    .getEditingBoard().get().getState(cursorPosition.getX(), cursorPosition.getY());
            CellState newState = this.state.getDrawMode().get();

            Change change = new Change(cursorPosition, newState, currentState);
            state.getCurrentEdit().get().add(change);
        }
    }

    private void cursorPositionChanged(CellPosition cursorPosition) {
        CursorPositionCommand command = new CursorPositionCommand(cursorPosition);
        commandExecutor.execute(command);
    }
}
