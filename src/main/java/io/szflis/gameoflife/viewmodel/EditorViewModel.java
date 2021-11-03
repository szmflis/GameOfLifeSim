package io.szflis.gameoflife.viewmodel;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.util.Property;

public class EditorViewModel {

    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();

    private BoardViewModel boardViewModel;
    private Board editingBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialEditingBoard) {
        this.boardViewModel = boardViewModel;
        this.editingBoard = initialEditingBoard;
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
            this.boardViewModel.getBoard().set(editingBoard);
        } else {
            drawingEnabled = false;
        }
    }

    private void boardPressed(CellPosition cellPosition) {
        this.cursorPosition.set(cellPosition);
        if (drawingEnabled) {
            this.editingBoard.setState(cellPosition.getX(), cellPosition.getY(), drawMode.get());
            this.boardViewModel.getBoard().set(this.editingBoard);
        }
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }

    public Board getBoard() {
        return this.editingBoard;
    }
}
