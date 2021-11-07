package io.szflis.gameoflife.logic.simulator;

import io.szflis.app.command.Command;

public interface SimulatorCommand extends Command<SimulatorState> {
    @Override
    void execute(SimulatorState simulatorState);

    @Override
    default Class<SimulatorState> getStateClass() {
        return SimulatorState.class;
    }
}
