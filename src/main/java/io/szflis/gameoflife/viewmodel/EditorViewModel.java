package io.szflis.gameoflife.viewmodel;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.util.Property;

public class EditorViewModel {

    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);

    private BoardViewModel boardViewModel;
    private Board editingBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialEditingBoard) {
        this.boardViewModel = boardViewModel;
        this.editingBoard = initialEditingBoard;
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            this.boardViewModel.getBoard().set(editingBoard);
        } else {
            drawingEnabled = false;
        }
    }

    public void boardPressed(int simX, int simY) {
        if (drawingEnabled) {
            this.editingBoard.setState(simX, simY, drawMode.get());
            this.boardViewModel.getBoard().set(this.editingBoard);
        }
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }
}
