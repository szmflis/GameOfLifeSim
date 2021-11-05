package io.szflis.gameoflife.logic.simulator;

import io.szflis.gameoflife.command.Command;
import io.szflis.gameoflife.state.SimulatorState;

public interface SimulatorCommand extends Command<SimulatorState> {
    @Override
    void execute(SimulatorState simulatorState);

    @Override
    default Class<SimulatorState> getStateClass() {
        return SimulatorState.class;
    }
}
