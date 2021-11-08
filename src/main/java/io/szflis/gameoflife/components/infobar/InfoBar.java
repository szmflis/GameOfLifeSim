package io.szflis.gameoflife.components.infobar;

import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {

    private static String drawModeFormat = "Draw mode: %s";
    private static String cursorPosFormat = "Cursor: (%d, %d)";

    private Label cursor;
    private Label editingTool;

    public InfoBar(InfoBarViewModel infoBarViewModel) {
        infoBarViewModel.getCurrentDrawMode().listen(this::setDrawMode);
        infoBarViewModel.getCursorPosition().listen(this::setCursorPos);

        this.cursor = new Label();
        this.editingTool = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

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

    public void setCursorPos(CellPosition cellPosition) {
        this.cursor.setText(String.format(cursorPosFormat, cellPosition.getX(), cellPosition.getY()));
    }
}
