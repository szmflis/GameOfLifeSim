package io.szflis.gameoflife.model.drawlayer;

import io.szflis.gameoflife.view.InvalidationListener;
import javafx.scene.canvas.GraphicsContext;

public interface DrawLayer {

    void draw(GraphicsContext g);

    int getLayer();

    void addInvalidationListener(InvalidationListener listener);

}
