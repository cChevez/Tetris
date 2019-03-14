package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class PiezaNave1 implements Pieza{
    private Bloque[] pieza;
    private int state;
    private boolean active;
    private boolean fixed;

    public  PiezaNave1(Context context, View view, int size, int centX0, int centY0){
        pieza = new Bloque[4];
        pieza[0] = new Bloque(4,0,5,size,centX0,centY0,true,view,context);
        pieza[1] = new Bloque(5,0,5,size,centX0,centY0,true,view,context);
        pieza[2] = new Bloque(5,1,5,size,centX0,centY0,true,view,context);
        pieza[3] = new Bloque(6,1,5,size,centX0,centY0,true,view,context);
        state=0;
        active=true;
        fixed=false;
    }

    public Bloque[] getPieza() {
        return pieza;
    }

    public void setPieza(Bloque[] pieza) {
        this.pieza = pieza;
    }

    public void draw(Canvas canvas) {
        pieza[0].drawBloque(canvas);
        pieza[1].drawBloque(canvas);
        pieza[2].drawBloque(canvas);
        pieza[3].drawBloque(canvas);
    }

    public void turn(Board board) {
        switch (state){
            case 0: {
                if (pieza[0].getY() + 2 < 20 &&
                        pieza[1].getX() - 1 > -1 &&
                        pieza[1].getY() + 1 < 20 &&
                        pieza[3].getX() - 1 > -1 &&
                        !board.objeto(pieza[0].getX(), pieza[0].getY() + 2).isActiveBlock() &&
                        !board.objeto(pieza[1].getX() - 1, pieza[1].getY() + 1).isActiveBlock() &&
                        !board.objeto(pieza[2].getX(), pieza[2].getY()).isActiveBlock() &&
                        !board.objeto(pieza[3].getX() - 1, pieza[3].getY() - 1).isActiveBlock()) {
                    pieza[0].setX(pieza[0].getX());
                    pieza[0].setY(pieza[0].getY() + 2);
                    pieza[1].setX(pieza[1].getX() - 1);
                    pieza[1].setY(pieza[1].getY() + 1);
                    pieza[2].setX(pieza[2].getX());
                    pieza[2].setY(pieza[2].getY());
                    pieza[3].setX(pieza[3].getX() - 1);
                    pieza[3].setY(pieza[3].getY() - 1);
                    state = 1;
                }
                break;
            }
            case 1: {
                if (pieza[1].getX() + 1 < 10 &&
                        pieza[3].getX() + 1 < 10 &&
                        pieza[3].getY() + 1 < 20 &&
                        !board.objeto(pieza[0].getX(), pieza[0].getY() - 2).isActiveBlock() &&
                        !board.objeto(pieza[1].getX() + 1, pieza[1].getY() - 1).isActiveBlock() &&
                        !board.objeto(pieza[2].getX(), pieza[2].getY()).isActiveBlock() &&
                        !board.objeto(pieza[3].getX() + 1, pieza[3].getY() + 1).isActiveBlock()) {
                    pieza[0].setX(pieza[0].getX());
                    pieza[0].setY(pieza[0].getY() - 2);
                    pieza[1].setX(pieza[1].getX() + 1);
                    pieza[1].setY(pieza[1].getY() - 1);
                    pieza[2].setX(pieza[2].getX());
                    pieza[2].setY(pieza[2].getY());
                    pieza[3].setX(pieza[3].getX() + 1);
                    pieza[3].setY(pieza[3].getY() + 1);
                    state = 2;
                }
                break;
            }case 2: {
                if (pieza[0].getY() + 2 < 20 &&
                        pieza[1].getX() - 1 > -1 &&
                        pieza[1].getY() + 1 < 20 &&
                        pieza[3].getX() - 1 > -1 &&
                        !board.objeto(pieza[0].getX(), pieza[0].getY() + 2).isActiveBlock() &&
                        !board.objeto(pieza[1].getX() - 1, pieza[1].getY() + 1).isActiveBlock() &&
                        !board.objeto(pieza[2].getX(), pieza[2].getY()).isActiveBlock() &&
                        !board.objeto(pieza[3].getX() -1, pieza[3].getY() - 1).isActiveBlock()) {
                    pieza[0].setX(pieza[0].getX());
                    pieza[0].setY(pieza[0].getY() + 2);
                    pieza[1].setX(pieza[1].getX() - 1);
                    pieza[1].setY(pieza[1].getY() + 1);
                    pieza[2].setX(pieza[2].getX());
                    pieza[2].setY(pieza[2].getY());
                    pieza[3].setX(pieza[3].getX() - 1);
                    pieza[3].setY(pieza[3].getY() - 1);
                    state = 3;
                }
                break;
            }case 3: {
                if (pieza[1].getX() + 1 < 10 &&
                        pieza[3].getX() + 1 < 10 &&
                        pieza[3].getY() + 1 < 20 &&
                        !board.objeto(pieza[0].getX(), pieza[0].getY() - 2).isActiveBlock() &&
                        !board.objeto(pieza[1].getX() + 1, pieza[1].getY() - 1).isActiveBlock() &&
                        !board.objeto(pieza[2].getX(), pieza[2].getY()).isActiveBlock() &&
                        !board.objeto(pieza[3].getX() + 1, pieza[3].getY() + 1).isActiveBlock()) {
                    pieza[0].setX(pieza[0].getX());
                    pieza[0].setY(pieza[0].getY() - 2);
                    pieza[1].setX(pieza[1].getX() + 1);
                    pieza[1].setY(pieza[1].getY() - 1);
                    pieza[2].setX(pieza[2].getX());
                    pieza[2].setY(pieza[2].getY());
                    pieza[3].setX(pieza[3].getX() + 1);
                    pieza[3].setY(pieza[3].getY() + 1);
                    state = 0;
                }
                break;
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
