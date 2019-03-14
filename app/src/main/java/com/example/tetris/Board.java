package com.example.tetris;

import android.graphics.Canvas;
import android.view.View;

public class Board {
    private Bloque[][] board;

    public Board(View view){
        board = new Bloque[10][20];
        int i,j;
        for (i = 0; i < 10; i++){
            for(j = 0; j < 20; j++){
                board[i][j] = new Bloque(i,j);
            }
        }
    }

    public Bloque objeto(int x, int y){
        return board[x][y];
    }

    public void setObjet(int x, int y, Bloque bloque){
        board[x][y] = bloque;
    }

    public void draw(Canvas canvas){
        int i,j;
        for(i=0;i<10;i++){
            for(j=0;j<20;j++){
                if(board[i][j].isActiveBlock()) {
                    board[i][j].drawBloque(canvas);
                }
            }
        }
    }

    public int eliminarFila(){
        int filas=0;
        int i,j;
        for(i = 19; i > -1; i--){
            if(board[0][i].isActiveBlock() && board[1][i].isActiveBlock() &&
                    board[2][i].isActiveBlock() && board[3][i].isActiveBlock() &&
                    board[4][i].isActiveBlock() && board[5][i].isActiveBlock() &&
                    board[6][i].isActiveBlock() && board[7][i].isActiveBlock() &&
                    board[8][i].isActiveBlock() && board[9][i].isActiveBlock()){
                for(j = 0; j < 10; j++){
                    board[j][i].setActiveBlock(false);
                }
                filas++;
            }else{
                for(j = 0; j < 10; j++){
                    if(board[j][i].isActiveBlock() && filas>0){
                        board[j][i + filas]=board[j][i];
                        board[j][i + filas].setY(board[j][i + filas].getY() + filas);
                        board[j][i] = new Bloque(j,i);
                    }
                }
            }
        }
        return filas;
    }
}
