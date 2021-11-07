package io.szflis.gameoflife.logic.editor;

import io.szflis.gameoflife.model.CellState;

public class DrawModeCommand implements EditorCommand {

    private CellState newDrawMode;

    public DrawModeCommand(CellState newDrawMode) {
        this.newDrawMode = newDrawMode;
    }

    @Override
    public void execute(EditorState editorState) {
        editorState.getDrawMode().set(newDrawMode);
    }
}
