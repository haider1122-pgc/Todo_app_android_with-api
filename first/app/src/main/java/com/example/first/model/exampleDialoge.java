package com.example.first.model;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.first.R;

public class exampleDialoge extends AppCompatDialogFragment {
    private EditText title,disc;
    private exampleDialogeListner listner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.dialoge,null);
        builder.setView(view).setTitle("Add new item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String titl=title.getText().toString();
                        String dis=disc.getText().toString();
                        listner.applyTexts(titl,dis);

                    }
                });
        title = view.findViewById(R.id.title);
        disc=view.findViewById(R.id.description);
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner= (exampleDialogeListner)context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString()+"must implement exampledialoguelistner ");
        }
    }

    public interface exampleDialogeListner{
        void applyTexts(String titl,String dis);
    }
}
