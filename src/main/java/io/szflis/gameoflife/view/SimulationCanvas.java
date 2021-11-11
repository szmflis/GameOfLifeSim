package io.szflis.gameoflife.view;

import io.szflis.app.event.EventBus;
import io.szflis.gameoflife.components.editor.BoardEvent;
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

    private int singleBlockPixelSizeWidth = 30;
    private int singleBlockPixelSizeHeight = 30;
    private int canvasWidthInBlocks = 20;
    private int canvasHeightInBlocks = 20;

    public SimulationCanvas(EventBus eventBus, DrawLayersState drawLayersState) {
        this.eventBus = eventBus;
        this.drawLayersState = drawLayersState;

        setupCanvasHandlers();
        setupCanvasTransforms();
        initializeDrawLayers();

        this.getChildren().add(this.canvas);
    }

    private void setupCanvasTransforms() {
        this.affine = new Affine();
        this.affine.appendScale(this.singleBlockPixelSizeWidth, this.singleBlockPixelSizeHeight);
    }

    private void setupCanvasHandlers() {
        this.canvas = new Canvas(canvasWidthInBlocks*singleBlockPixelSizeWidth, canvasHeightInBlocks*singleBlockPixelSizeHeight);
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
        gc.fillRect(0,0,20,20);

        for (AbstractDrawLayer drawLayer : drawLayersState.getDrawLayers().get()) {
            System.out.println("Draw layer: " + drawLayer.toString());
            drawLayer.draw(gc);
        }
    }

    @Override
    public void resize(double v, double v1) {
        super.resize(v, v1);
        draw();
    }
}
