module org.example {
    requires javafx.controls;
    exports io.szflis.gameoflife;
    exports io.szflis.gameoflife.model;
    exports io.szflis.gameoflife.view;
    exports io.szflis.gameoflife.components.editor;
    exports io.szflis.gameoflife.components.simulator;
    exports io.szflis.app.observable;
    exports io.szflis.gameoflife.components.infobar;
    exports io.szflis.gameoflife.components.toolbar;
    exports io.szflis.gameoflife.model.drawlayer;
    exports io.szflis.gameoflife.components.simulationcavnas;
}