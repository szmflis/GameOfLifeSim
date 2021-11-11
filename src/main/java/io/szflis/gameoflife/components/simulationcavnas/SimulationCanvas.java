package io.szflis.gameoflife.components.simulationcavnas;

import io.szflis.app.event.EventBus;
import io.szflis.gameoflife.components.board.BoardState;
import io.szflis.gameoflife.components.editor.BoardEvent;
import io.szflis.gameoflife.components.resizer.CanvasResizeEvent;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.drawlayer.AbstractDrawLayer;
import io.szflis.gameoflife.model.drawlayer.DrawLayersState;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class SimulationCanvas extends Pane {

    private Canvas canvas;
    private Affine affine;
    private EventBus eventBus;
    private DrawLayersState drawLayersState;
    private SimulationCanvasState simulationCanvasState;
    private BoardState boardState;

    public SimulationCanvas(EventBus eventBus,
                            DrawLayersState drawLayersState,
                            SimulationCanvasState canvasState,
                            BoardState boardState) {
        this.eventBus = eventBus;
        this.drawLayersState = drawLayersState;
        this.simulationCanvasState = canvasState;
        this.boardState = boardState;

        setupCanvasHandlers();
        setupCanvasTransforms();
        initializeDrawLayers();

        this.getChildren().add(this.canvas);
    }

    private void setupCanvasTransforms() {
        this.affine = new Affine();
        final int blockPixelSize = simulationCanvasState.getBlockPxSize().get();
        this.affine.appendScale(blockPixelSize, blockPixelSize);
    }

    private int getCanvasWindowWidth() {
        return simulationCanvasState.getCanvasWidth().get() * simulationCanvasState.getBlockPxSize().get();
    }

    private int getCanvasWindowHeight() {
        return simulationCanvasState.getCanvasHeight().get()
                * simulationCanvasState.getBlockPxSize().get();
    }


    private void setupCanvasHandlers() {
        this.canvas = new Canvas(getCanvasWindowWidth(), getCanvasWindowHeight());
        this.canvas.setOnMousePressed(this::handlePressed);
        this.canvas.setOnMouseDragged(this::handleCursorMoved);
        this.canvas.setOnMouseReleased(this::handleReleased);
        this.canvas.setOnMouseMoved(this::handleCursorMoved);
    }

    private void handlePressed(MouseEvent mouseEvent) {
        CellPosition simCoord = getPointSimCoords(mouseEvent);
        eventBus.emit(new BoardEvent(BoardEvent.Type.PRESSED, simCoord));
    }

    private void handleCursorMoved(MouseEvent mouseEvent) {
        CellPosition cursorPosition = getPointSimCoords(mouseEvent);
        eventBus.emit(new BoardEvent(BoardEvent.Type.MOVED, cursorPosition));
    }

    private void handleReleased(MouseEvent mouseEvent) {
        CellPosition simCoord = getPointSimCoords(mouseEvent);
        eventBus.emit(new BoardEvent(BoardEvent.Type.RELEASED, simCoord));
    }

    private void initializeDrawLayers() {
        drawLayersState.getDrawLayers().get().forEach(drawLayer -> drawLayer.addInvalidationListener(this::draw));
    }


    private CellPosition getPointSimCoords(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);
            return new CellPosition((int) simCoord.getX(), (int) simCoord.getY());
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non invertable transform");
        }
    }

    private void draw() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,boardState.getBoard().get().getWidth(),boardState.getBoard().get().getHeight());

        for (AbstractDrawLayer drawLayer : drawLayersState.getDrawLayers().get()) {
            drawLayer.draw(gc);
        }
    }

    public void resizeCanvasWindow(int width, int height) {
        this.canvas.setWidth(width*simulationCanvasState.getBlockPxSize().get());
        this.canvas.setHeight(height*simulationCanvasState.getBlockPxSize().get());
    }

    @Override
    public void resize(double v, double v1) {
        super.resize(v, v1);
        draw();
    }

}
