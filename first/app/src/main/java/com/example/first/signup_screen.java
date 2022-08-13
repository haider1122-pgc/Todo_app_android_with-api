package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.first.ModelResponse.RegisterResponse;
import com.example.first.model.personModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup_screen extends AppCompatActivity {
    Db_Handler db;
    TextView t,t1;
    EditText name,age,email,pass;
    Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        db=new Db_Handler(signup_screen.this);


        t= (TextView)findViewById(R.id.already);
        b=(Button)findViewById(R.id.signup_btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!name.getText().toString().equals("") && !age.getText().toString().equals("") && !email.getText().toString().equals("") && !pass.getText().toString().equals("")) {

                    /*personModel p = new personModel(name.getText().toString(), age.getText().toString(), email.getText().toString(), pass.getText().toString());
                    db.insertPerson(p);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("SIGN UP");
                    builder.setMessage("You are Successfully registered  ");

                    openNewAvtivityOnTVClick();*/

                        personModel p = new personModel(name.getText().toString(), email.getText().toString(), pass.getText().toString(), age.getText().toString());


                    Call<RegisterResponse> call = RetrofitClient.getInstance().getApi()
                            .register("application/json",p);
                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            RegisterResponse registerResponse = response.body();
                            if(response.isSuccessful()){
                                assert registerResponse != null;
                                assert response.body() != null;
                                personModel p1 = new personModel(response.body().getUser().getName(),response.body().getUser().getEmail(),pass.getText().toString(),response.body().getUser().getAge()+"",response.body().getUser().getId());
                                db.insertPerson(p1);
                                Toast.makeText(getApplicationContext(), "registration successful", Toast.LENGTH_LONG).show();
                                openNewAvtivityOnTVClick();
                            }


                            else if(response.code() == 400)
                            {
                                Toast.makeText(getApplicationContext(), "user already exists", Toast.LENGTH_LONG).show();
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