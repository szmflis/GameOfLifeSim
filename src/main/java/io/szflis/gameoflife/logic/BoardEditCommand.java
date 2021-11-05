package io.szflis.gameoflife.logic;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.state.EditorState;

public class BoardEditCommand implements EditorCommand {

    private CellPosition position;
    private CellState drawMode;

    public BoardEditCommand(CellPosition position, CellState drawMode) {
        this.position = position;
        this.drawMode = drawMode;
    }

    @Override
    public void execute(EditorState editorState) {
        Board board = editorState.getEditingBoard().get();
        board.setState(position.getX(), position.getY(), drawMode);
        editorState.getEditingBoard().set(board);
    }
}
