package com.example.tetris;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   }

    public void button_playOnClick(View view) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

    public void button_aboutOnClick(View view) {
        Context context = getApplicationContext();
        CharSequence text = "\t\t\t\t\t\t\t\t\t\t\t\tTarea 1\nDesarrollo de aplicaciones móviles\n\t\t\t\t\t\t\t\tCarlos Chévez C.\n\t\t\t\t\t\t\t\tI Semestre 2019";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }

    public void button_exitOnClick(View view) {
        this.finish();
    }
}
