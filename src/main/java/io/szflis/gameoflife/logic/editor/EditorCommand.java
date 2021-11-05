package io.szflis.gameoflife.logic.editor;

import io.szflis.gameoflife.command.Command;
import io.szflis.gameoflife.state.EditorState;

public interface EditorCommand extends Command<EditorState> {
    @Override
    void execute(EditorState editorState);

    @Override
    default Class<EditorState> getStateClass() {
        return EditorState.class;
    }
}
