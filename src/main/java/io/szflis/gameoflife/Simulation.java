package io.szflis.gameoflife;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.CellState;
import io.szflis.gameoflife.model.SimulationRule;

public class Simulation {

    private Board simulationBoard;
    private final SimulationRule simulationRule;

    public Simulation(Board simulationBoard, SimulationRule simulationRule) {
        this.simulationBoard = simulationBoard;
        this.simulationRule = simulationRule;
    }

    public void step() {
        Board nextState = simulationBoard.copy();

        for (int y = 0; y < simulationBoard.getWidth(); y++) {
            for (int x = 0; x < simulationBoard.getHeight(); x++) {
                CellState newState = simulationRule.getNextState(x,y,simulationBoard);
                nextState.setState(x,y,newState);
            }
        }

        this.simulationBoard = nextState;
    }

    public Board getBoard() {
        return simulationBoard;
    }
}