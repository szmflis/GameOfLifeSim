package io.szflis.gameoflife.components.editor;

import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.view.AbstractDrawLayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CurrentEditDrawLayer extends AbstractDrawLayer {

    private EditorState editorState;

    public CurrentEditDrawLayer(EditorState editorState) {
        this.editorState = editorState;
        editorState.getCurrentEdit().listen(e -> this.invalidate());
    }

    @Override
    public void draw(GraphicsContext g) {
        if (!editorState.getCurrentEdit().isPresent()) return;

        Edit edit = editorState.getCurrentEdit().get();

        for (Change change : edit) {
            if (change.getNewCellState() == CellState.ALIVE) {
                g.setFill(Color.BLACK);
            } else {
                g.setFill(Color.LIGHTGRAY);
            }

            CellPosition position = change.getPosition();
            g.fillRect(position.getX(), position.getY(), 1,1);
        }
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
