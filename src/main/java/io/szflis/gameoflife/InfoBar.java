package io.szflis.gameoflife;

import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.viewmodel.EditorViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {

    private static String drawModeFormat = "Draw mode: %s";
    private static String cursorPosFormat = "Cursor: (%d, %d)";

    private Label cursor;
    private Label editingTool;

    public InfoBar(EditorViewModel editorViewModel) {
        editorViewModel.getDrawMode().listen(this::setDrawMode);
        this.cursor = new Label();
        this.editingTool = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setCursorPos(0,0);

        this.getChildren().addAll(editingTool, spacer, cursor);
    }

    private void setDrawMode(CellState drawMode) {
        String drawModeString;
        if (drawMode == CellState.ALIVE) {
            drawModeString = "Drawing";
        } else {
            drawModeString = "Erasing";
        }
        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }

    public void setCursorPos(int x, int y) {
        this.cursor.setText(String.format(cursorPosFormat, x, y));
    }
}
