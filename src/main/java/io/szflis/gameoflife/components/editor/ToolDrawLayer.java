package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.drawlayer.AbstractDrawLayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ToolDrawLayer extends AbstractDrawLayer {

    private EditorState editorState;

    public ToolDrawLayer(EditorState editorState) {
        this.editorState = editorState;
        this.editorState.getCursorPosition().listen(cp -> invalidate());
    }

    @Override
    public void draw(GraphicsContext g) {
        CellPosition cursorPosition = editorState.getCursorPosition().get();

        if (cursorPosition != null) {
            g.setFill(new Color(0.3, 0.3, 0.3, 0.5));
            g.fillRect(cursorPosition.getX(), cursorPosition.getY(), 1, 1);
        }
    }

    @Override
    public int getLayer() {
        return 9;
    }
}
