package io.szflis.app.command;

public interface UndoableCommand<T> extends Command<T> {

    void undo(T state);

}
