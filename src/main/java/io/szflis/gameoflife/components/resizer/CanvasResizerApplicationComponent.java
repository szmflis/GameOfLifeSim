package io.szflis.gameoflife.components.resizer;

import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;
import io.szflis.gameoflife.components.board.BoardState;
import io.szflis.gameoflife.components.editor.EditorState;
import io.szflis.gameoflife.components.simulationcavnas.SimulationCanvasState;

public class CanvasResizerApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext applicationContext) {
        BoardState boardState = applicationContext.getStateRegistry().getState(BoardState.class);
        EditorState editorState = applicationContext.getStateRegistry().getState(EditorState.class);
        SimulationCanvasState simulationCanvasState = applicationContext.getStateRegistry().getState(SimulationCanvasState.class);

        CanvasResizer canvasResizer = new CanvasResizer(boardState, editorState, simulationCanvasState);

        applicationContext.getEventBus().listenFor(CanvasResizeEvent.class, canvasResizer::resize);
    }

    @Override
    public void initState(ApplicationContext applicationContext) {

    }
}
