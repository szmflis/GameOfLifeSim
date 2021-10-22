package io.szflis.gameoflife.viewmodel;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellState;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {

    private BoardViewModel boardViewModel;
    private Board editingBoard;
    private boolean drawingEnabled = true;

    private CellState drawMode = CellState.ALIVE;
    private List<SimpleChangeListener<CellState>> drawModeListeners;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialEditingBoard) {
        this.boardViewModel = boardViewModel;
        this.editingBoard = initialEditingBoard;
        this.drawModeListeners = new LinkedList<>();
    }

    public void listenToDrawMode(SimpleChangeListener<CellState> listener) {
        drawModeListeners.add(listener);
    }

    public void setDrawMode(CellState drawMode) {
        this.drawMode = drawMode;
        notifyDrawModeListeners();
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            this.boardViewModel.setBoard(editingBoard);
        } else {
            drawingEnabled = false;
        }
    }

    private void notifyDrawModeListeners() {
        for (SimpleChangeListener<CellState> drawModeListener : this.drawModeListeners) {
            drawModeListener.valueChanged(drawMode);
        }
    }

    public void boardPressed(int simX, int simY) {
        if (drawingEnabled) {
            this.editingBoard.setState(simX, simY, drawMode);
            this.boardViewModel.setBoard(this.editingBoard);
        }
    }
}
