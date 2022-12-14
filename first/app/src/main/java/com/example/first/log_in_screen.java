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

import com.example.first.ModelResponse.RegisterResponse;
import com.example.first.model.personModel;

import java.io.Console;
import java.security.PrivateKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private  static final  String TOKEN ="token";



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
                /*
                //when a person log in his key is saved in shared prefrence
                SharedPreferences.Editor editor= sharedPreferences.edit();
                String key;
                key=db.getPersonId("PERSON","EMAIL",em,"PASSWORD",ps);
                if(key!="-1") {
                    editor.putString(KEY_NAME,key );
                    editor.apply();
                }
                if(db.checkPerson("PERSON","EMAIL",em,"PASSWORD",ps)){
                    openNewAvtivityOnbtnClick();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                }
                */
                personModel p = new personModel(email.getText().toString(),pass.getText().toString());
                Call<RegisterResponse> call = RetrofitClient.getInstance().getApi()
                        .login("application/json",p);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                        if(response.isSuccessful()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String key;
                            String token;
                            //key=db.getPersonId("PERSON","EMAIL",em,"PASSWORD",ps);
                            key = response.body().getUser().getId();
                            token =response.body().getToken();
                            if (key != "-1") {
                                editor.putString(KEY_NAME, key);
                                editor.putString(TOKEN, token);
                                editor.apply();
                                //openNewAvtivityOnbtnClick();

                            }
                            if (db.checkPerson("PERSON", "EMAIL",response.body().getUser().getEmail(), "PASSWORD", ps)) {

                            } else {
                                personModel p1 = new personModel(response.body().getUser().getName(),response.body().getUser().getEmail(),ps,response.body().getUser().getAge()+"",response.body().getUser().getId());
                                db.insertPerson(p1);
                            }
                            openNewAvtivityOnbtnClick();


                            Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG).show();



                        }
                        else if(response.code()==400)
                        {
                            Toast.makeText(getApplicationContext(), "invalid credentials", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_LONG).show();
                    }
                });


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