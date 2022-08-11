package com.example.first.model;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.first.R;

public class editDialoge extends AppCompatDialogFragment {
    private EditText title,disc;
    private editDialogeListner listner;
    String tit,dis;
    int position;
    public void setData(String tit,String dis,int position){
      this.tit=tit;
      this.dis=dis;
      this.position=position;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.dialoge,null);
        title = view.findViewById(R.id.title);
        disc=view.findViewById(R.id.description);
        title.setText(tit);
        disc.setText(dis);
        builder.setView(view).setTitle("Edit item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String titl=title.getText().toString();
                        String dis=disc.getText().toString();
                        listner.applyText(titl,dis,position);

                    }
                });

        return builder.create();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner= (editDialogeListner)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement exampledialoguelistner ");
        }
    }

    public interface editDialogeListner{
        void applyText(String titl,String dis,int position);
    }
}
