package io.szflis.gameoflife;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.viewmodel.ApplicationViewModel;
import io.szflis.gameoflife.viewmodel.BoardViewModel;
import io.szflis.gameoflife.viewmodel.EditorViewModel;
import io.szflis.gameoflife.viewmodel.SimulationViewModel;
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

    private EditorViewModel editorViewModel;
    private BoardViewModel boardViewModel;

    private Canvas canvas;
    private Affine affine;
    private InfoBar infoBar;


    public MainView(ApplicationViewModel applicationViewModel,
                    BoardViewModel boardViewModel,
                    EditorViewModel editorViewModel,
                    SimulationViewModel simulationViewModel) {
        this.boardViewModel = boardViewModel;
        this.editorViewModel = editorViewModel;
        this.boardViewModel.listenToBoard(this::onBoardChanged);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(editorViewModel, applicationViewModel, simulationViewModel);
        this.infoBar = new InfoBar(editorViewModel);
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

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.editorViewModel.setDrawMode(CellState.ALIVE);
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.editorViewModel.setDrawMode(CellState.DEAD);
        }
    }

    private void handleDraw(MouseEvent mouseEvent) {

        Point2D simCoord = getPointSimCoords(mouseEvent);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.editorViewModel.boardPressed(simX, simY);
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

}
