package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first.ModelResponse.DeleteTaskResponse;
import com.example.first.ModelResponse.LogoutResponse;
import com.example.first.ModelResponse.RegisterResponse;
import com.example.first.ModelResponse.TaskResponse;

import com.example.first.adapter.todoAdapter;
import com.example.first.model.editDialoge;
import com.example.first.model.exampleDialoge;
import com.example.first.model.todoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Todo_app extends AppCompatActivity implements exampleDialoge.exampleDialogeListner,recyclerViewInterface, editDialoge.editDialogeListner {

    Db_Handler db;
    CheckBox chk;
    TextView textView;
    Button logoutBtn;
    Spinner spinner;
    private RecyclerView recycler;
    private todoAdapter taskadapter;
    private List<todoModel> taskLst;
    FloatingActionButton btn;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_NAME = "id";
    private static final String TOKEN = "token";

    //return context of todo_app_activity
    public Context getContext() {
        return Todo_app.this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_app);
        db = new Db_Handler(Todo_app.this);
        taskLst = new ArrayList<>();
        //initializing shared preferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        recycler = findViewById(R.id.tasksRecyclerView);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        taskadapter = new todoAdapter(this, this, db);

        recycler.setAdapter(taskadapter);
        //setting all tasks from db to list
        taskLst = db.getAllTasks(sharedPreferences.getString(KEY_NAME, "-1"));
        taskadapter.setTask(taskLst);
        //making animation false so recycler view doesn't change its position
        RecyclerView.ItemAnimator animator = recycler.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        //for swip delete
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(taskadapter, recycler,this));
        itemTouchHelper.attachToRecyclerView(recycler);
        //stop adapter to change position after data changes



       /* //for reversing recycler view
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recycler.setLayoutManager(mLayoutManager);

        */

        spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener

        String id = sharedPreferences.getString(KEY_NAME, "-1");

        // Spinner Drop down elements
        List<String> item = new ArrayList<String>();
        item.add(db.getPersonName(id));
        item.add("Log out");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (String.valueOf(spinner.getSelectedItem()).toString().equals("Log out")) {
                    Intent intent = new Intent(Todo_app.this, log_in_screen.class);
                    Call<LogoutResponse> call = RetrofitClient.getInstance().getApi()
                            .logout("Bearer " + sharedPreferences.getString(TOKEN, ""));
                    call.enqueue(new Callback<LogoutResponse>() {
                        @Override
                        public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                            if (response.isSuccessful() && Objects.requireNonNull(response.body()).getSuccess()) {
                                Toast.makeText(getApplicationContext(), "successfully logged out ", Toast.LENGTH_LONG).show();
                                startActivity(intent);

                            } else if (response.code() == 400) {
                                Toast.makeText(getApplicationContext(), "user not found", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "user not found", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<LogoutResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_LONG).show();
                        }
                    });


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //creating on click listner for floating button
        btn = (FloatingActionButton) findViewById(R.id.add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Toast.makeText(getApplicationContext(), te, Toast.LENGTH_LONG).show();
                exampleDialoge e = new exampleDialoge();

                e.show(getSupportFragmentManager(), "Example dialoge");


            }


        });
/*
        //dummy data
        todoModel t1 = new todoModel("Assignment1","need to complete in time","06/11/2017 12:26:18",false);
        todoModel t2 = new todoModel("Reading","hobbies requirement","06/11/2017 12:26:28",true);
        todoModel t3 = new todoModel("Cricket Match","highly important ","06/11/2017 12:26:38",true);
        todoModel t4 = new todoModel("Assignment2","need to complete in time","06/11/2017 12:26:48",false);
        todoModel t5 = new todoModel("Meeting","with pitb HR ","07/11/2017 12:26:48",false);
        todoModel t6 = new todoModel("Shopping","buy furniture for family","07/11/2017 12:26:48",true);
        todoModel t7 = new todoModel("Internship tasks","creation of a todo ","08/11/2017 12:26:48",false);
        todoModel t8 = new todoModel("Outing","with friends ","08/11/2017 12:26:48",true);
        todoModel t9 = new todoModel("Family gathering","Dinner with family","08/11/2017 12:26:48",true);
        todoModel t10 = new todoModel("Tour","islamabad visit","08/11/2017 12:26:48",false);
        taskLst.add(t1);
        taskLst.add(t2);
        taskLst.add(t3);
        taskLst.add(t4);
        taskLst.add(t5);
        taskLst.add(t6);
        taskLst.add(t7);
        taskLst.add(t8);
        taskLst.add(t9);
        taskLst.add(t10);
        taskadapter.setTask(taskLst);


 */

    }
    //this function first remove data from api then remove from list and db
    public void deleteTask(int position) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + sharedPreferences.getString(TOKEN,""));
        headers.put("Content-Type", "application/json");
        Call<DeleteTaskResponse> call = RetrofitClient.getInstance().getApi()
                .deleteTask(taskLst.get(position).getId(), headers);
        call.enqueue(new Callback<DeleteTaskResponse>() {
            @Override
            public void onResponse(Call<DeleteTaskResponse> call, Response<DeleteTaskResponse> response) {
                if (response.isSuccessful() && response.body().getSuccess()) {
                    taskadapter.deleteItem(position);

                } else if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<DeleteTaskResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_LONG).show();
            }
        });


    }


    //opening edit dialougue on click of item
    @Override
    public void onItemClick(int position) {

        editDialoge e1 = new editDialoge();
        e1.setData(taskLst.get(position).getTitle(), taskLst.get(position).getDescriptipn(), position);
        e1.show(getSupportFragmentManager(), "Example dialoge");

    }

    //updating status on click of checkbox
    @Override
    public void onCheckBoxClick(int position, boolean status) {
        todoModel t = new todoModel(status);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + sharedPreferences.getString(TOKEN, ""));
        headers.put("Content-Type", "application/json");
        Call<TaskResponse> call = RetrofitClient.getInstance().getApi()
                .updateTask(taskLst.get(position).getId(), headers, t);
        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful() && response.body().getSuccess()) {
                    todoModel t1 = new todoModel(response.body().getData().getDescription(), taskLst.get(position).getDescriptipn(), taskLst.get(position).getTime(), response.body().getData().getCompleted(), response.body().getData().getId());
                    taskadapter.editItemStatus(position, t1);

                } else if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_LONG).show();
            }
        });








        /*
        //testing shared preferences
        int id = sharedPreferences.getInt(KEY_NAME,-1);
        if(id!=-1){
            Toast.makeText(getApplicationContext(), id+"", Toast.LENGTH_LONG).show();
        }
         */


    }

    //this function of interface helps us to get input from dialougue window and add into list
    @Override
    public void applyTexts(String titl, String dis) {
        if (titl.equals("") || dis.equals("") || titl.equals("Title") || dis.equals("Description")) {
            Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
        } else {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            //getting shared preference key

            // todoModel t = new todoModel(titl, dis, formatter.format(date).toString(), false);
            todoModel t = new todoModel(titl);
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + sharedPreferences.getString(TOKEN, ""));
            headers.put("Content-Type", "application/json");

            Call<TaskResponse> call = RetrofitClient.getInstance().getApi()
                    .addTask(headers, t);
            call.enqueue(new Callback<TaskResponse>() {
                @Override
                public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                    if (response.isSuccessful() && response.body().getSuccess()) {
                        todoModel t = new todoModel(response.body().getData().getDescription(), dis, formatter.format(date).toString(), false, response.body().getData().getId());
                        taskLst.add(t);
                        db.insertTask(t, response.body().getData().getOwner());
                        taskadapter.setTask(taskLst);
                        Toast.makeText(getApplicationContext(), "Task Added", Toast.LENGTH_LONG).show();


                    } else if (response.code() == 400) {
                        Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<TaskResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_LONG).show();

                }
            });


        }

    }

    //this function helps us to get input from update dialougue window and update data into lsit by calling update function
    @Override
    public void applyText(String titl, String dis, int position) {
        if (titl.equals("") || dis.equals("") || titl.equals("Title") || dis.equals("Description")) {
            Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
        } else {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            //getting previous status
            boolean l;
            l = taskLst.get(position).isStatus();
            todoModel t = new todoModel(titl, dis, formatter.format(date).toString(), l, taskLst.get(position).getId());
            taskadapter.editItem(position, t);
        }


    }



}

