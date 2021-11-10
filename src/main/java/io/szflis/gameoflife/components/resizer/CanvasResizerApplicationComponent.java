package io.szflis.gameoflife.components.resizer;

import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;
import io.szflis.gameoflife.components.board.BoardState;
import io.szflis.gameoflife.components.editor.EditorState;

public class CanvasResizerApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext applicationContext) {
        BoardState boardState = applicationContext.getStateRegistry().getState(BoardState.class);
        EditorState editorState = applicationContext.getStateRegistry().getState(EditorState.class);

        CanvasResizer canvasResizer = new CanvasResizer(boardState, editorState);

        applicationContext.getEventBus().listenFor(CanvasResizeEvent.class, canvasResizer::resize);
    }

    @Override
    public void initState(ApplicationContext applicationContext) {

    }
}
