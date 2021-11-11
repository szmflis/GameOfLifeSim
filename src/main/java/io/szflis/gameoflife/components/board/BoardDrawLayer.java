package io.szflis.gameoflife.components.board;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.model.drawlayer.AbstractDrawLayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardDrawLayer extends AbstractDrawLayer {

    private BoardState boardState;

    public BoardDrawLayer(BoardState boardState) {
        this.boardState = boardState;
        boardState.getBoard().listen(b -> this.invalidate()); //will cause draw to be called
    }

    @Override
    public void draw(GraphicsContext g) {
        g.setFill(Color.BLACK);

        Board board = boardState.getBoard().get();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.getState(x,y) == CellState.ALIVE) {
                    g.fillRect(x,y,1,1);
                }
            }
        }
    }

    @Override
    public int getLayer() {
        return 0;
    }
}
