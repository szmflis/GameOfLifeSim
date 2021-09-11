package io.szflis.gameoflife;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.model.StandardRule;
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

    public static final int EDITING = 0;
    public static final int SIMULATING = 1;


    private Simulation simulation;
    private Board initialBoard;

    private Canvas canvas;
    private Affine affine;
    private InfoBar infoBar;
    private CellState drawMode = CellState.ALIVE;
    private int applicationState = EDITING;


    public MainView() {
        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this);
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

        this.initialBoard = new BoundedBoard(10,10);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.drawMode = CellState.ALIVE;
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.drawMode = CellState.DEAD;
        }
    }

    private void handleDraw(MouseEvent mouseEvent) {
        if (this.applicationState == SIMULATING) return;

        Point2D simCoord = getPointSimCoords(mouseEvent);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.initialBoard.setState(simX, simY, drawMode);
        draw();
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

    public void draw() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);
        
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,400,400);

        gc.setFill(Color.BLACK);

        if (applicationState == EDITING) {
            drawSimulation(this.initialBoard);
        } else {
            drawSimulation(this.simulation.getBoard());
        }

        gc.setStroke(Color.GREY);
        gc.setLineWidth(0.05f);
        for (int x = 0; x <= this.initialBoard.getWidth(); x++) {
            gc.strokeLine(x,0,x,10);
        }

        for (int y = 0; y <= this.initialBoard.getHeight(); y++) {
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

    public void setApplicationState(int applicationState) {
        if (applicationState == this.applicationState) return;

        this.applicationState = applicationState;
        if (applicationState == SIMULATING) {
            this.simulation = new Simulation(this.initialBoard, new StandardRule());
        }

        System.out.println(this.applicationState);
    }

    // probably to delete

    public Simulation getSimulation() {
        return simulation;
    }

    public void setDrawMode(CellState newDrawMode) {
        drawMode = newDrawMode;
        this.infoBar.setDrawMode(newDrawMode);
    }

    public int getApplicationState() {
        return applicationState;
    }
}
