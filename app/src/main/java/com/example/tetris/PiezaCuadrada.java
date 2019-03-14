package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class PiezaCuadrada implements Pieza {
    private Bloque[] pieza;
    private int state;
    private boolean active;
    private boolean fixed;

    public PiezaCuadrada(Context context, View view, int size, int centX0, int centY0){
        pieza = new Bloque[4];
        pieza[0] = new Bloque(4,0,1,size,centX0,centY0,true,view,context);
        pieza[1] = new Bloque(5,0,1,size,centX0,centY0,true,view,context);
        pieza[2] = new Bloque(4,1,1,size,centX0,centY0,true,view,context);
        pieza[3] = new Bloque(5,1,1,size,centX0,centY0,true,view,context);
        state=0;
        active=true;
        fixed=false;
    }

    public Bloque[] getPieza() {
        return  pieza;
    }

    public void setPieza(Bloque[] pieza){
        this.pieza = pieza;
    }

    public void draw(Canvas canvas){
        pieza[0].drawBloque(canvas);
        pieza[1].drawBloque(canvas);
        pieza[2].drawBloque(canvas);
        pieza[3].drawBloque(canvas);
    }

    public void turn(Board board){
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean activa){
        this.active = activa;
    }

    public boolean isFixed(){
        return fixed;
    }

    public void setFixed(boolean fija){
        this.fixed = fija;
    }
}
