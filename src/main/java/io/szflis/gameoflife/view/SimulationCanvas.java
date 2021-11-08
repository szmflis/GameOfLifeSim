package io.szflis.gameoflife.view;

import io.szflis.app.event.EventBus;
import io.szflis.gameoflife.components.editor.BoardEvent;
import io.szflis.gameoflife.model.CellPosition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SimulationCanvas extends Pane {

    private final Canvas canvas;
    private Affine affine;
    private EventBus eventBus;
    private List<DrawLayer> drawLayers = new LinkedList<>();

    public SimulationCanvas(EventBus eventBus) {
        this.eventBus = eventBus;

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleCursorMoved);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(400/10f, 400/10f);
    }

    public void addDrawLayer(DrawLayer drawLayer) {
        drawLayers.add(drawLayer);
        drawLayers.sort(Comparator.comparingInt(DrawLayer::getLayer));
        drawLayer.addInvalidationListener(this::draw);
    }

    private void handleCursorMoved(MouseEvent mouseEvent) {
        CellPosition cursorPosition = getPointSimCoords(mouseEvent);
        eventBus.emit(new BoardEvent(BoardEvent.Type.MOVED, cursorPosition));
    }

    private void handleDraw(MouseEvent mouseEvent) {
        CellPosition simCoord = getPointSimCoords(mouseEvent);
        eventBus.emit(new BoardEvent(BoardEvent.Type.PRESSED, simCoord));
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
        gc.fillRect(0,0,400,400);

        for (DrawLayer drawLayer : drawLayers) {
            drawLayer.draw(gc);
        }
    }

    @Override
    public void resize(double v, double v1) {
        super.resize(v, v1);
        draw();
    }
}
