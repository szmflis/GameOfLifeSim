package io.szflis.gameoflife.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {

    @Test
    void simulatesEntireBounds() {
        Board board = new BoundedBoard(5,3);
        board.setState(0,0, CellState.ALIVE);
        board.setState(4,0, CellState.ALIVE);
        board.setState(4,2, CellState.ALIVE);
        board.setState(0,2, CellState.ALIVE);
        Simulation simulation = new Simulation(board, new StandardRule());

        simulation.step();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getWidth(); y++) {
                assertEquals(CellState.DEAD, simulation.getBoard().getState(x,y));
            }
        }
    }

}
