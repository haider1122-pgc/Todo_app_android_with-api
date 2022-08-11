package com.example.first;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
     Button btn;
     EditText e,email,pass;
     CheckBox c;
    TextView t,f;
    Button bt;
    EditText pss;
    ProgressBar pb;
    int counter=0;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar)findViewById(R.id.progressBar);



        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run()
            {
                counter++;
                pb.setProgress(counter);

                if(counter == 100) {

                    final  Intent i = new Intent(MainActivity.this,log_in_screen.class);
                    startActivity(i);
                    t.cancel();

                }
            }
        };

        t.schedule(tt,0,70);

        //code for hold screen for 1000 milli sec
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
            }
        },1000);*/









    }
    private void openNewAvtivityOnTVClick() {
       // e =  (EditText) findViewById(R.id.tv1);
        //String te = e.getText().toString();
        Intent intent = new Intent(this, signup_screen.class);
       // intent.putExtra("message",te); //to access the widgets of second activity
        startActivity(intent);



    }
    private void openNewAvtivityOnbtnClick() {
        // e =  (EditText) findViewById(R.id.tv1);
        //String te = e.getText().toString();
        Intent intent = new Intent(this, Todo_app.class);
        // intent.putExtra("message",te); //to access the widgets of second activity
        startActivity(intent);



    }
    public void createNewDialougue(){
        dialogBuilder = new AlertDialog.Builder(this );
        final  View popup=getLayoutInflater().inflate(R.layout.popup,null);
        //get data from popup here
        dialogBuilder.setView(popup);
        dialog=dialogBuilder.create();
        dialog.show();


    }
}