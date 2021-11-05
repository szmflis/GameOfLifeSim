package io.szflis.gameoflife.logic.simulator;

import io.szflis.gameoflife.model.Simulation;
import io.szflis.gameoflife.state.SimulatorState;

public class SetBoardCommand implements SimulatorCommand {

    private Simulation simulation;

    public SetBoardCommand(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void execute(SimulatorState simulatorState) {
        simulatorState.getBoard().set(simulation.getBoard());
    }

}
