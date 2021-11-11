package io.szflis.gameoflife.components.simulationcavnas;

import io.szflis.app.event.EventBus;
import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;
import io.szflis.gameoflife.components.board.BoardState;
import io.szflis.gameoflife.components.resizer.CanvasResizeEvent;
import io.szflis.gameoflife.model.drawlayer.DrawLayersState;

public class SimulationCanvasApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext applicationContext) {
        EventBus eventBus = applicationContext.getEventBus();
        DrawLayersState drawLayersState = applicationContext.getStateRegistry().getState(DrawLayersState.class);
        SimulationCanvasState canvasState = applicationContext.getStateRegistry().getState(SimulationCanvasState.class);
        BoardState boardState = applicationContext.getStateRegistry().getState(BoardState.class);
        SimulationCanvas simulationCanvas = new SimulationCanvas(eventBus, drawLayersState, canvasState, boardState);

        eventBus.listenFor(CanvasResizeEvent.class, event -> {
            simulationCanvas.resizeCanvasWindow(boardState.getBoard().get().getWidth(), boardState.getBoard().get().getHeight());
        });

        applicationContext.getMainView().setCenter(simulationCanvas);
    }

    @Override
    public void initState(ApplicationContext applicationContext) {
        SimulationCanvasState simulationCanvasState = new SimulationCanvasState(
                20, applicationContext.getBoardWidth(), applicationContext.getBoardHeight());
        applicationContext.getStateRegistry().registerState(SimulationCanvasState.class, simulationCanvasState);
    }
}
