package io.szflis.gameoflife.logic.board;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.view.AbstractDrawLayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridDrawLayer extends AbstractDrawLayer {

    private BoardState boardState;

    public GridDrawLayer(BoardState boardState) {
        this.boardState = boardState;
    }

    @Override
    public void draw(GraphicsContext g) {
        Board board = boardState.getBoard().get();
        g.setStroke(Color.GREY);
        g.setLineWidth(0.05f);
        for (int x = 0; x <= board.getWidth(); x++) {
            g.strokeLine(x,0,x,board.getHeight());
        }

        for (int y = 0; y <= board.getHeight(); y++) {
            g.strokeLine(0,y,board.getWidth(),y);
        }
    }

    @Override
    public int getLayer() {
        return 10;
    }
}
