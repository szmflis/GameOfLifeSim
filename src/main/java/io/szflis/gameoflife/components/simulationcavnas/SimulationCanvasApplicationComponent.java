package io.szflis.gameoflife.components.simulationcavnas;

import io.szflis.app.event.EventBus;
import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;
import io.szflis.gameoflife.model.drawlayer.DrawLayersState;
import io.szflis.gameoflife.view.SimulationCanvas;

public class SimulationCanvasApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext applicationContext) {
        EventBus eventBus = applicationContext.getEventBus();
        DrawLayersState drawLayersState = applicationContext.getStateRegistry().getState(DrawLayersState.class);
        SimulationCanvas simulationCanvas = new SimulationCanvas(eventBus, drawLayersState);
        applicationContext.getMainView().setCenter(simulationCanvas);
        System.out.println("Simulation canvas");
    }

    @Override
    public void initState(ApplicationContext applicationContext) {

    }
}
