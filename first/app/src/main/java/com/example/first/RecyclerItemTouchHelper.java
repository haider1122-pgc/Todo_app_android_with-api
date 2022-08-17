package com.example.first;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.first.R;
import com.example.first.adapter.todoAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private todoAdapter adapter;
    private Todo_app t;
    RecyclerView recyclerView;


    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    public RecyclerItemTouchHelper(todoAdapter adapter,RecyclerView recyclerView,Todo_app t) {
        super(0, ItemTouchHelper.LEFT );
        this.adapter = adapter;
        this.t=t;
        this.recyclerView=recyclerView;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }



    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();

        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setCancelable(false);
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this Task?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //adapter.deleteItem(position);

                            t.deleteTask(position);

                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    //again setting adapter because item change didnt work when animator is false
                    recyclerView.setAdapter(adapter);


                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();





        } else {

        }
    }



    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon = null;
        ColorDrawable background = null;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if (dX > 0) {

        } else {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.ic_baseline_delete);
            background = new ColorDrawable(Color.RED);
        }

        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

         if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }
}