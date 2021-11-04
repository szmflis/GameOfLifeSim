package io.szflis.gameoflife;

import io.szflis.gameoflife.logic.*;
import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.logic.Simulator;
import io.szflis.gameoflife.util.event.EventBus;
import io.szflis.gameoflife.view.InfoBar;
import io.szflis.gameoflife.view.MainView;
import io.szflis.gameoflife.view.SimulationCanvas;
import io.szflis.gameoflife.view.Toolbar;
import io.szflis.gameoflife.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();
        Board board = new BoundedBoard(20,13);

        ApplicationStateManager applicationStateManager = new ApplicationStateManager();
        BoardViewModel boardViewModel = new BoardViewModel();

        Editor editor = new Editor(board);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);

        editor.getCursorPosition().listen(cursorPosition -> {
            // every time cursor position changes in the editor do this
            // one-way bind
            boardViewModel.getCursorPosition().set(cursorPosition);
        });

        Simulator simulator = new Simulator(applicationStateManager);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);

        editor.getBoard().listen(editorBoard -> {
            simulator.getInitialBoard().set(editorBoard);
            boardViewModel.getBoard().set(editorBoard);
        });

        simulator.getCurrentBoard().listen(simulationBoard -> {
            boardViewModel.getBoard().set(simulationBoard);
        });

        applicationStateManager.getApplicationState().listen(editor::onAppStateChanged);
        boardViewModel.getBoard().set(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);
        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
        editor.getCursorPosition().listen(cursorPosition -> {
            infoBarViewModel.getCursorPosition().set(cursorPosition);
        });
        editor.getDrawMode().listen(drawMode -> {
            infoBarViewModel.getCurrentDrawMode().set(drawMode);
        });
        InfoBar infoBar = new InfoBar(infoBarViewModel);

        MainView mainView = new MainView(eventBus);
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