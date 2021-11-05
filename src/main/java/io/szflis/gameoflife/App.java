package io.szflis.gameoflife;

import io.szflis.gameoflife.logic.*;
import io.szflis.gameoflife.logic.editor.BoardEvent;
import io.szflis.gameoflife.logic.editor.DrawModeEvent;
import io.szflis.gameoflife.logic.editor.Editor;
import io.szflis.gameoflife.logic.simulator.SimulatorEvent;
import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.logic.simulator.Simulator;
import io.szflis.gameoflife.state.EditorState;
import io.szflis.gameoflife.state.SimulatorState;
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

        EditorState editorState = new EditorState(board);
        Editor editor = new Editor(editorState);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);

        editorState.getCursorPosition().listen(cursorPosition -> {
            // every time cursor position changes in the editor do this
            // one-way bind
            boardViewModel.getCursorPosition().set(cursorPosition);
        });

        SimulatorState simulatorState = new SimulatorState(board);
        Simulator simulator = new Simulator(applicationStateManager, simulatorState);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);

        editorState.getEditingBoard().listen(editorBoard -> {
            simulatorState.getBoard().set(editorBoard);
            boardViewModel.getBoard().set(editorBoard);
        });

        simulatorState.getBoard().listen(simulationBoard -> {
            boardViewModel.getBoard().set(simulationBoard);
        });

        applicationStateManager.getApplicationState().listen(editor::onAppStateChanged);
        applicationStateManager.getApplicationState().listen(newState -> {
            if (newState == ApplicationState.EDITING) {
                simulatorState.getBoard().set(editorState.getEditingBoard().get());
                boardViewModel.getBoard().set(editorState.getEditingBoard().get());
            }
        });
        boardViewModel.getBoard().set(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);
        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();

        editorState.getCursorPosition().listen(cursorPosition -> {
            infoBarViewModel.getCursorPosition().set(cursorPosition);
        });

        editorState.getDrawMode().listen(drawMode -> {
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