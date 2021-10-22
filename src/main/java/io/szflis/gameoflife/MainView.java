package io.szflis.gameoflife;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.viewmodel.ApplicationState;
import io.szflis.gameoflife.viewmodel.ApplicationViewModel;
import io.szflis.gameoflife.viewmodel.BoardViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Board initialBoard;

    private Canvas canvas;
    private Affine affine;
    private InfoBar infoBar;
    private CellState drawMode = CellState.ALIVE;

    private ApplicationViewModel applicationViewModel;
    private BoardViewModel boardViewModel;
    private boolean isDrawingEnabled = true;
    private boolean drawInitialBoard = true;

    public MainView(ApplicationViewModel applicationViewModel,
                    BoardViewModel boardViewModel, Board initialBoard) {
        this.applicationViewModel = applicationViewModel;
        this.boardViewModel = boardViewModel;
        this.initialBoard = initialBoard;
        this.boardViewModel.listenToBoard(this::onBoardChanged);

        this.applicationViewModel.listenToApplicationState(this::onApplicationStateChanged);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this, applicationViewModel, boardViewModel);
        this.infoBar = new InfoBar();
        this.infoBar.setDrawMode(this.drawMode);
        this.infoBar.setCursorPos(0,0);

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer,  infoBar);

        this.affine = new Affine();
        this.affine.appendScale(400/10f, 400/10f);

    }

    private void onBoardChanged(Board board) {
        draw(board);
    }

    private void onApplicationStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            this.isDrawingEnabled = true;
            this.boardViewModel.setBoard(this.initialBoard);
        } else if(state == ApplicationState.SIMULATING) {
            this.isDrawingEnabled = false;
        } else {
            throw new IllegalArgumentException("Unsupported ApplicationState " + state.name());
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.drawMode = CellState.ALIVE;
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.drawMode = CellState.DEAD;
        }
    }

    private void handleDraw(MouseEvent mouseEvent) {
        if (!isDrawingEnabled) return;

        Point2D simCoord = getPointSimCoords(mouseEvent);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.initialBoard.setState(simX, simY, drawMode);
        this.boardViewModel.setBoard(this.initialBoard);
    }

    private void handleMoved(MouseEvent mouseEvent) {
        Point2D simCoord = getPointSimCoords(mouseEvent);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();
        this.infoBar.setCursorPos(simX, simY);
    }

    private Point2D getPointSimCoords(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        try {
            return this.affine.inverseTransform(mouseX, mouseY);
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non invertable transform");
        }
    }

    public void draw(Board board) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);
        
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,400,400);

        gc.setFill(Color.BLACK);

        this.drawSimulation(board);

        gc.setStroke(Color.GREY);
        gc.setLineWidth(0.05f);
        for (int x = 0; x <= board.getWidth(); x++) {
            gc.strokeLine(x,0,x,10);
        }

        for (int y = 0; y <= board.getHeight(); y++) {
            gc.strokeLine(0,y,10,y);
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

    public void setDrawMode(CellState newDrawMode) {
        drawMode = newDrawMode;
        this.infoBar.setDrawMode(newDrawMode);
    }
}
