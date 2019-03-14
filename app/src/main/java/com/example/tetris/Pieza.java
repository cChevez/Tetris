package com.example.tetris;

import android.graphics.Canvas;

public interface Pieza {
    Bloque[] getPieza();
    void setPieza(Bloque[] pieza);
    void draw(Canvas canvas);
    void turn(Board board);
    boolean isActive();
    void setActive(boolean active);
    boolean isFixed();
    void setFixed(boolean fixed);
}
