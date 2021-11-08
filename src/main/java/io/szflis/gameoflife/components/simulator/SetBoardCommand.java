package io.szflis.gameoflife.components.simulator;

import io.szflis.gameoflife.model.Simulation;

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
