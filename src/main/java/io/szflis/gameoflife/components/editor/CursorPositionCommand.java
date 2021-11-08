package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.CellPosition;

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
