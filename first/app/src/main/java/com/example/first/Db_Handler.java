package com.example.first;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.first.adapter.todoAdapter;
import com.example.first.model.personModel;
import com.example.first.model.todoModel;

import java.util.ArrayList;
import java.util.List;

public class Db_Handler extends SQLiteOpenHelper {



    private SQLiteDatabase db;
    Context context;
    private static  final String DATABASE_NAME = "TODO_DATABASE";
    private static  final String TABLE_PERSON = "PERSON";
    private static  final String COL_ID = "ID";
    private static  final String COL_NAME = "NAME";
    private static  final String COL_AGE = "AGE";
    private static  final String COL_EMAIL = "EMAIL";
    private static  final String COL_PASSWORD = "PASSWORD";
    //setting attributes of second table
    private static  final String TABLE_TASK = "TASKS";
    private static  final String COL_TASK_ID = "ID";
    private static  final String COL_PERSON_ID = "PERSON_ID";
    private static  final String COL_TITLE = "TITLE";
    private static  final String COL_DESCRIPTION = "DESCRIPTION";
    private static  final String COL_TIME = "TIME";
    private static  final String COL_STATUS = "STATUS";
    //shared pref id




    public Db_Handler(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 7);
        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PERSON + "(ID TEXT PRIMARY KEY  NOT NULL , NAME TEXT NOT NULL, AGE TEXT NOT NULL, EMAIL TEXT NOT NULL, PASSWORD TEXT NOT NULL )");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TASK + "(ID TEXT PRIMARY KEY  NOT NULL,PERSON_ID TEXT NOT NULL  , TITLE TEXT NOT NULL, DESCRIPTION TEXT NOT NULL, TIME TEXT NOT NULL, STATUS INTEGER NOT NULL, FOREIGN KEY (PERSON_ID) REFERENCES PERSON(ID) \n" +
                "ON UPDATE CASCADE       ON DELETE CASCADE)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);

    }
    //this function inserts task in tasks table
    public void insertTask(@NonNull todoModel model, String personID){

        db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TASK_ID,model.getId());
        values.put(COL_TITLE , model.getTitle());
        values.put(COL_DESCRIPTION , model.getDescriptipn());
        values.put(COL_TIME , model.getTime());
        int status;
        if(model.isStatus()){
            status=1;
        }
        else
        {
            status=0;
        }
        values.put(COL_STATUS ,status);
        values.put(COL_PERSON_ID ,personID);
        db.insert(TABLE_TASK , null , values);
        //Toast.makeText(context, "Added", Toast.LENGTH_LONG).show();
    }
    //this function deletes a provided id task from list
    public void deleteTask(String id ){

        db = this.getWritableDatabase();
        db.delete(TABLE_TASK , "ID=?" , new String[]{String.valueOf(id)});


    }
    //this function only updates status of task in db
    public void updateStatus(String id , int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STATUS , status);
        db.update(TABLE_TASK , values , "ID=?" , new String[]{String.valueOf(id)});
    }
    //this function updated task title description and time not status
    public void updateTask(String id , String title,String description, String time){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE,title);
        values.put(COL_DESCRIPTION , description);
        values.put(COL_TIME , time);
        db.update(TABLE_TASK , values , "ID=?" , new String[]{String.valueOf(id)});
    }
    //This function inserts the data of person in db (registration of person)
    public void insertPerson(@NonNull personModel model){

        db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID,model.getId());
        values.put(COL_NAME , model.getName());
        values.put(COL_AGE , model.getAge());
        values.put(COL_EMAIL , model.getEmail());
        values.put(COL_PASSWORD , model.getPassword());
        db.insert(TABLE_PERSON , null , values);
        Toast.makeText(context, "Added", Toast.LENGTH_LONG).show();
    }
    //this function validates that the person with provided credentials is avaiable in db helped in login purpose
    public  boolean checkPerson(String TableName, String emailColumn, String email,String passwordColumn, String pass) {
        db=super.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + emailColumn + " = '" + email+"' AND "+ passwordColumn + " = '" + pass+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    //this id is used in shared preferennces and as an foreign key in tasks table
    @SuppressLint("Range")
    public  String getPersonId(String TableName, String emailColumn, String email, String passwordColumn, String pass) {
        db=super.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + emailColumn + " = '" + email+"' AND "+ passwordColumn + " = '" + pass+"'";
        Cursor cursor = db.rawQuery(Query, null);
         String id="-1";
        if(cursor.getCount() <= 0){
            cursor.close();
            return id;
        }
        cursor.moveToFirst();
        id = cursor.getString(cursor.getColumnIndex("ID"));

        //Toast.makeText(context, id+"", Toast.LENGTH_LONG).show();
        cursor.close();
        return id;
    }
    //this function returns name of a person who is currently login to the app
    @SuppressLint("Range")
    public  String getPersonName( String id) {
        db=super.getReadableDatabase();
        String Query = "Select * from " + "PERSON" + " where " + "ID" + " = '" + id+"'";
        Cursor cursor = db.rawQuery(Query, null);
        String name;
        if(cursor.getCount() <= 0){
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        name= cursor.getString(cursor.getColumnIndex("NAME"));

        //Toast.makeText(context, id+"", Toast.LENGTH_LONG).show();
        cursor.close();
        return name;
    }
    //this function returns the list of the person who is currently login taking input the id of the person which is gained by shared preferences
    @SuppressLint("Range")
    public List<todoModel> getAllTasks(String i){


        db = super.getWritableDatabase();
        Cursor cursor = null;
        String Query = "Select * from " + TABLE_TASK + " where " + COL_PERSON_ID + " = '" + i+"' ";
        List<todoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.rawQuery(Query,null);
            boolean status;

            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        if(cursor.getInt(cursor.getColumnIndex(COL_STATUS))==1){
                            status=true;
                        }
                        else{
                            status=false;
                        }
                        @SuppressLint("Range") todoModel task = new todoModel(cursor.getString(cursor.getColumnIndex(COL_TITLE)),cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)),cursor.getString(cursor.getColumnIndex(COL_TIME)),status,cursor.getString(cursor.getColumnIndex(COL_TASK_ID)));
                        modelList.add(task);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        return modelList;
    }

}
