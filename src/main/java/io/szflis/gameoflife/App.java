package io.szflis.gameoflife;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Board board = new BoundedBoard(10,10);
        ApplicationViewModel applicationViewModel
                = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel
                = new BoardViewModel();
        EditorViewModel editorViewModel
                = new EditorViewModel(boardViewModel, board);
        SimulationViewModel simulationViewModel
                = new SimulationViewModel(boardViewModel);

        applicationViewModel.listenToApplicationState(editorViewModel::onAppStateChanged);
        applicationViewModel.listenToApplicationState(simulationViewModel::onAppStateChange);

        MainView mainView = new MainView(
                applicationViewModel,
                boardViewModel,
                editorViewModel,
                simulationViewModel);

        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();

        boardViewModel.setBoard(board);
    }

    public static void main(String[] args) {
        launch();
    }
}