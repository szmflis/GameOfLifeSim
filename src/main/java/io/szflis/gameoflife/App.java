package io.szflis.gameoflife;

import io.szflis.gameoflife.model.Board;
import io.szflis.gameoflife.model.BoundedBoard;
import io.szflis.gameoflife.viewmodel.ApplicationState;
import io.szflis.gameoflife.viewmodel.ApplicationViewModel;
import io.szflis.gameoflife.viewmodel.BoardViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationViewModel applicationViewModel
                = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel
                = new BoardViewModel();
        Board board = new BoundedBoard(10,10);

        MainView mainView = new MainView(applicationViewModel, boardViewModel, board);

        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();

        boardViewModel.setBoard(board);
    }

    public static void main(String[] args) {
        launch();
    }
}