package io.szflis.gameoflife.components.resizer;

import io.szflis.gameoflife.components.board.BoardState;
import io.szflis.gameoflife.components.editor.EditorState;
import io.szflis.gameoflife.components.simulationcavnas.SimulationCanvasState;
import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;

public class CanvasResizer {

    private final BoardState boardState;
    private final EditorState editorState;
    private SimulationCanvasState simulationCanvasState;

    public CanvasResizer(BoardState boardState, EditorState editorState, SimulationCanvasState simulationCanvasState) {
        this.boardState = boardState;
        this.editorState = editorState;
        this.simulationCanvasState = simulationCanvasState;
    }

    public void resize(CanvasResizeEvent canvasResizeEvent) {
        Board currentBoard = boardState.getBoard().get();

        switch (canvasResizeEvent.getEventType()) {
            case INCREASE:
                BoundedBoard biggerBoard = createBiggerCanvas(currentBoard, canvasResizeEvent.getAmount());
                boardState.getBoard().set(biggerBoard);
                editorState.getEditingBoard().set(biggerBoard);
                simulationCanvasState.getCanvasWidth().set(biggerBoard.getWidth());
                simulationCanvasState.getCanvasHeight().set(biggerBoard.getHeight());
                break;
            case DECREASE:
                BoundedBoard smallerBoard = createSmallerCanvas(currentBoard, canvasResizeEvent.getAmount());
                boardState.getBoard().set(smallerBoard);
                editorState.getEditingBoard().set(smallerBoard);
                simulationCanvasState.getCanvasWidth().set(smallerBoard.getWidth());
                simulationCanvasState.getCanvasHeight().set(smallerBoard.getHeight());
                break;
        }
    }

    private BoundedBoard createBiggerCanvas(Board board, int amount) {
        BoundedBoard newBoundedBoard
                = new BoundedBoard(board.getWidth() + amount, board.getHeight() + amount);

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                newBoundedBoard.setState(x, y, board.getState(x,y));
            }
        }

        return newBoundedBoard;
    }

    private BoundedBoard createSmallerCanvas(Board board, int amount) {
        BoundedBoard newBoundedBoard
                = new BoundedBoard(board.getWidth() - amount, board.getHeight() - amount);

        for (int y = 0; y < newBoundedBoard.getHeight(); y++) {
            for (int x = 0; x < newBoundedBoard.getWidth(); x++) {
                newBoundedBoard.setState(x, y, board.getState(x,y));
            }
        }

        return newBoundedBoard;
    }
}
