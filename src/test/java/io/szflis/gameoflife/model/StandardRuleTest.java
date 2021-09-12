package io.szflis.gameoflife.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class StandardRuleTest {

    private Board board;
    private SimulationRule simulationRule;

    @BeforeEach
    void setUp() {
        board = new BoundedBoard(3,3);
        simulationRule = new StandardRule();
    }

    @Test
    void getNextState_alive_noNeighbours() {
        board.setState(1,1,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_alive_oneNeighbour() {
        board.setState(1,1,CellState.ALIVE);
        board.setState(1,0,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_alive_twoNeighbours() {
        board.setState(1,1,CellState.ALIVE);
        board.setState(1,0,CellState.ALIVE);
        board.setState(0,0,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.ALIVE, nextState);
    }

    @Test
    void getNextState_alive_threeNeighbours() {
        board.setState(1,1,CellState.ALIVE);

        board.setState(1,0,CellState.ALIVE);
        board.setState(0,0,CellState.ALIVE);
        board.setState(2,2,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.ALIVE, nextState);
    }

    @Test
    void getNextState_alive_fourNeighbours() {
        board.setState(1,1,CellState.ALIVE);

        board.setState(1,0,CellState.ALIVE);
        board.setState(0,0,CellState.ALIVE);
        board.setState(2,2,CellState.ALIVE);
        board.setState(2,1,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_alive_eightNeighbours() {
        board.setState(1,1,CellState.ALIVE);

        board.setState(0,0,CellState.ALIVE);
        board.setState(0,1,CellState.ALIVE);
        board.setState(0,2,CellState.ALIVE);
        board.setState(1,0,CellState.ALIVE);
        board.setState(1,2,CellState.ALIVE);
        board.setState(2,0,CellState.ALIVE);
        board.setState(2,1,CellState.ALIVE);
        board.setState(2,2,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_noNeighbours() {
        board.setState(1,1,CellState.DEAD);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_oneNeighbours() {
        board.setState(1,1,CellState.DEAD);

        board.setState(1,0,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_twoNeighbours() {
        board.setState(1,1,CellState.DEAD);

        board.setState(1,0,CellState.ALIVE);
        board.setState(2,0,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_threeNeighbours() {
        board.setState(1,1,CellState.DEAD);

        board.setState(1,0,CellState.ALIVE);
        board.setState(2,0,CellState.ALIVE);
        board.setState(0,0,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.ALIVE, nextState);
    }

    @Test
    void getNextState_dead_fourNeighbours() {
        board.setState(1,1,CellState.DEAD);

        board.setState(1,0,CellState.ALIVE);
        board.setState(2,0,CellState.ALIVE);
        board.setState(0,0,CellState.ALIVE);
        board.setState(2,1,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

    @Test
    void getNextState_dead_eightNeighbours() {
        board.setState(1,1,CellState.DEAD);

        board.setState(1,0,CellState.ALIVE);
        board.setState(2,0,CellState.ALIVE);
        board.setState(0,0,CellState.ALIVE);
        board.setState(2,1,CellState.ALIVE);
        board.setState(0,1,CellState.ALIVE);
        board.setState(0,2,CellState.ALIVE);
        board.setState(1,2,CellState.ALIVE);
        board.setState(2,2,CellState.ALIVE);

        CellState nextState = simulationRule.getNextState(1,1, board);

        assertEquals(CellState.DEAD, nextState);
    }

}