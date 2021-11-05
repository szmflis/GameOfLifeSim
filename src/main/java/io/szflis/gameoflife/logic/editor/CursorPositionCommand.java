package io.szflis.gameoflife.logic.editor;

import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.state.EditorState;

public class CursorPositionCommand implements EditorCommand {

    private final CellPosition cursorPosition;

    public CursorPositionCommand(CellPosition cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    @Override
    public void execute(EditorState editorState) {
        editorState.getCursorPosition().set(cursorPosition);
    }
}
