package io.szflis.gameoflife.components.board;

import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;
import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.model.drawlayer.DrawLayersState;

public class BoardApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext applicationContext) {
        BoardState state = applicationContext.getStateRegistry().getState(BoardState.class);
        DrawLayersState drawLayersState = applicationContext.getStateRegistry().getState(DrawLayersState.class);

        BoardDrawLayer boardDrawLayer = new BoardDrawLayer(state);
        GridDrawLayer gridDrawLayer = new GridDrawLayer(state);

        drawLayersState.addDrawLayer(boardDrawLayer);
        drawLayersState.addDrawLayer(gridDrawLayer);
    }

    @Override
    public void initState(ApplicationContext applicationContext) {
        Board board = new BoundedBoard(applicationContext.getBoardWidth(), applicationContext.getBoardHeight());
        BoardState boardState = new BoardState(board);
        applicationContext.getStateRegistry().registerState(BoardState.class, boardState);
    }
}
