package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;

public class BoardEditCommand implements EditorCommand {

    private CellPosition position;
    private CellState drawMode;

    public BoardEditCommand(CellPosition position, CellState drawMode) {
        this.position = position;
        this.drawMode = drawMode;
    }

    @Override
    public void execute(EditorState editorState) {
        BoundedBoard board = (BoundedBoard) editorState.getEditingBoard().get();
        board.setState(position.getX(), position.getY(), drawMode);
        editorState.getEditingBoard().set(board);
    }
}
