package com.example.tetris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Tetris extends View {
    private Board board;
    int lvl;
    private hilo thread = new hilo();
    private static int CAMBIO = 50;
    private long lastProcess = 0;
    private long lastDesc = 0;
    int lado;
    Pieza pieza;
    int baseX, baseY;
    int centX0, centY0;
    Context contexto;

    private TextView textLvl, textPoints;

    private int points = 0;

    public Tetris(Context context, AttributeSet attribSet){
        super(context,attribSet);
        contexto = context;
        board = new Board(this);
        lvl = 1;
        points = 0;
    }

    @Override
    protected  void onSizeChanged(int ancho, int alto, int ancho_anterior, int alto_anterior){
        super.onSizeChanged(ancho,alto,ancho_anterior,alto_anterior);
        int bloque_alto = alto/20;
        int bloque_ancho = ancho/10;
        int centroX=ancho/2;
        int centroY=alto/2;
        if(bloque_alto>bloque_ancho){
            lado = bloque_ancho;
        }else{
            lado = bloque_alto;
        }
        baseX = centroX -(5*lado);
        baseY = centroY - (10*lado);
        centX0 = baseX+(lado/2);
        centY0 = baseY+(lado/2);

        int random = (int)(Math.random()*6);
        switch (random){
            case 0:
            {
                pieza = new PiezaCuadrada(contexto,this,lado,centX0,centY0);
                break;
            }
            case 1:
            {
                pieza = new PiezaBarra(contexto,this,lado,centX0,centY0);
                break;
            }
            case 2:
            {
                pieza = new PiezaEleIzquierda(contexto,this,lado,centX0,centY0);
                break;
            }
            case 3:
            {
                pieza = new PiezaEleDerecha(contexto,this,lado,centX0,centY0);
                break;
            }
            case 4:
            {
                pieza = new PiezaNave1(contexto,this,lado,centX0,centY0);
                break;
            }
            case 5:
            {
                pieza = new PiezaNave2(contexto,this,lado,centX0,centY0);
                break;
            }
            default:{

            }
        }

        lastProcess = System.currentTimeMillis();
        lastDesc = System.currentTimeMillis();
        thread.start();

        textLvl = ((Activity)contexto).findViewById(R.id.lvlLabel);
        textPoints = ((Activity)contexto).findViewById(R.id.pointsLabel);

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                ((Activity)contexto).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textLvl.setText(String.valueOf(lvl));
                        textPoints.setText(String.valueOf(points));
                    }
                });
            }
        }, 0, 1000);
    }

    class hilo extends Thread{
        private boolean pause, running;
        public synchronized void pausar(){
            pause = true;
        }
        public synchronized void reanudar(){
            pause = false;
            notify();
        }
        public void detener(){
            running = false;
            if(pause){
                reanudar();
            }
        }
        @Override
        public void run(){
            running = true;
            while(running){
                updateGame();
                synchronized (this){
                    while (pause){
                        try {
                            wait();
                        }catch (Exception e){

                        }
                    }
                }
            }
        }
    }

    public hilo getThread(){
        return thread;
    }

    protected  void updateGame(){
        long time = System.currentTimeMillis();
        if(lastProcess + CAMBIO > time){
            return;
        }
        lastProcess = time;
        if(time>lastDesc + 1000 - (lvl*10)){
            downPieza(pieza);
            lastDesc = time;
            if(pieza.isFixed()){
                int random = (int) (Math.random() * 6);
                switch (random) {
                    case 0: {
                        pieza = new PiezaCuadrada(contexto, this, lado, centX0, centY0);
                        break;
                    }
                    case 1: {
                        pieza = new PiezaBarra(contexto, this, lado, centX0, centY0);
                        break;
                    }
                    case 2: {
                        pieza = new PiezaEleIzquierda(contexto, this, lado, centX0, centY0);
                        break;
                    }
                    case 3: {
                        pieza = new PiezaEleDerecha(contexto, this, lado, centX0, centY0);
                        break;
                    }
                    case 4: {
                        pieza = new PiezaNave1(contexto, this, lado, centX0, centY0);
                        break;
                    }
                    case 5: {
                        pieza = new PiezaNave2(contexto, this, lado, centX0, centY0);
                        break;
                    }
                    default: {

                    }
                }
                if(board.objeto(pieza.getPieza()[0].getX(),pieza.getPieza()[0].getY()).isActiveBlock() ||
                        board.objeto(pieza.getPieza()[1].getX(),pieza.getPieza()[1].getY()).isActiveBlock() ||
                        board.objeto(pieza.getPieza()[2].getX(),pieza.getPieza()[2].getY()).isActiveBlock() ||
                        board.objeto(pieza.getPieza()[3].getX(),pieza.getPieza()[3].getY()).isActiveBlock()){
                    exit();
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        pieza.draw(canvas);
        board.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawLine(baseX-1,baseY-1,baseX-1,baseY+(20*lado)+1,paint);
        canvas.drawLine(baseX-1,baseY+(20*lado)+1,baseX+1+(10*lado),baseY+(20*lado)+1,paint);
        canvas.drawLine(baseX+1+(10*lado),baseY+(20*lado)+1,baseX+1+(10*lado),baseY-1,paint);
        canvas.drawLine(baseX+1+(10*lado),baseY-1,baseX-1,baseY-1,paint);
    }

    private Activity dad;

    public void setDad(Activity daddy){
        this.dad = daddy;
    }

    private void exit(){
        dad.finish();
    }

    private void fixPieza(Pieza p){
        board.setObjet(p.getPieza()[0].getX(),p.getPieza()[0].getY(),p.getPieza()[0]);
        board.setObjet(p.getPieza()[1].getX(),p.getPieza()[1].getY(),p.getPieza()[1]);
        board.setObjet(p.getPieza()[2].getX(),p.getPieza()[2].getY(),p.getPieza()[2]);
        board.setObjet(p.getPieza()[3].getX(),p.getPieza()[3].getY(),p.getPieza()[3]);
        p.setFixed(true);
        points = points + (board.eliminarFila() * 1000);
        lvl = 1 + (points/10000);
    }

    private void downPieza(Pieza p){
        if(!limitDown(p) && !obstacleDown(p)){
            p.getPieza()[0].setY(p.getPieza()[0].getY()+1);
            p.getPieza()[1].setY(p.getPieza()[1].getY()+1);
            p.getPieza()[2].setY(p.getPieza()[2].getY()+1);
            p.getPieza()[3].setY(p.getPieza()[3].getY()+1);
        }else{
            fixPieza(p);
        }
    }

    private void movePiezaLeft(Pieza p){
        if(!limitLeft(p) && !obstacleLeft(p)){
            p.getPieza()[0].setX(p.getPieza()[0].getX()-1);
            p.getPieza()[1].setX(p.getPieza()[1].getX()-1);
            p.getPieza()[2].setX(p.getPieza()[2].getX()-1);
            p.getPieza()[3].setX(p.getPieza()[3].getX()-1);
        }
    }

    private void movePiezaRight(Pieza p){
        if(!limitRight(p) && !obstacleRight(p)){
            p.getPieza()[0].setX(p.getPieza()[0].getX()+1);
            p.getPieza()[1].setX(p.getPieza()[1].getX()+1);
            p.getPieza()[2].setX(p.getPieza()[2].getX()+1);
            p.getPieza()[3].setX(p.getPieza()[3].getX()+1);
        }
    }

    private boolean limitLeft(Pieza p){
        if(p.getPieza()[0].getX()-1 < 0 ||
                p.getPieza()[1].getX()-1 < 0 ||
                p.getPieza()[2].getX()-1 < 0 ||
                p.getPieza()[3].getX()-1 < 0 ){
            return true;
        }else {
            return false;
        }
    }

    private boolean limitRight(Pieza p){
        if(p.getPieza()[0].getX()+1 > 9 ||
                p.getPieza()[1].getX()+1 > 9 ||
                p.getPieza()[2].getX()+1 > 9 ||
                p.getPieza()[3].getX()+1 > 9 ){
            return true;
        }else {
            return false;
        }
    }

    private boolean limitDown(Pieza p){
        if(p.getPieza()[0].getY()+1 > 19 ||
                p.getPieza()[1].getY()+1 > 19 ||
                p.getPieza()[2].getY()+1 > 19 ||
                p.getPieza()[3].getY()+1 > 19 ){
            return true;
        }else {
            return false;
        }
    }

    private boolean obstacleLeft(Pieza p){
        if(board.objeto(p.getPieza()[0].getX()-1, p.getPieza()[0].getY()).isActiveBlock() ||
                board.objeto(p.getPieza()[1].getX()-1, p.getPieza()[1].getY()).isActiveBlock() ||
                board.objeto(p.getPieza()[2].getX()-1, p.getPieza()[2].getY()).isActiveBlock() ||
                board.objeto(p.getPieza()[3].getX()-1, p.getPieza()[3].getY()).isActiveBlock()){
            return true;
        }else {
            return false;
        }
    }

    private boolean obstacleRight(Pieza p){
        if(board.objeto(p.getPieza()[0].getX()+1, p.getPieza()[0].getY()).isActiveBlock() ||
                board.objeto(p.getPieza()[1].getX()+1, p.getPieza()[1].getY()).isActiveBlock() ||
                board.objeto(p.getPieza()[2].getX()+1, p.getPieza()[2].getY()).isActiveBlock() ||
                board.objeto(p.getPieza()[3].getX()+1, p.getPieza()[3].getY()).isActiveBlock()){
            return true;
        }else {
            return false;
        }
    }

    private boolean obstacleDown(Pieza p){
        if(board.objeto(p.getPieza()[0].getX(), p.getPieza()[0].getY()+1).isActiveBlock() ||
                board.objeto(p.getPieza()[1].getX(), p.getPieza()[1].getY()+1).isActiveBlock() ||
                board.objeto(p.getPieza()[2].getX(), p.getPieza()[2].getY()+1).isActiveBlock() ||
                board.objeto(p.getPieza()[3].getX(), p.getPieza()[3].getY()+1).isActiveBlock()){
            return true;
        }else {
            return false;
        }
    }

    private float mX=0, mY=0;
    private boolean push=false;
    private boolean down=false;
    private boolean left=false;
    private boolean right=false;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                push=true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dy>20 && dx<20){
                    if(y > mY){
                        down=true;
                        push = false;
                    }
                } else if (dx>20 && dy<20){
                    if (x > mX){
                        right=true;
                        push = false;
                    }else{
                        left=true;
                        push= false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (push){
                    pieza.turn(board);
                }
                if (down){
                    downPieza(pieza);
                    down=false;
                }
                if (right){
                    movePiezaRight(pieza);
                    right=false;
                }
                if (left){
                    movePiezaLeft(pieza);
                    left=false;
                }
                break;
        }
        mX=x; mY=y;
        return true;
    }
}
