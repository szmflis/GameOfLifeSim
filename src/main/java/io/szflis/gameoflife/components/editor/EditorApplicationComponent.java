package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;
import io.szflis.gameoflife.components.board.BoardState;
import io.szflis.gameoflife.components.simulator.SimulatorEvent;
import io.szflis.gameoflife.components.simulator.SimulatorState;
import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.model.drawlayer.DrawLayersState;

public class EditorApplicationComponent implements ApplicationComponent  {

    @Override
    public void initComponent(ApplicationContext applicationContext) {
        EditorState editorState = applicationContext.getStateRegistry().getState(EditorState.class);
        BoardState boardState = applicationContext.getStateRegistry().getState(BoardState.class);
        DrawLayersState drawLayersState = applicationContext.getStateRegistry().getState(DrawLayersState.class);
        SimulatorState simulatorState = applicationContext.getStateRegistry().getState(SimulatorState.class);

        Editor editor = new Editor(editorState, applicationContext.getCommandExecutor());
        applicationContext.getEventBus().listenFor(DrawModeEvent.class, editor::handle);
        applicationContext.getEventBus().listenFor(BoardEvent.class, editor::handle);
        applicationContext.getEventBus().listenFor(SimulatorEvent.class, editor::handleSimulatorEvent);
        applicationContext.getEventBus().listenFor(SimulatorEvent.class, event -> {
            if (event.getEventType() == SimulatorEvent.Type.RESET) {
                boardState.getBoard().set(editorState.getEditingBoard().get());
                simulatorState.getBoard().set(editorState.getEditingBoard().get());
            }
        });

        editorState.getEditingBoard().listen(boardState.getBoard()::set);

        ToolDrawLayer toolDrawLayer = new ToolDrawLayer(editorState);
        drawLayersState.addDrawLayer(toolDrawLayer);

        CurrentEditDrawLayer editDrawLayer = new CurrentEditDrawLayer(editorState);
        drawLayersState.addDrawLayer(editDrawLayer);
    }

    @Override
    public void initState(ApplicationContext applicationContext) {
        Board board = new BoundedBoard(applicationContext.getBoardWidth(), applicationContext.getBoardHeight());
        EditorState editorState = new EditorState(board);
        applicationContext.getStateRegistry().registerState(EditorState.class, editorState);
    }
}
