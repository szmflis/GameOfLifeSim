package io.szflis.gameoflife.model;

public class CellPosition {
    private int x;
    private int y;

    public CellPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
