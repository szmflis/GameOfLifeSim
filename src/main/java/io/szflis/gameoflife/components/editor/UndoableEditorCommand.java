package io.szflis.gameoflife.components.editor;

import io.szflis.app.command.UndoableCommand;

public interface UndoableEditorCommand extends UndoableCommand<EditorState> {
    @Override
    void execute(EditorState editorState);

    @Override
    void undo(EditorState state);

    @Override
    default Class<EditorState> getStateClass() {
        return EditorState.class;
    }
}
