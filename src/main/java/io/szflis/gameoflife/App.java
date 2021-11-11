package io.szflis.gameoflife;

import io.szflis.app.command.CommandExecutor;
import io.szflis.app.event.EventBus;
import io.szflis.app.state.StateRegistry;
import io.szflis.gameoflife.components.board.BoardApplicationComponent;
import io.szflis.gameoflife.components.editor.EditorApplicationComponent;
import io.szflis.gameoflife.components.infobar.InfoBarApplicationComponent;
import io.szflis.gameoflife.components.resizer.CanvasResizerApplicationComponent;
import io.szflis.gameoflife.components.simulationcavnas.SimulationCanvasApplicationComponent;
import io.szflis.gameoflife.components.simulator.SimulatorApplicationComponent;
import io.szflis.gameoflife.components.toolbar.ToolbarApplicationComponent;
import io.szflis.gameoflife.model.drawlayer.DrawLayersState;
import io.szflis.gameoflife.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        EventBus eventBus = new EventBus();
        StateRegistry stateRegistry = new StateRegistry();
        CommandExecutor commandExecutor = new CommandExecutor(stateRegistry);

        DrawLayersState drawLayersState = new DrawLayersState();
        stateRegistry.registerState(DrawLayersState.class, drawLayersState);

        MainView mainView = new MainView(eventBus, commandExecutor);

        ApplicationContext applicationContext = new ApplicationContext(
                eventBus, commandExecutor, stateRegistry, mainView, 20, 20);

        List<ApplicationComponent> components = new LinkedList<>();
        components.add(new EditorApplicationComponent());
        components.add(new SimulatorApplicationComponent());
        components.add(new BoardApplicationComponent());
        components.add(new InfoBarApplicationComponent());
        components.add(new CanvasResizerApplicationComponent());
        components.add(new ToolbarApplicationComponent());
        components.add(new SimulationCanvasApplicationComponent());

        for (ApplicationComponent component : components) {
            component.initState(applicationContext);
        }
        for (ApplicationComponent component : components) {
            component.initComponent(applicationContext);
        }

        Scene scene = new Scene(mainView, 1920, 1080);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
