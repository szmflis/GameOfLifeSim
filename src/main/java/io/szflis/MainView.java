package io.szflis;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Button stepButton;
    private Canvas canvas;
    private Simulation simulation;
    private Affine affine;

    private int drawMode = 1;

    public MainView() {
        this.stepButton = new Button("step");

        this.stepButton.setOnAction(actionEvent -> {
            simulation.step();
            draw();
        });


        this.canvas = new Canvas(400, 400);
        //adding clicability
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        this.setOnKeyPressed(this::onKeyPressed);

        this.getChildren().addAll(this.stepButton, this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(400/10f, 400/10f);

        this.simulation = new Simulation(10,10);



        //        this.simulation.setAlive(5,5);
//        this.simulation.setAlive(5,6);
//        this.simulation.setAlive(6,5);
//
//        this.simulation.setAlive(2,2);
//        this.simulation.setAlive(2,3);
//        this.simulation.setAlive(2,4);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D) {
            this.drawMode = 1;
        } else if (keyEvent.getCode() == KeyCode.E) {
            this.drawMode = 0;
        }
    }

    private void handleDraw(MouseEvent mouseEvent) {
        //simulation coords - 0 -> 400
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY); // convas cord -> sim cord
            int simX = (int) simCoord.getX();
            int simY = (int) simCoord.getY();

            this.simulation.setState(simX, simY, drawMode);

            draw();
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
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
                if (this.simulation.getState(x,y) == 1) {
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
}
