package io.szflis.gameoflife.command;

public interface Command<T> {

    void execute(T t);

}
