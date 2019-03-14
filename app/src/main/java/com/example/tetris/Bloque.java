package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Bloque {
    private Drawable drawable;
    private int x,y;
    private int tipo;
    private boolean activeBlock;
    private View view;


    private int cenX, cenY;
    private int ancho, alto;
    private int xAnterior, yAnterior;
    private int radioInval;

    public Bloque(int bx, int by, int btipo, int btam,int centX0, int centY0, boolean bactivo, View view, Context context){
        x=bx;
        y=by;
        tipo=btipo;
        ancho = btam;
        alto = btam;
        cenX = (x * btam +centX0);
        cenY = (y * btam +centY0);
        activeBlock=bactivo;
        this.view = view;
        radioInval = (int) Math.hypot(ancho/2, alto/2);
        switch (tipo){
            case 1:
            {
                drawable = resizeImage(context,R.drawable.amarillo,btam,btam);
                break;
            }
            case 2:
            {
                drawable = resizeImage(context,R.drawable.azul,btam,btam);
                break;
            }
            case 3:
            {
                drawable = resizeImage(context,R.drawable.naranja,btam,btam);
                break;
            }
            case 4:
            {
                drawable = resizeImage(context,R.drawable.lila,btam,btam);
                break;
            }
            case 5:
            {
                drawable = resizeImage(context,R.drawable.verde,btam,btam);
                break;
            }
            case 6:
            {
                drawable = resizeImage(context,R.drawable.rojo,btam,btam);
                break;
            }
            default:{
            }
        }
    }

    public Bloque(int bx, int by){
        x = bx;
        y = by;
        tipo = 0;
        activeBlock = false;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        cenX = ((x-this.x)*ancho+cenX);
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y) {
        cenY = ((y-this.y) * alto +cenY);
        this.y = y;
    }

    public boolean isActiveBlock(){
        return activeBlock;
    }

    public void setActiveBlock(boolean activeBlock){
        this.activeBlock = activeBlock;
    }

    public void drawBloque(Canvas canvas){
        int x = cenX - ancho/2;
        int y = cenY - alto/2;
        drawable.setBounds(x, y, x+ancho, y+alto);
        canvas.save();
        drawable.draw(canvas);
        canvas.restore();
        view.invalidate(cenX-radioInval, cenY-radioInval,
                cenX+radioInval, cenY+radioInval);
        view.invalidate(xAnterior-radioInval, yAnterior-radioInval,
                xAnterior+radioInval, yAnterior+radioInval);
        xAnterior = cenX;
        yAnterior = cenY;
    }

    public static Drawable resizeImage(Context context, int resId, int weight, int height){
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId);
        Bitmap resized = Bitmap.createScaledBitmap(bm, weight, height, true);
        //noinspection deprecation
        return new BitmapDrawable(resized);
    }
}
