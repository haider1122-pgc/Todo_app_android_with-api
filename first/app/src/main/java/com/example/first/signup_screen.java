package com.example.first;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first.model.personModel;

public class signup_screen extends AppCompatActivity {
    Db_Handler db;
    TextView t,t1;
    EditText name,contact,email,pass;
    Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        db=new Db_Handler(signup_screen.this);


        t= (TextView)findViewById(R.id.already);
        b=(Button)findViewById(R.id.signup_btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!name.getText().toString().equals("") && !contact.getText().toString().equals("") && !email.getText().toString().equals("") && !pass.getText().toString().equals("")) {

                    personModel p = new personModel(name.getText().toString(), contact.getText().toString(), email.getText().toString(), pass.getText().toString());
                    db.insertPerson(p);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("SIGN UP");
                    builder.setMessage("You are Successfully registered  ");

                                    openNewAvtivityOnTVClick();


                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                }


                //openNewAvtivityOnTVClick();
            }


        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getApplicationContext(), te, Toast.LENGTH_LONG).show();


                openNewAvtivityOnTVClick();
            }


        });
        t1= (TextView)findViewById(R.id.bck);
        t1.setOnClickListener(new View.OnClickListener() {
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
        Intent intent = new Intent(this, log_in_screen.class);
        // intent.putExtra("message",te); //to access the widgets of second activity
        startActivity(intent);



    }
}