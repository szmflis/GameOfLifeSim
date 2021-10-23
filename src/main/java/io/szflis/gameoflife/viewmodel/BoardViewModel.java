package io.szflis.gameoflife.viewmodel;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.util.Property;

public class BoardViewModel {

    private Property<Board> board = new Property<>();

    public BoardViewModel() {
    }

    public Property<Board> getBoard() {
        return this.board;
    }
}
