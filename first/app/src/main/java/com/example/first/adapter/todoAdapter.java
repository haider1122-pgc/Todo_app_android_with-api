package com.example.first.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first.Db_Handler;
import com.example.first.ModelResponse.TaskResponse;
import com.example.first.R;
import com.example.first.RetrofitClient;
import com.example.first.Todo_app;
import com.example.first.model.todoModel;
import com.example.first.recyclerViewInterface;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public  class todoAdapter extends RecyclerView.Adapter<todoAdapter.ViewHolder> {
    Db_Handler db;
    private List<todoModel> lst;
    private RecyclerView recycler;
    private Todo_app todo;
    private final recyclerViewInterface inter;



    public List<todoModel> getLst() {
        return lst;
    }

    public todoAdapter(Todo_app todo, Todo_app inter, Db_Handler db){
        this.db= db;
        this.todo=todo;
        this.inter=inter;

    }


    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType ){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent ,false);
        return new ViewHolder(itemView,inter);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        todoModel item = lst.get(position);
        holder.title.setText(item.getTitle());
        holder.task.setChecked(item.isStatus());
        holder.img.setImageResource(R.drawable.line);

        //striking text while loading data from list
        if(lst.get(position).isStatus()){
            holder.img.setBackgroundColor(Color.rgb(66,103,178));
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
        holder.time.setText(item.getTime());


    }

    @Override
    public int getItemCount() {

        return lst.size();
    }
    public void setTask(List<todoModel> tasklst){
        this.lst=tasklst;
        //notifyItemRangeChanged(0, lst.size());
        notifyItemInserted(lst.size());



    }

    public void deleteItem(int position){

        //deleting from db
        db.deleteTask(lst.get(position).getId());
        //deleting from list
        lst.remove(position);
        Toast.makeText(todo.getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();
        notifyItemRemoved(position);
    }

    public void editItem(int position, todoModel e) {
        //updating task in list
        lst.set(position,e);
        //updating in db
        db.updateTask(lst.get(position).getId(),lst.get(position).getTitle(),lst.get(position).getDescriptipn(),lst.get(position).getTime());
        notifyItemChanged(position);
        Toast.makeText(todo.getApplicationContext(), "Task Updated", Toast.LENGTH_LONG).show();

    }



    public void editItemStatus(int position, todoModel e) {
        //updating status in list
        lst.set(position,e);
        //as in db boolean value is not stored so converting boolean into integer then updating in db
        int status;
        if(e.isStatus()){
            Toast.makeText(todo.getApplicationContext(), "Task Completed", Toast.LENGTH_LONG).show();
            status=1;
        }
        else{
            Toast.makeText(todo.getApplicationContext(), "Task Not Completed", Toast.LENGTH_LONG).show();
            status=0;
        }
        db.updateStatus(lst.get(position).getId(),status);
        notifyItemChanged(position);



    }

    public Context getContext() {
        return todo;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
         CheckBox task;
         TextView time,title;
         TextView description;
         ImageView img;



         ViewHolder(View  view , recyclerViewInterface inter) {
            super(view);
            task = view.findViewById(R.id.todo_checkbox);
            title=view.findViewById(R.id.title);
            time=view.findViewById(R.id.txt_start_time);
            img=view.findViewById(R.id.line);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inter!=null ){
                        int position=getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            inter.onItemClick(position);
                        }
                    }
                }
            });
            task.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inter!=null ){
                        int position1=getAdapterPosition();
                        if (position1 != RecyclerView.NO_POSITION) {
                            boolean l = task.isChecked();
                            //this setting cause notifydatasetchanges error
                            if(l){

                                title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                img.setBackgroundColor(Color.rgb(66,103,178));
                            }
                            else{
                                title.setPaintFlags(title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                                img.setBackgroundColor(Color.rgb(169, 169, 169));
                            }



                            inter.onCheckBoxClick(position1,l);

                        }
                    }

                }
            });


            // description=view.findViewById(R.id.description);
        }
    }

}

