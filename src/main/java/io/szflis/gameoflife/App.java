package io.szflis.gameoflife;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.util.event.EventBus;
import io.szflis.gameoflife.view.SimulationCanvas;
import io.szflis.gameoflife.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();
        Board board = new BoundedBoard(20,13);

        ApplicationViewModel applicationViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();

        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, board);
        eventBus.listenFor(DrawModeEvent.class, editorViewModel::handle);
        eventBus.listenFor(BoardEvent.class, editorViewModel::handle);

        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel, applicationViewModel, editorViewModel);
        eventBus.listenFor(SimulatorEvent.class, simulationViewModel::handle);

        applicationViewModel.getApplicationState().listen(editorViewModel::onAppStateChanged);
        boardViewModel.getBoard().set(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(editorViewModel, boardViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);
        InfoBar infoBar = new InfoBar(editorViewModel);

        MainView mainView = new MainView(editorViewModel);
        mainView.setTop(toolbar);
        mainView.setCenter(simulationCanvas);
        mainView.setBottom(infoBar);

        Scene scene = new Scene(mainView, 1200, 800);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}