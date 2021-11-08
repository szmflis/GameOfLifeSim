package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;

public class BoardEditCommand implements UndoableEditorCommand {

    private Edit edit;

    public BoardEditCommand(Edit edit) {
        this.edit = new Edit(edit);
    }

    @Override
    public void execute(EditorState editorState) {
        BoundedBoard board = (BoundedBoard) editorState.getEditingBoard().get();

        for (Change change : edit) {
            CellPosition position = change.getPosition();
            board.setState(position.getX(), position.getY(), change.getNewCellState());
        }

        editorState.getEditingBoard().set(board);
    }

    @Override
    public void undo(EditorState editorState) {
        BoundedBoard board = (BoundedBoard) editorState.getEditingBoard().get();

        for (Change change : edit) {
            CellPosition position = change.getPosition();
            board.setState(position.getX(), position.getY(), change.getPreviousCellState());
        }

        editorState.getEditingBoard().set(board);
    }
}
