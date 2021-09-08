package io.szflis;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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

    private Canvas canvas;
    private Simulation simulation;
    private Affine affine;

    private InfoBar infoBar;

    private int drawMode = Simulation.ALIVE;

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

        this.simulation = new Simulation(10,10);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.drawMode = Simulation.ALIVE;
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.drawMode = Simulation.DEAD;
        }
    }

    private void handleDraw(MouseEvent mouseEvent) {
        Point2D simCoord = getPointSimCoords(mouseEvent);
        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.simulation.setState(simX, simY, drawMode);
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

        for (int x = 0; x < this.simulation.width; x++) {
            for (int y = 0; y < this.simulation.height; y++) {
                if (this.simulation.getState(x,y) == Simulation.ALIVE) {
                    gc.fillRect(x,y,1,1);
                }
            }
        }

        gc.setStroke(Color.GREY);
        gc.setLineWidth(0.05f);
        for (int x = 0; x <= this.simulation.width; x++) {
            gc.strokeLine(x,0,x,10);
        }

        for (int y = 0; y <= this.simulation.width; y++) {
            gc.strokeLine(0,y,10,y);
        }
    }

    // probably to delete

    public Simulation getSimulation() {
        return simulation;
    }

    public void setDrawMode(int newDrawMode) {
        drawMode = newDrawMode;
        this.infoBar.setDrawMode(newDrawMode);
    }
}
