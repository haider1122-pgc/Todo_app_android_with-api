package com.example.first;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

public class log_in_screen extends AppCompatActivity {

    Button btn;
    EditText e,email,pass;
    CheckBox c;
    TextView t,f;
    Button bt;
    EditText pss;
    Db_Handler db;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    SharedPreferences sharedPreferences;
    private  static final  String SHARED_PREF_NAME ="myPref";
    private  static final  String KEY_NAME ="id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        db= new Db_Handler(log_in_screen.this);
        c= (CheckBox) findViewById(R.id.ch);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getApplicationContext(), te, Toast.LENGTH_LONG).show();
                pss=(EditText) findViewById(R.id.password);
                if(c.isChecked()){

                    pss.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else
                {
                    pss.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }


            }


        });

        //opening dashboard after validating email and pass
        bt= (Button)findViewById(R.id.login_btn);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                email =  (EditText) findViewById(R.id.email);
                String em = email.getText().toString();
                pass=(EditText) findViewById(R.id.password);
                String ps = pass.getText().toString();
                //when a person log in his key is saved in shared prefrence
                SharedPreferences.Editor editor= sharedPreferences.edit();
                int key;
                key=db.getPersonId("PERSON","EMAIL",em,"PASSWORD",ps);
                if(key!=-1) {
                    editor.putInt(KEY_NAME,key );
                    editor.apply();
                }
                if(db.checkPerson("PERSON","EMAIL",em,"PASSWORD",ps)){
                    openNewAvtivityOnbtnClick();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                }

            }


        });
        t= (TextView)findViewById(R.id.createAcc);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getApplicationContext(), te, Toast.LENGTH_LONG).show();


                openNewAvtivityOnTVClick();
            }


        });









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

}