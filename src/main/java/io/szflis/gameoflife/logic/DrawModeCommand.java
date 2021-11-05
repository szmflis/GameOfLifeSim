package io.szflis.gameoflife.logic;

import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.state.EditorState;

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
