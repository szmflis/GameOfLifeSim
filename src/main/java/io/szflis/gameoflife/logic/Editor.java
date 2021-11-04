package io.szflis.gameoflife.logic;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.util.Property;

public class Editor {

    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();
    private Property<Board> editingBoard = new Property<>();

    private boolean drawingEnabled = true;

    public Editor(Board initialEditingBoard) {
        this.editingBoard.set(initialEditingBoard);
    }

    public void handle(DrawModeEvent drawModeEvent) {
        this.drawMode.set(drawModeEvent.getDrawMode());
    }

    public void handle(BoardEvent boardEvent) {
        switch (boardEvent.getEventType()) {
            case PRESSED:
                boardPressed(boardEvent.getCursorPosition());
                break;
            case MOVED:
                cursorPosition.set(boardEvent.getCursorPosition());
                break;
        }
    }

    public void onAppStateChanged(ApplicationState state) {
        System.out.println("EditorViewModel got a ping about ppp state change to: " + state);
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            this.editingBoard.set(this.editingBoard.get());
        } else {
            drawingEnabled = false;
        }
    }

    private void boardPressed(CellPosition cellPosition) {
        this.cursorPosition.set(cellPosition);
        if (drawingEnabled) {
            Board board = this.editingBoard.get();
            board.setState(cellPosition.getX(), cellPosition.getY(), drawMode.get());
            this.editingBoard.set(board);
        }
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }

    public Property<Board> getBoard() {
        return this.editingBoard;
    }
}
