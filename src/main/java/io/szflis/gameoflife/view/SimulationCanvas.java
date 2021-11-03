package io.szflis.gameoflife.view;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellPosition;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.util.event.EventBus;
import io.szflis.gameoflife.viewmodel.BoardEvent;
import io.szflis.gameoflife.viewmodel.BoardViewModel;
import io.szflis.gameoflife.viewmodel.EditorViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class SimulationCanvas extends Pane {

    private final Canvas canvas;
    private Affine affine;
    private EditorViewModel editorViewModel;
    private BoardViewModel boardViewModel;
    private EventBus eventBus;

    public SimulationCanvas(EditorViewModel editorViewModel, BoardViewModel boardViewModel, EventBus eventBus) {
        this.editorViewModel = editorViewModel;
        this.boardViewModel = boardViewModel;
        this.eventBus = eventBus;
        boardViewModel.getBoard().listen(this::draw);
        editorViewModel.getCursorPosition().listen(
                cellPosition -> draw(boardViewModel.getBoard().get()));

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

    private void draw(Board board) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,400,400);

        gc.setFill(Color.BLACK);

        this.drawSimulation(board);

        CellPosition cursorPosition = editorViewModel.getCursorPosition().get();
        if (editorViewModel.getCursorPosition().isPresent()) {
            gc.setFill(new Color(0.3, 0.3, 0.3, 0.5));
            gc.fillRect(cursorPosition.getX(), cursorPosition.getY(), 1, 1);
        }

        gc.setStroke(Color.GREY);
        gc.setLineWidth(0.05f);
        for (int x = 0; x <= board.getWidth(); x++) {
            gc.strokeLine(x,0,x,board.getHeight());
        }

        for (int y = 0; y <= board.getHeight(); y++) {
            gc.strokeLine(0,y,board.getWidth(),y);
        }
    }

    private void drawSimulation(Board simulationToDraw) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        for (int x = 0; x < simulationToDraw.getWidth(); x++) {
            for (int y = 0; y < simulationToDraw.getHeight(); y++) {
                if (simulationToDraw.getState(x,y) == CellState.ALIVE) {
                    gc.fillRect(x,y,1,1);
                }
            }
        }
    }

    @Override
    public void resize(double v, double v1) {
        super.resize(v, v1);
        draw(this.boardViewModel.getBoard().get());
    }
}
