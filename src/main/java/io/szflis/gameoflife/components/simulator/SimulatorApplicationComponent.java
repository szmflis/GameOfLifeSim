package io.szflis.gameoflife.components.simulator;

import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;
import io.szflis.gameoflife.components.board.BoardState;
import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.components.editor.EditorState;

public class SimulatorApplicationComponent implements ApplicationComponent  {


    @Override
    public void initComponent(ApplicationContext applicationContext) {
        SimulatorState simulatorState
                = applicationContext.getStateRegistry().getState(SimulatorState.class);
        EditorState editorState = applicationContext.getStateRegistry().getState(EditorState.class);
        BoardState boardState = applicationContext.getStateRegistry().getState(BoardState.class);


        Simulator simulator = new Simulator(simulatorState, applicationContext.getCommandExecutor());
        applicationContext.getEventBus().listenFor(SimulatorEvent.class, simulator::handle);

        editorState.getEditingBoard().listen(simulatorState.getBoard()::set);

        simulatorState.getBoard().listen(simulationBoard -> {
            if (simulatorState.getSimulating().get()) {
                boardState.getBoard().set(simulationBoard);
            }
        });
    }

    @Override
    public void initState(ApplicationContext applicationContext) {
        Board board = new BoundedBoard(applicationContext.getBoardWidth(), applicationContext.getBoardHeight());
        SimulatorState simulatorState = new SimulatorState(board);
        applicationContext.getStateRegistry().registerState(SimulatorState.class, simulatorState);
    }
}
