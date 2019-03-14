package com.example.tetris;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    private Tetris tetris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tetris = findViewById(R.id.TetrisLayout);
        tetris.setDad(this);
    }

    @Override protected void onPause() {
        super.onPause();
        tetris.getThread().pausar();
    }
    @Override protected void onResume() {
        super.onResume();
        tetris.getThread().reanudar();
    }
    @Override protected void onDestroy() {
        tetris.getThread().detener();
        super.onDestroy();
    }
}
