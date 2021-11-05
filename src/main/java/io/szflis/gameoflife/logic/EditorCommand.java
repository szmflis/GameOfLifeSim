package io.szflis.gameoflife.logic;

import io.szflis.gameoflife.command.Command;
import io.szflis.gameoflife.state.EditorState;

public interface EditorCommand extends Command<EditorState> {
    @Override
    void execute(EditorState editorState);
}
